package com.in2it.blogservice.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogservice.customException.IdInvalidException;
import com.in2it.blogservice.customException.UserNotFoundException;
import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;
import com.in2it.blogservice.mapper.Converter;
import com.in2it.blogservice.model.Blog;
import com.in2it.blogservice.repository.BlogRepository;
import com.in2it.blogservice.service.BlogService;

import io.swagger.v3.oas.models.Paths;

@Service
@Component
public class BlogServiceImpl implements BlogService {

	@Autowired
	private Converter objectMapper;

	@Autowired
	private BlogRepository repo;
//	@Autowired
//	private AuthorRepository authorRepo;
	


	@Override
	public BlogDto saveBlog(BlogDto blogDto, List<MultipartFile> img) {

		

//		blogDto.setLikeCount(0);
//		blogDto.setCommentCount(0);
//		Author author=authorRepo.getById(blogDto.getAuthorId());
		
//		Blog save = repo.save(objectMapper.dtoToBlogConverter(blogDto));
		
		File file=new File("src\\main\\resources\\static\\image");
		
		List<String> fullPath=new ArrayList<>();
		if(!file.isDirectory())
		{
			try {
				Files.createDirectories(Path.of("src\\main\\resources\\static\\image"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    if(file.isDirectory())
	    {
	    	try 
	    	{
				String path=file.getAbsolutePath();
				for(MultipartFile f1:img)
				{
					String path1=path+"\\"+f1.getOriginalFilename();
					f1.transferTo(new File(path1));
					fullPath.add(path1);
				}
//				fullPath=path+"\\"+img.getOriginalFilename();
//				img.transferTo(new File(fullPath));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

		Blog blog = repo.save(objectMapper.dtoToBlogConverter(blogDto,img,fullPath));
		
		return objectMapper.blogToDtoConverter(blog);
	}

	@Override
	public BlogDto updateBlog(BlogUpdateDto updateDto, Long id) {
		Blog blog=repo.getByBlogId(updateDto.getId());

		if(!updateDto.getContent().isEmpty())blog.setContent(updateDto.getContent());
		if(!updateDto.getVisiblity().isEmpty())blog.setVisiblity(updateDto.getVisiblity());
		List<MultipartFile> images=new ArrayList<>();
		if(updateDto.getMedia()!=null)
		{
			File file=new File("src\\main\\resources\\static\\image");
			
			List<String> fullPath=new ArrayList<>();
			List<String> fileName=new ArrayList<>();
			if(!file.isDirectory())
			{
				try {
					Files.createDirectories(Path.of("src\\main\\resources\\static\\image"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		    if(file.isDirectory())
		    {
		    	try 
		    	{
					String path=file.getAbsolutePath();
					for(MultipartFile f1:updateDto.getMedia())
					{
						String path1=path+"\\"+f1.getOriginalFilename();
						f1.transferTo(new File(path1));
						fileName.add(f1.getOriginalFilename());
						fullPath.add(path1);
					}
//					fullPath=path+"\\"+img.getOriginalFilename();
//					img.transferTo(new File(fullPath));
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    blog.setMediaPath(fullPath);
		    blog.setMedia(fileName);
		}
		
		return objectMapper.blogToDtoConverter(repo.save(blog));
	}

	@Override
	public Boolean deleteBlog(Long id) {
		return null;

	}

	@Override
	public List<BlogDto> getBlogTitle(String title) {

		List<Blog> blog = repo.findByTitle(title);

		List<BlogDto> blogDtoList = new ArrayList<>();

		for (Blog blog2 : blog) {

			if (blog2 != null) {
				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);
				blogDtoList.add(blogToDtoConverter);
			} else {
				throw new UserNotFoundException(
						HttpStatus.NO_CONTENT + "Content not avelable, please ! Try again.");
			}
		}

		return blogDtoList;
	}

	@Override
	public List<BlogDto> getBlog() {
		
		List<Blog> blog = repo.findAll();

		List<BlogDto> blogDtoList = new ArrayList<>();

		for (Blog blog2 : blog) {

			if (blog2 != null) {
				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);
				blogDtoList.add(blogToDtoConverter);
			} else {
				throw new UserNotFoundException(
						HttpStatus.NO_CONTENT + "Content not avelable, please ! Try again.");
			}
		}

		return blogDtoList;
	}



	public List<BlogDto> getByAutherID(long id) {
		
		List<Blog> byAuthorId = repo.findByAuthorId(id);
		
		List<BlogDto> blogDtoList = new ArrayList<>();

		for (Blog blog2 : byAuthorId) {

			if (blog2 != null) {
				BlogDto blogToDtoConverter = objectMapper.blogToDtoConverter(blog2);
				blogDtoList.add(blogToDtoConverter);
			} else {
				throw new UserNotFoundException(
						HttpStatus.NO_CONTENT + "Content not available, please ! Try again.");
			}
		}
		
		return blogDtoList;
	}

	@Override
	public BlogDto getBlogById(Long id) {

		Blog blog = repo.findById(id).orElseThrow(() -> new IdInvalidException("Please ! correct id ."));
		if (blog != null) {
			BlogDto blogDto = objectMapper.blogToDtoConverter(blog);
			return blogDto;
		} else {
			throw new UserNotFoundException(
					HttpStatus.NO_CONTENT + "Content not avelable, please ! Try again.");
		}
	}

	
	
	
	

}
