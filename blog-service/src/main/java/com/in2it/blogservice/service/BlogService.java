package com.in2it.blogservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;

public interface BlogService {
	
	
	BlogDto saveBlog(BlogDto blogDto,List<MultipartFile> img);
//	public BlogDto saveBlog(BlogDto blogDto);
	BlogDto updateBlog(BlogUpdateDto updateDto, Long id);
	Boolean deleteBlog(Long id);
	

	List<BlogDto> getBlog();
	List<BlogDto> getByAutherID(long id);
	List<BlogDto> getBlogTitle(String title);
	BlogDto getBlogById(Long id);
	
	

}
