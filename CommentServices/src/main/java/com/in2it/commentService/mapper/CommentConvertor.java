package com.in2it.commentService.mapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in2it.commentService.dto.CommentDto;
import com.in2it.commentService.dto.CreateCommentDto;
import com.in2it.commentService.model.Comment;


@Component
public class CommentConvertor {

	public Comment dtoToCommentConvertor(CreateCommentDto commentDto) {
		
		List<String> mediaName=new ArrayList<>();
		List<String> mediaPath=new ArrayList<>();
		
		HashMap<String , String> imgNameAndUrl=persistFiles(commentDto.getMedia());
		Set entrySet = imgNameAndUrl.entrySet();
		Iterator iterator=entrySet.iterator();
		while(iterator.hasNext())
		{
			Map.Entry<String, String> pair=(Map.Entry<String, String>)iterator.next();
			mediaName.add(pair.getKey());
			mediaPath.add(pair.getValue());
		}

	    Comment comment=new Comment();
		comment.setAuthorId(commentDto.getAuthorId());
		comment.setBlogId(commentDto.getBlogId());
		comment.setContent(commentDto.getContent());
		comment.setMedia(mediaName);
		comment.setMediaPath(mediaPath);
		comment.setCreatedAt(LocalDateTime.now());
		comment.setCreatedBy(commentDto.getAuthorId());
		comment.setStatus("Active");
		return comment;
	}
	
	public CommentDto commentToDtoConvertor(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setAuthorId(comment.getAuthorId());
		commentDto.setBlogId(comment.getBlogId());
		commentDto.setContent(comment.getContent());
		commentDto.setId(comment.getId());
		commentDto.setMediaName(comment.getMedia());
		List<String> filesPath=new ArrayList<>();
//		for(String fileName:comment.getMedia()) {
//			
//			filesPath.add(ServletUriComponentsBuilder.fromCurrentContextPath().path("/CommentImage/").path(fileName).toUriString());
//		}
		
		for(String filePath:comment.getMediaPath()) {
			
			File file=new File(filePath);
			try {

		        byte[] fileContent = FileUtils.readFileToByteArray(file);
		        String encodedString = Base64.getEncoder().encodeToString(fileContent);
		        filesPath.add(encodedString);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		commentDto.setMediaPath(filesPath);
		commentDto.setCreatedAt(comment.getCreatedAt());
		commentDto.setCreatedBy(comment.getCreatedBy());
		commentDto.setUpdatedAt(comment.getUpdatedAt());
		commentDto.setUpdatedBy(comment.getUpdatedBy());
		return commentDto;
	}
	
	public HashMap<String , String> persistFiles(List<MultipartFile> media)
	{
		HashMap<String , String> imgNameAndUrl=new HashMap<>();
		File file=new File("src\\main\\resources\\static\\CommentImage");
		if(!file.isDirectory())
		{
			try {
				Files.createDirectories(Path.of("src\\main\\resources\\static\\CommentImage"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    if(file.isDirectory())
	    {
	    	try 
	    	{
				String path=file.getAbsolutePath();
				for(MultipartFile image:media)
				{
					String imageName=ThreadLocalRandom.current().nextLong()+image.getOriginalFilename();
					String path1=path+"\\"+imageName;
					
					image.transferTo(new File(path1));
					imgNameAndUrl.put(imageName, path1);
				}
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
	    return imgNameAndUrl;
	}

}
