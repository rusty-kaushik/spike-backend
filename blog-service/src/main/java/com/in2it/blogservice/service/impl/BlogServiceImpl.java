package com.in2it.blogservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

		log.info("------------------------" + blogDto);
		// Set original file name is this list.
		List<String> originalFilenames = null;
		List<String> paths = null;
		if (multipartFile!=null && blogDto != null) {

			// calling uploadFile method to save media in file
			Map<String, List<String>> uploadFilePath = objectMapper.uploadFile(multipartFile);

			for (Map.Entry<String, List<String>> entry : uploadFilePath.entrySet()) {
				if (entry.getKey().equals("media_path")) {

					paths = entry.getValue();
				} else if (entry.getKey().equals("file_name")) {

					originalFilenames = entry.getValue();
				}
			}
		}
		Blog dtoToBlogConverter = objectMapper.dtoToBlogConverter(blogDto, originalFilenames, paths);
		
		// converting DTO to Entity
		
		// calling random generating URI in converter class
		dtoToBlogConverter.setMediaPath(objectMapper.genrateUriLink(multipartFile));
		
		blog = repo.save(dtoToBlogConverter);

		return objectMapper.blogToDtoConverter(blog);

	}

	// This method is used to update BLOG with Blog_id with limited permissions
	@Override
	public BlogDto updateBlog(BlogUpdateDto updateDto, UUID blogId) {

		
		
		Blog blog = null;

		blog = repo.getByBlogId(blogId);

		log.info("===================" + updateDto.getUserName());

		if ((blog != null && updateDto.getUserName() != null) && blog.getId().equals(blogId)) {
			if (updateDto.getContent()!=null)
				blog.setContent(updateDto.getContent());
			if (updateDto.getTitle()!=null)
				blog.setTitle(updateDto.getTitle());
			blog.setUpdatedDateTime(LocalDateTime.now());
			blog.setUpdatedBy(updateDto.getUserName());

			return objectMapper.blogToDtoConverter(repo.save(blog));
		} else {
			
			UserNotFoundException userNotFoundException = new UserNotFoundException(" Insufficient information, May be your userName or blogId is null. Please ! Enter correct Input");
		log.error("userNotFoundException--------------------------------------------------"+userNotFoundException);
			throw userNotFoundException;
		}

	}

	// update total like count
	@Override
	public BlogDto updateLike(Long totallikeCount, UUID id) {

		System.out.println(id);
		Blog blog = repo.getByBlogId(id);

		if (blog != null) {

			blog.setLikeCount(totallikeCount);
			return objectMapper.blogToDtoConverter(blog);
		} else {
			IdInvalidException idInvalidException = new IdInvalidException("id not found , Please ! Enter correct id.");
			log.error("idInvalidException------------------"+idInvalidException);
			
			throw idInvalidException;
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
			IdInvalidException idInvalidException = new IdInvalidException("id not found , Please ! Enter correct id .");
			log.error("idInvalidException---------------------------"+idInvalidException);
			throw idInvalidException;
		}

	}

	// soft delete with blog_id and save user_id whose delete this post
	@Override
	public Boolean deleteBlog(UUID id, String updatedBy) {

		BlogDto blog = getBlogById(id);

		if (blog.getId().equals(id) && updatedBy != null) {
			log.info("============================" + blog);

			repo.deleteBlogById(id, updatedBy, LocalDateTime.now());

			return true;
		} else {

			IdInvalidException idInvalidException = new IdInvalidException("id not found , Please ! Enter correct id .");
			log.error("idInvalidException---------------------------"+idInvalidException);
			throw idInvalidException;
		}

	}

	// soft delete with title and save user_id whose delete this post
	@Override
	public Boolean deleteBlogByTitle(String title, UUID blogId, String updatedBy) {

		boolean flag = false;

		List<BlogDto> blog = getBlogTitle(title);

		log.info("-----------" + blog);

		if (!blog.isEmpty()) {

			for (BlogDto blogDto : blog) {

				if (title != null && blogDto.getId().equals(blogId)) {

					repo.deleteByTitleContainingAllIgnoringCaseAndStatus(blogDto.getId(), LocalDateTime.now(),
							updatedBy);

					flag = true;

					break;
				} else {
//					continue;
					InfoMissingException infoMissingException = new InfoMissingException(HttpStatus.NO_CONTENT + " Data not found, Please ! try again .");
					log.error("infoMissingException--------------------------------------------------"+infoMissingException);
					throw infoMissingException;

				}
			}
		} else {
			InfoMissingException infoMissingException = new InfoMissingException(HttpStatus.NO_CONTENT + " Data not found, Please ! try again .");
			log.error("infoMissingException--------------------------------------------------"+infoMissingException);
			throw infoMissingException;
		}

		return flag;

	}

	// Get blog with title
	public List<BlogDto> getBlogTitle(String title) {

		List<Blog> blog = repo.findByTitleContainingAllIgnoringCaseAndStatus(title, true);
		List<BlogDto> blogDtoList = new ArrayList<>();
		for (Blog blog2 : blog) {

			if (blog2 != null) {
				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);

				blogDtoList.add(blogToDtoConverter);
			} else {
				UserNotFoundException userNotFoundException = new UserNotFoundException(HttpStatus.NO_CONTENT + "  Data not available, please ! Try again.");
				log.error("userNotFoundException----------------------------"+userNotFoundException);
				throw userNotFoundException;
			}
		}

		return blogDtoList;
	}

	// SEARCH BLOG BY TITLE WITH PAGEINATION
	@Override
	public List<BlogDto> getBlogTitleWithPage(int pageNum, int pageSize, String title) {

		PageRequest pageable = PageRequest.of(pageNum, pageSize);

		List<Blog> blog = repo.findByTitleContainingAllIgnoringCaseAndStatus(pageable, title, true);
		List<BlogDto> blogDtoList = new ArrayList<>();
		for (Blog blog2 : blog) {

			if (blog2 != null) {
				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);

				blogDtoList.add(blogToDtoConverter);
			} else {
				UserNotFoundException userNotFoundException = new UserNotFoundException(HttpStatus.NO_CONTENT + "  Data not available, please ! Try again.");
				log.error("userNotFoundException----------------------------"+userNotFoundException);
				throw userNotFoundException;
			}
		}

		return blogDtoList;
	}

	// get all blog
	@Override
	public List<BlogDto> getBlog(int pageNum, int pageSize) {

		PageRequest pageable = PageRequest.of(pageNum, pageSize);

		List<Blog> blog = repo.findAll(pageable,true);
		if(blog.isEmpty() || blog==null) {
			
				UserNotFoundException e = new UserNotFoundException(HttpStatus.NO_CONTENT + " Data not available, please ! Try again.");
				log.error("Error ocurred -------------------------"+e);
				throw e;
			
		}

		log.info("----------------------------------" + blog);

		List<BlogDto> blogDtoList = new ArrayList<>();

		for (Blog blog2 : blog) {

			if (blog2 != null) {
				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);

				blogDtoList.add(blogToDtoConverter);
			}
		}

		return blogDtoList;
	}

	// Get blog by userId or we can say unique userName
	@Override
	public List<BlogDto> getByAutherID(String userName) {

		List<Blog> byAuthorId = repo.findByAuthorId(userName);

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
			
			UserNotFoundException userNotFoundException = new UserNotFoundException(HttpStatus.NO_CONTENT + "  Data not available, please ! Try again.");
			log.error("userNotFoundException----------------------------"+userNotFoundException);
			throw userNotFoundException;
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
			UserNotFoundException userNotFoundException = new UserNotFoundException(HttpStatus.NO_CONTENT + "  Data not available, please ! Try again.");
			log.error("userNotFoundException----------------------------"+userNotFoundException);
			throw userNotFoundException;
		}
	}

	@Override
	@Deprecated
	public List<BlogDto> getByVisibility(long departmentId) {

		List<Blog> bydepartmentId = repo.getByDepartmentId(departmentId);

		List<BlogDto> dtos = new ArrayList<>();

		if (!bydepartmentId.isEmpty()) {

			for (Blog blog : bydepartmentId) {

				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog);
				dtos.add(blogToDtoConverter);
			}

			return dtos;
		} else {
			UserNotFoundException userNotFoundException = new UserNotFoundException(HttpStatus.NO_CONTENT + "  Data not available, please ! Try again.");
			log.error("userNotFoundException----------------------------"+userNotFoundException);
			throw userNotFoundException;

		}

	}

}
