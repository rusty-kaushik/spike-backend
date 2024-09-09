package com.in2it.commentandlikeservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.commentandlikeservice.dto.BlogDto;
import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.dto.CommentUpdateDto;
import com.in2it.commentandlikeservice.exception.BlogNotFoundException;
import com.in2it.commentandlikeservice.exception.CommentNotFoundException;
import com.in2it.commentandlikeservice.feign.BlogFeign;
import com.in2it.commentandlikeservice.mapper.CommentConvertor;
import com.in2it.commentandlikeservice.model.Comment;
import com.in2it.commentandlikeservice.repository.CommentRepository;
import com.in2it.commentandlikeservice.service.impl.CommentServiceImpl;

public class CommentServiceImplTest {

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private CommentConvertor objectMapper;

	@Mock
	private BlogFeign feign;

	@InjectMocks
	private CommentServiceImpl commentService;

	CommentDto commentDto;
	Comment comment;
	BlogDto blogDto;
	MultipartFile multipartFile;
	FileInputStream input;

	File file = new File("D:\\path\\media\\CommentImage\\1725535141823Screenshot 2024-08-29 114124.png");

	@BeforeEach
	void setUp() {
		try {
			input = new FileInputStream(file);
			multipartFile = new MockMultipartFile("fileItem", file.getName(), "image/png", IOUtils.toByteArray(input));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		MockitoAnnotations.openMocks(this);
		commentDto = new CommentDto();
		commentDto.setContent("This is a comment");
		commentDto.setMedia(List.of(multipartFile));
		commentDto
				.setMediaPath(List.of("D:\\path\\media\\CommentImage\\1725535141823Screenshot 2024-08-29 114124.png"));

		blogDto = new BlogDto();
		blogDto.setId("1");
		blogDto.setCommentCount(5);

		comment = new Comment();
		comment.setId("1");
		comment.setContent("This is a comment");
		comment.setMedia(List.of("1725535141823Screenshot 2024-08-29 114124.png"));
		comment.setMediaPath(List.of("D:\\path\\media\\CommentImage\\1725535141823Screenshot 2024-08-29 114124.png"));

	}

	@Test
	void saveCommentSuccessTest() {

		when(feign.getBlogById("1")).thenReturn(ResponseEntity.ok(blogDto));
		when(objectMapper.dtoToCommentConvertor(commentDto)).thenReturn(comment);
		when(commentRepository.findByBlogIdAndStatus("1", "Active")).thenReturn(Collections.emptyList());
		when(commentRepository.save(comment)).thenReturn(comment);
		when(objectMapper.commentToDtoConvertor(comment)).thenReturn(commentDto);

		System.out.println("Comment DTO " + commentDto);
		CommentDto savedComment = commentService.saveComment(commentDto, "1");

		System.out.println(savedComment.toString());

		assertNotNull(savedComment);
		assertEquals("This is a comment", savedComment.getContent());
		verify(feign, times(1)).getBlogById("1");
		verify(commentRepository, times(1)).save(comment);

	}

	@Test
	void saveCommentBlogNotFoundTest() {

		when(feign.getBlogById("1")).thenThrow(new BlogNotFoundException("Blog Id is not valid.."));

		assertThrows(BlogNotFoundException.class, () -> {
			commentService.saveComment(commentDto, "1");
		});
	}

	@Test
	void updateCommentSuccessTest() {

		String commentId = "1";
		CommentUpdateDto updateDto = new CommentUpdateDto();
		updateDto.setContent("Updated content");

		Comment existingComment = new Comment();
		existingComment.setId(commentId);
		existingComment.setContent("Old content");
		existingComment.setUserName("user");

		Comment updatedComment = new Comment();
		updatedComment.setId(commentId);
		updatedComment.setContent("Updated content");
		updatedComment.setUserName("user");
		updatedComment.setUpdatedDateTime(LocalDateTime.now());

		CommentDto commentDto = new CommentDto();
		commentDto.setContent("Updated content");

	
		when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
		when(commentRepository.save(existingComment)).thenReturn(updatedComment);
		when(objectMapper.commentToDtoConvertor(updatedComment)).thenReturn(commentDto);

		CommentDto result = commentService.updateComment(updateDto, commentId);

		assertNotNull(result);
		assertEquals("Updated content", result.getContent());
		assertNotNull(existingComment.getUpdatedDateTime()); 
		verify(commentRepository, times(1)).findById(commentId);
		verify(commentRepository, times(1)).save(existingComment);
		verify(objectMapper, times(1)).commentToDtoConvertor(updatedComment);
	}

	@Test
	void updateCommentCommentNotFoundTest() {
		CommentUpdateDto updateDto = new CommentUpdateDto();

		when(commentRepository.findById("1")).thenReturn(Optional.empty());

		assertThrows(CommentNotFoundException.class, () -> {
			commentService.updateComment(updateDto, "1");
		});
	}

	@Test
	void getByBlogIdSuccessTest() {

		when(commentRepository.findByBlogIdAndStatus("1", "Active")).thenReturn(Collections.singletonList(comment));
		when(objectMapper.commentToDtoConvertor(comment)).thenReturn(commentDto);

		List<CommentDto> result = commentService.getByBlogId("1");

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("This is a comment", result.get(0).getContent());
	}

	@Test
	void getByBlogIdNoCommentsTest() {
		when(commentRepository.findByBlogIdAndStatus("1", "Active")).thenReturn(Collections.emptyList());

		assertThrows(CommentNotFoundException.class, () -> {
			commentService.getByBlogId("1");
		});
	}

	@Test
	void deleteByBlogIdSuccessTest() {

		comment.setStatus("Active");

		when(feign.getBlogById("1")).thenReturn(ResponseEntity.ok(blogDto));
		when(commentRepository.findByIdAndStatus("1", "Active")).thenReturn(Optional.of(comment));
		when(commentRepository.save(comment)).thenReturn(comment);
		when(objectMapper.commentToDtoConvertor(comment)).thenReturn(commentDto);

		CommentDto result = commentService.deleteByBlogId("1", "1");

		assertNotNull(result);
		assertEquals("InActive", comment.getStatus());
	}

	@Test
	void deleteByBlogIdBlogNotFoundTest() {
		when(feign.getBlogById("1")).thenThrow(new BlogNotFoundException("Blog Id is not valid.."));

		assertThrows(BlogNotFoundException.class, () -> {
			commentService.deleteByBlogId("1", "1");
		});
	}

	@Test
	void getCommentByIdSuccessTest() {

		when(commentRepository.findById("1")).thenReturn(Optional.of(comment));
		when(objectMapper.commentToDtoConvertor(comment)).thenReturn(commentDto);

		CommentDto result = commentService.getCommentById("1");

		assertNotNull(result);
		assertEquals("This is a comment", result.getContent());
	}

	@Test
	void getCommentByIdCommentNotFoundTest() {
		when(commentRepository.findById("1")).thenReturn(Optional.empty());

		assertThrows(CommentNotFoundException.class, () -> {
			commentService.getCommentById("1");
		});
	}

}

