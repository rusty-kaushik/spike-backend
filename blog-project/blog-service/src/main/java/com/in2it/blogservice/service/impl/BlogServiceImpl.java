package com.in2it.blogservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in2it.blogservice.customException.IdInvalidException;
import com.in2it.blogservice.customException.InfoMissingException;
import com.in2it.blogservice.customException.UserNotFoundException;
import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;
import com.in2it.blogservice.mapper.Converter;
import com.in2it.blogservice.model.Blog;
import com.in2it.blogservice.repository.BlogRepository;
import com.in2it.blogservice.service.BlogService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Transactional
@Slf4j
public class BlogServiceImpl implements BlogService {

	// This Converter is used to inject our converter class for converting Entity to
	// DTO or DTO to Entity .
	@Autowired
	private Converter objectMapper;

	// Injecting our Repository class
	@Autowired
	private BlogRepository repo;

	// This method is used to save data in database and save Media in file system .
	@Override
	public BlogDto saveBlogWithFile(BlogDto blogDto, List<MultipartFile> multipartFile) {
		Blog blog = null;

		// Set original file name is this list.
		List<String> originalFilenames = new ArrayList<>();

		if (!multipartFile.isEmpty() && blogDto != null) {

			// calling uploadFile method to save media in file
			List<String> uploadFilePath = objectMapper.uploadFile(multipartFile);

			// getting original file name and save in list
			for (MultipartFile multipart : multipartFile) {

				String originalFilename2 = multipart.getOriginalFilename();
				originalFilenames.add(originalFilename2);
			}

			// converting DTO to Entity
			Blog dtoToBlogConverter = objectMapper.dtoToBlogConverter(blogDto, originalFilenames, uploadFilePath);

			// calling random generating URI in converter class
			dtoToBlogConverter.setMediaPath(objectMapper.genrateUriLink(multipartFile));

			blog = repo.save(dtoToBlogConverter);
		}

		return objectMapper.blogToDtoConverter(blog);

	}

	// This method is used to update BLOG with Blog_id with limited permissions
	@Override
	public BlogDto updateBlog(BlogUpdateDto updateDto, UUID blogId) {

		Blog blog = null;

		blog = repo.getByBlogId(blogId);

		System.out.println("===================" + updateDto.getAuthorId());

		if ((blog != null && updateDto.getAuthorId() != null) && blog.getId().equals(blogId)) {
			System.out.println("----------------------");
			if (!updateDto.getContent().isEmpty())
				blog.setContent(updateDto.getContent());
			if (!updateDto.getTitle().isEmpty())
				blog.setTitle(updateDto.getTitle());
			blog.setUpdatedDateTime(LocalDateTime.now());
			blog.setUpdatedBy(updateDto.getAuthorId());

			System.out.println("blog" + blog);
			return objectMapper.blogToDtoConverter(repo.save(blog));
		} else {
			throw new UserNotFoundException(
					" Insufficient information, Please ! try again with sufficient information.");
		}

	}

	// update total like count
	@Override
	public BlogDto updateLike(Long totallikeCount, UUID id) {

		Blog blog = repo.getByBlogId(id);

		if (blog != null) {

			blog.setLikeCount(totallikeCount);
			return objectMapper.blogToDtoConverter(blog);
		} else {
			throw new IdInvalidException("id not found , Please ! Enter correct id .");
		}

	}

	// update total comment count
	@Override
	public BlogDto updateComment(Long totalCommentCount, UUID id) {
		Blog blog = repo.getByBlogId(id);

		if (blog != null) {
			blog.setCommentCount(totalCommentCount);
			return objectMapper.blogToDtoConverter(blog);
		} else {
			throw new IdInvalidException("id not found , Please ! Enter correct id .");
		}

	}

	// soft delete with blog_id and save user_id whose delete this post
	@Override
	public Boolean deleteBlog(UUID id, String deletedBy) {

		BlogDto blog = getBlogById(id);

		if (blog.getId().equals(id) && deletedBy != null) {
			System.out.println("============================" + blog);

			repo.deleteBlogById(id, deletedBy, LocalDateTime.now());

			return true;
		} else {

			throw new IdInvalidException(HttpStatus.NO_CONTENT + "id not found, Please ! enter correct id.");
		}

	}

	// soft delete with title and save user_id whose delete this post
	@Override
	public Boolean deleteBlogByTitle(String title, UUID blogId) {

		boolean flag = false;

		List<BlogDto> blog = getBlogTitle(title);

		if (!blog.isEmpty()) {

			for (BlogDto blogDto : blog) {

				if (title != null && blogDto.getId().equals(blogId)) {

					repo.deleteByTitleContainingAllIgnoringCaseAndStatus(blogDto.getId(), blogDto.getAuthorId(),
							LocalDateTime.now());

					flag = true;

					break;
				} else {

					throw new InfoMissingException(HttpStatus.NO_CONTENT + " Data not found, Please ! try again .");
				}
			}
		} else {
			throw new InfoMissingException(HttpStatus.NO_CONTENT + " Data not found, Please ! try again .");
		}

		return flag;

	}

	// Get blog with title
	@Override
	public List<BlogDto> getBlogTitle(String title) {

		List<Blog> blog = repo.findByTitleContainingAllIgnoringCaseAndStatus(title, "ACTIVE");
		List<BlogDto> blogDtoList = new ArrayList<>();
		for (Blog blog2 : blog) {

			if (blog2 != null) {
				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);

				blogDtoList.add(blogToDtoConverter);
			} else {
				throw new UserNotFoundException(HttpStatus.NO_CONTENT + " Data not available, please ! Try again.");
			}
		}

		return blogDtoList;
	}

	// get all blog
	@Override
	public List<BlogDto> getBlog() {

		List<Blog> blog = repo.findAll();

		log.info("----------------------------------" + blog);

		List<BlogDto> blogDtoList = new ArrayList<>();

		for (Blog blog2 : blog) {

			if (blog2 != null) {
				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);

				blogDtoList.add(blogToDtoConverter);
			} else {
				throw new UserNotFoundException(HttpStatus.NO_CONTENT + " Data not available, please ! Try again.");
			}
		}

		return blogDtoList;
	}

	// Get blog by userId or we can say unique userName
	public List<BlogDto> getByAutherID(String autherId) {

		List<Blog> byAuthorId = repo.findByAuthorId(autherId);

		List<BlogDto> blogDtoList = new ArrayList<>();

		if (!byAuthorId.isEmpty()) {

			for (Blog blog2 : byAuthorId) {

				if (blog2 != null) {
					BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);
					blogDtoList.add(blogToDtoConverter);
				}
			}

			return blogDtoList;
		} else {
			throw new UserNotFoundException(HttpStatus.NO_CONTENT + "  Data not available, please ! Try again.");
		}

	}

	// Get blog by blog_id
	@Override
	public BlogDto getBlogById(UUID id) {

		Blog blog = repo.getByBlogId(id);
		if (blog != null) {
			BlogDto blogDto = objectMapper.blogToDtoConverter(blog);
			return blogDto;
		} else {
			throw new UserNotFoundException(HttpStatus.NO_CONTENT + "   Data not available, please ! Try again.");
		}
	}

	@Override
	public List<BlogDto> getByVisibility(long departmentId) {

		List<Blog> bydepartmentId = repo.getByDepartmentId(departmentId);

		System.out.println("--------------------------------------------" + bydepartmentId);

		List<BlogDto> dtos = new ArrayList<>();

		if (!bydepartmentId.isEmpty()) {

			for (Blog blog : bydepartmentId) {

				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog);
				dtos.add(blogToDtoConverter);
			}

			return dtos;
		} else {
			throw new UserNotFoundException(HttpStatus.NO_CONTENT + "   Data not available, please ! Try again.");

		}

	}

}
