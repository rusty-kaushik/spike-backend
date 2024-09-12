package com.in2it.commentandlikeservice.mapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.model.Comment;

@Component
public class CommentConvertor {
//	String dirPath="D:\\path\\media\\CommentImage";
	@Value("${media.path}")
	String dirPath;
	public Comment dtoToCommentConvertor(CommentDto commentDto) {

		List<String> mediaName = new ArrayList();
		List<String> mediaPath = new ArrayList<>();

//		File file = new File("D:\\path\\media\\CommentImage");
		
		
		File file = new File(dirPath);
		if (!file.isDirectory()) {
			try {
				Files.createDirectories(Path.of(dirPath));
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		if (file.isDirectory()) {
			try {
				String path = file.getAbsolutePath();
				for (MultipartFile image : commentDto.getMedia()) {
					String uniqueFileName =  System.currentTimeMillis() + image.getOriginalFilename();
					String path1 = path + "\\" + uniqueFileName;
					System.out.println(path1);
					mediaName.add(uniqueFileName);
					image.transferTo(new File(path1));
//					String path2 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/CommentImage/")
//							.path(image.getOriginalFilename()).toUriString();
					String path2 = dirPath+"\\"+uniqueFileName;
					mediaPath.add(path2);

					System.out.println("path" + path);
					System.out.println("path1" + path1);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Comment comment = new Comment();

		comment.setContent(commentDto.getContent());
		comment.setBlogId(commentDto.getBlogId());
		comment.setMedia(mediaName);
		comment.setMediaPath(mediaPath);
		comment.setUserName(commentDto.getUserName());
		comment.setCreatedDate(LocalDateTime.now());
		comment.setStatus("Active");

		return comment;
	}

	public CommentDto commentToDtoConvertor(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setBlogId(comment.getBlogId());
		commentDto.setContent(comment.getContent());
		commentDto.setId(comment.getId());

		commentDto.setUserName(comment.getUserName());

		commentDto.setCreatedDate(comment.getCreatedDate());

		List<String> filesPath = new ArrayList<>();

		for (String fileName : comment.getMedia()) {
//			filesPath.add(ServletUriComponentsBuilder.fromCurrentContextPath().path("/CommentImage/").path(fileName)
//					.toUriString());
			filesPath.add(dirPath+"\\"+fileName);
		}
		
		List<String> mediaData = new ArrayList<>();
		for (String filePath : comment.getMediaPath()) {

			File file = new File(filePath);
			try {

				byte[] fileContent = FileUtils.readFileToByteArray(file);
				String encodedString = Base64.getEncoder().encodeToString(fileContent);
				mediaData.add(encodedString);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		commentDto.setMediaPath(filesPath);
		commentDto.setMediaData(mediaData);
		return commentDto;
	}

	
	

}
