package com.in2it.commentandlikeservice;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.in2it.commentandlikeservice.feign.BlogResponce;
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
	BlogResponce<BlogDto> blogResponce;

//	File file = new File("D:\\path\\media\\CommentImage\\1725535141823Screenshot 2024-08-29 114124.png");

	@BeforeEach
	void setUp() {

		// Create a temporary file
		Path tempFile = null;
		try {
			tempFile = Files.createTempFile("fakefile", ".png");
		} catch (IOException e) {

			e.printStackTrace();
		}
		File file = tempFile.toFile();

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

		blogResponce = new BlogResponce<BlogDto>();
		blogResponce.setData(blogDto);

	}

	@Test
	void saveCommentSuccessTest() {

		when(feign.getBlogById("1")).thenReturn(ResponseEntity.ok(blogResponce));
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

//	@Test
//	void updateCommentSuccessTest() {
//
//		String commentId = "1";
//
//		Comment existingComment = new Comment();
//		existingComment.setId(commentId);
//		existingComment.setContent("Old content");
//		existingComment.setUserName("user");
//
//		Comment updatedComment = new Comment();
//		updatedComment.setId(commentId);
//		updatedComment.setContent("Updated content");
//		updatedComment.setUserName("user");
//		updatedComment.setUpdatedDateTime(LocalDateTime.now());
//
//		CommentDto commentDto = new CommentDto();
//		commentDto.setContent("Updated content");
//
//		when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
//		when(commentRepository.save(existingComment)).thenReturn(updatedComment);
//		when(objectMapper.commentToDtoConvertor(updatedComment)).thenReturn(commentDto);
//
//		CommentDto result = commentService.updateComment("Updated content", commentId);
//
//		assertNotNull(result);
//		assertEquals("Updated content", result.getContent());
//		assertNotNull(existingComment.getUpdatedDateTime());
//		verify(commentRepository, times(1)).findById(commentId);
//		verify(commentRepository, times(1)).save(existingComment);
//		verify(objectMapper, times(1)).commentToDtoConvertor(updatedComment);
//	}
//
//	@Test
//	void updateCommentCommentNotFoundTest() {
//
//		when(commentRepository.findById("1")).thenReturn(Optional.empty());
//
//		assertThrows(CommentNotFoundException.class, () -> {
//			commentService.updateComment("updated comment", "1");
//		});
//	}

	@Test
	void getByBlogIdSuccessTest() {

		when(commentRepository.findByBlogIdAndStatus("1", "Active")).thenReturn(Collections.singletonList(comment));
		when(objectMapper.commentToDtoConvertor(comment)).thenReturn(commentDto);

		List<CommentDto> result = commentService.getByBlogId("1");

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("This is a comment", result.get(0).getContent());
	}

//	@Test
//	void getByBlogIdNoCommentsTest() {
//		when(commentRepository.findByBlogIdAndStatus("1", "Active")).thenReturn(Collections.emptyList());
//
//		assertThrows(CommentNotFoundException.class, () -> {
//			commentService.getByBlogId("1");
//		});
//	}

//	@Test
//	void deleteByCommentIdSuccessTest() {
//
//		comment.setStatus("Active");
//
//		when(feign.getBlogById("1")).thenReturn(ResponseEntity.ok(blogResponce));
//		when(commentRepository.findByIdAndStatus("1", "Active")).thenReturn(Optional.of(comment));
//		when(commentRepository.save(comment)).thenReturn(comment);
//		when(objectMapper.commentToDtoConvertor(comment)).thenReturn(commentDto);
//
//		CommentDto result = commentService.deleteByCommentId("1", "1");
//
//		assertNotNull(result);
//		assertEquals("InActive", comment.getStatus());
//	}
//
//	@Test
//	void deleteByCommentIdBlogNotFoundTest() {
//		when(feign.getBlogById("1")).thenThrow(new BlogNotFoundException("Blog Id is not valid.."));
//
//		assertThrows(BlogNotFoundException.class, () -> {
//			commentService.deleteByCommentId("1", "1");
//		});
//	}

	@Test
	void getCommentByIdSuccessTest() {
		comment.setStatus("Active");
		when(commentRepository.findByIdAndStatus("1", "Active")).thenReturn(Optional.of(comment));
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

	@Test
	void deleteCommentsByBlogIdSuccessTest() {

		String blogId = "123";
		Comment comment1 = new Comment();
		comment1.setStatus("Active");
		Comment comment2 = new Comment();
		comment2.setStatus("Active");

		List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);

		when(commentRepository.findByBlogIdAndStatus(blogId, "Active")).thenReturn(comments);

		boolean result = commentService.deleteCommentsByblogId(blogId);

		assertTrue(result);
		assertEquals("InActive", comment1.getStatus());
		assertEquals("InActive", comment2.getStatus());
		verify(commentRepository).saveAll(comments);
	}

	@Test
	public void deleteCommentsByBlogIdNoCommentsFoundTest() {
		// Arrange
		String blogId = "123";
		when(commentRepository.findByBlogIdAndStatus(blogId, "Active")).thenReturn(new ArrayList<>());

		BlogNotFoundException thrown = assertThrows(BlogNotFoundException.class, () -> {
			commentService.deleteCommentsByblogId(blogId);
		});

		assertEquals("No comments are available for given blog Id", thrown.getMessage());

	}

}
