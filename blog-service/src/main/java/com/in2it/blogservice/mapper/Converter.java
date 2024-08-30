package com.in2it.blogservice.mapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.model.Blog;

@Component
public class Converter {


	public Blog dtoToBlogConverter(BlogDto dto,List<MultipartFile> img,List<String> mediaPath)
	{
		Blog blog=new Blog();
		blog.setContent(dto.getContent());
		blog.setAuthorId(dto.getAuthorId());
		List<String> mediaNames=new ArrayList<>();
		for(MultipartFile file:dto.getMedia())
		{
			mediaNames.add(file.getOriginalFilename());
		}
//		
		blog.setMedia(mediaNames);
//		blog.setMedia(img.getOriginalFilename());
		blog.setTitle(dto.getTitle());
		blog.setVisiblity(dto.getVisiblity());
//		blog.setMediaPath(dto.getMediaPath());
//		blogDto.setCretedDateTime(LocalDateTime.now());
//		blogDto.setMediaPath(path1);
		blog.setMediaPath(mediaPath);
		
		//set to current date&time
		blog.setCretedDateTime(LocalDateTime.now());
//		blog.setCretedDateTime(dto.getCretedDateTime());
		
//		blog.setProjectId(author.getProjectId());
//		blog.setDepartmentId(author.getDepartmentId());
		blog.setProjectId(dto.getProjectId());
		blog.setDepartmentId(dto.getDepartmentId());
		//set to initial value 
//		blog.setLikeCount(0);
//		blog.setCommentCount(0);
		
		blog.setLikeCount(0);
		blog.setCommentCount(0);
		
		
		return blog;
	}
	
	public BlogDto blogToDtoConverter(Blog blog)
	{
		BlogDto dto=new BlogDto();
		dto.setId(blog.getId());
		dto.setContent(blog.getContent());
		dto.setAuthorId(blog.getAuthorId());
//		dto.setMedia (blog.getMedia());
		dto.setTitle(blog.getTitle());
		dto.setVisiblity(blog.getVisiblity());
		dto.setProjectId(blog.getProjectId());
		dto.setDepartmentId(blog.getDepartmentId());
		
		
		List<String> imgPath=new ArrayList<>();
		
		for(String temp:blog.getMedia()) 
		{
			imgPath.add(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(temp).toUriString());
		}
		dto.setImgPath(imgPath);
		//set to current date&time
//		dto.setCretedDateTime(blog.getCretedDateTime());
		
		//set to initial value 
		dto.setLikeCount(blog.getLikeCount());
		dto.setCommentCount(blog.getCommentCount());
		
		
		
		return dto;
	}
	
}



