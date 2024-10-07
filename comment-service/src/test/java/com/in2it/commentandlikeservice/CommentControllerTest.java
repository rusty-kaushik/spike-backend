package com.in2it.commentandlikeservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.in2it.commentandlikeservice.controller.CommentController;
import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.dto.CommentUpdateDto;
import com.in2it.commentandlikeservice.response.Response;
import com.in2it.commentandlikeservice.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

	@Mock
	private CommentService commentService;

	@InjectMocks
	private CommentController commentController;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void createCommentTest() throws Exception {
		CommentDto commentDto = new CommentDto();
		commentDto.setContent("This is a comment");
		when(commentService.saveComment(any(CommentDto.class), anyString())).thenReturn(commentDto);
		ResponseEntity<Response<CommentDto>> response = commentController.createComment(commentDto,
				UUID.randomUUID().toString());
		assertEquals(commentDto, response.getBody().getData());
	}

//	@Test
//	void updateCommentTest() throws Exception {
//		CommentUpdateDto updateDto = new CommentUpdateDto();
//		updateDto.setContent("Updated content");
//
//		CommentDto commentDto = new CommentDto();
//		commentDto.setContent("Updated content");
//		commentDto.setCreatedDate(LocalDateTime.now());
//
//		when(commentService.updateComment(any(CommentUpdateDto.class), anyString())).thenReturn(commentDto);
//
//		ResponseEntity<Response<CommentDto>> response = commentController.updateComment(updateDto,
//				UUID.randomUUID().toString());
//		assertEquals(commentDto, response.getBody().getData());
//	}

	@Test
	void getCommentByBlogIdTest() throws Exception {
		CommentDto commentDto = new CommentDto();
		commentDto.setContent("Comment content");

		List<CommentDto> comments = Collections.singletonList(commentDto);

		when(commentService.getByBlogId(anyString())).thenReturn(comments);

		ResponseEntity<Response<List<CommentDto>>> blogComments = commentController.getCommentByBlogId("sdsdfsf");
		assertEquals(comments, blogComments.getBody().getData());

	}

	@Test
	void getCommentByCommentIdTest() throws Exception {
		CommentDto commentDto = new CommentDto();
		commentDto.setContent("Comment content");

		when(commentService.getCommentById(anyString())).thenReturn(commentDto);

		ResponseEntity<Response<CommentDto>> comment = commentController.getCommentByCommentId("dfkdjsfkjf");

		assertEquals(commentDto, comment.getBody().getData());

	}

//	@Test
//	void deleteCommentByCommentIdTest() throws Exception {
//		CommentDto commentDto = new CommentDto();
//		commentDto.setContent("Deleted comment");
//
//		when(commentService.deleteByCommentId(anyString(), anyString())).thenReturn(commentDto);
//		ResponseEntity<Response<Boolean>> deletedComment = commentController.deleteCommentByCommentId("dsadadad",
//				"sadadadsad");
//
//		assertEquals(true, deletedComment.getBody().getData());
//
//	}

	
	@Operation(summary = "API to delete all the comment of a blog")
	@Test
	void deleteCommentsByblogIdTest() {

		String blogId = "121";
		when(commentService.deleteCommentsByblogId(anyString())).thenReturn(true);

		ResponseEntity<Response<Boolean>> responce = commentController.deleteCommentsByblogId(blogId);

		assertEquals(true, responce.getBody().getData());

	}
}
