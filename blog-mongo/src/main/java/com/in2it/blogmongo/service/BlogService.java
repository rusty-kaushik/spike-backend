package com.in2it.blogmongo.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogmongo.dto.BlogDto;
import com.in2it.blogmongo.model.Blog;

@Component
public interface BlogService {

	Blog createBlog(Blog blog);
	
//	Blog updateBlog(Blog blog, String blogId);
	
	BlogDto deleteBlog(String blogId);
	
	List<BlogDto> deleteByAuthorId(String authorId);
	

	List<BlogDto> getAllBlogs();
	
	List<BlogDto> getAllActiveBlogs();
	
	BlogDto getBlogByBlogId(String blogId);
	
	List<BlogDto> getBlogsByAuthorId(String authorId);
	
	List<BlogDto> getBlogsByTitle(String title);
	


	BlogDto addBlog(String title, String content, String visiblity, List<MultipartFile> media, String authorid, List<String> tags, Long departmentId, Long teamId);
	BlogDto updateBlog(String blogId, String title, String content, String visiblity, List<String> tags);

}
