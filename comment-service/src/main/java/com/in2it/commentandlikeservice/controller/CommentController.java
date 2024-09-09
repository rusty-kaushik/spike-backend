package com.in2it.commentandlikeservice.controller;

import java.util.List;
import java.util.UUID;

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
import com.in2it.commentandlikeservice.exception.UserNotFoundException;
import com.in2it.commentandlikeservice.model.Comment;
import com.in2it.commentandlikeservice.service.CommentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/spike/blog/comment")
@Slf4j
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping(path = "/create/{blogId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<CommentDto> createComment(@ModelAttribute CommentDto commentDto,
			@PathVariable String blogId) {

		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(commentDto, blogId));

	}

	@PutMapping(path = "/update/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentUpdateDto updateDto,
			@Valid @PathVariable("commentId") String commentId) {

		return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(updateDto, commentId));

	}

	@GetMapping("/get-all/{blogId}, method = RequestMethod.GET")
	public ResponseEntity<List<CommentDto>> getCommentByBlogId(@PathVariable String blogId) {

		return ResponseEntity.status(HttpStatus.OK).body(commentService.getByBlogId(blogId));

	}

	@GetMapping("/comment/{commentId}")
	public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable String commentId) {
		return ResponseEntity.ok(commentService.getCommentById(commentId));
	}

	@DeleteMapping("/delete/{blogId}/{commentId}")
	public ResponseEntity<CommentDto> deleteCommentByBlogId(@PathVariable String blogId,
			@PathVariable String commentId) {

		return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteByBlogId(blogId, commentId));

	}

}
