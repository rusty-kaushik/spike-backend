package com.in2it.commentandlikeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.dto.CommentUpdateDto;
import com.in2it.commentandlikeservice.response.Response;
import com.in2it.commentandlikeservice.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/spike/blog")
@Slf4j
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Operation(summary = "API to add a comment on blog")
	@PostMapping(path = "/create/comment/{blogId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response<CommentDto>> createComment(@ModelAttribute CommentDto commentDto,
			@PathVariable String blogId) {

		CommentDto comment = commentService.saveComment(commentDto, blogId);

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response<CommentDto>(comment,
				"Comment successfully created", HttpStatus.CREATED, HttpStatus.CREATED.value()));

	}

	@Operation(summary = "API to update the comment of a blog")
	@PutMapping(path = "/update/comment/{commentId}")
	public ResponseEntity<Response<CommentDto>> updateComment(@RequestBody CommentUpdateDto updateDto,
			@Valid @PathVariable("commentId") String commentId) {

		CommentDto comment = commentService.updateComment(updateDto, commentId);

		return ResponseEntity.status(HttpStatus.OK).body(new Response<CommentDto>(comment,
				"Comment successfully updated", HttpStatus.OK, HttpStatus.OK.value()));

	}

	@Operation(summary = "API to get all the comment of a blog with blogId")
	@GetMapping("/get-all/comment/{blogId}")
	public ResponseEntity<Response<List<CommentDto>>> getCommentByBlogId(@PathVariable String blogId) {

		List<CommentDto> comments = commentService.getByBlogId(blogId);

		return ResponseEntity.status(HttpStatus.OK).body(new Response<List<CommentDto>>(comments,
				"Found all the comments of this blog " + blogId, HttpStatus.OK, HttpStatus.OK.value()));
	}

	@Operation(summary = "API to find comment with commentId")
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<Response<CommentDto>> getCommentByCommentId(@PathVariable String commentId) {
		CommentDto comment = commentService.getCommentById(commentId);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response<CommentDto>(comment, "Comment found  ", HttpStatus.OK, HttpStatus.OK.value()));
	}

	@Operation(summary = "API to delete the comment of a blog with commentId")
	@DeleteMapping("/delete/comment/{blogId}/{commentId}")
	public ResponseEntity<Response<Boolean>> deleteCommentByCommentId(@PathVariable String blogId,
			@PathVariable String commentId) {
		Boolean flag = false;

		CommentDto deleteByCommentId = commentService.deleteByCommentId(blogId, commentId);

		if (deleteByCommentId != null) {
			flag = true;
		}

		Response<Boolean> response = new Response<Boolean>(flag, "Deleted Successfully", HttpStatus.OK,
				HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@Operation(summary = "API to delete all the comment of a blog")
	@DeleteMapping("deleteByBlogId/{blogId}")
	public ResponseEntity<Response<Boolean>> deleteCommentsByblogId(@PathVariable String blogId) {
		boolean deleted = commentService.deleteCommentsByblogId(blogId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Response<Boolean>(deleted,
				"All comments are deleted", HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value()));

	}

}
