package com.in2it.commentandlikeservice.mapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.model.Comment;

@Component
public class CommentConvertor {

	public Comment dtoToCommentConvertor(CommentDto commentDto) {

		List<String> mediaName = new ArrayList();
		List<String> mediaPath = new ArrayList<>();

		File file = new File("D:\\path\\media\\CommentImage");

		if (!file.isDirectory()) {
			try {
				Files.createDirectories(Path.of("D:\\path\\media\\CommentImage"));
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		if (file.isDirectory()) {
			try {
				String path = file.getAbsolutePath();
				for (MultipartFile image : commentDto.getMedia()) {
					String path1 = path + "\\" + image.getOriginalFilename();
					mediaName.add(image.getOriginalFilename());
					image.transferTo(new File(path1));
					String path2 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/CommentImage/")
							.path(image.getOriginalFilename()).toUriString();
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
			filesPath.add(ServletUriComponentsBuilder.fromCurrentContextPath().path("/CommentImage/").path(fileName)
					.toUriString());
		}
		commentDto.setMediaPath(filesPath);
		return commentDto;
	}


	


}
