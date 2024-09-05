package com.in2it.commentandlikeservice.controller;

import java.time.LocalDate;
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

import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;
import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.dto.CommentUpdateDto;
import com.in2it.commentandlikeservice.model.Comment;
import com.in2it.commentandlikeservice.payload.UserNotFoundException;
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
	public ResponseEntity<CommentDto> createComment(@ModelAttribute CommentDto commentDto, @PathVariable UUID blogId) {
		try {
			CommentDto createComment = commentService.saveComment(commentDto, blogId, commentDto.getMedia());
			return ResponseEntity.status(HttpStatus.CREATED).body(createComment);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	
	
	@PutMapping(path = "/update/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentUpdateDto updateDto,
			@Valid @PathVariable("commentId") String commentId) {

		Comment comment = commentService.getByCommentId(commentId);

		if (comment.getId().equals(commentId)) {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(updateDto, commentId));
		} else {
			throw new UserNotFoundException(
					" Insufficient information, Please ! try again with sufficient information.");
		}
	}

	
	
	@GetMapping("/get-all/{blogId}, method = RequestMethod.GET")
	public ResponseEntity<List<CommentDto>> getCommentByBlogId(@PathVariable UUID blogId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.getByBlogId(blogId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/get/{commentId}")
	public ResponseEntity<Comment> getCommentById(@PathVariable String commentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.getByCommentId(commentId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/delete/{blogId}/{commentId}")
	public ResponseEntity<List<Comment>> deleteCommentByBlogId(@PathVariable UUID blogId,
			@PathVariable String commentId) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteByBlogId(blogId, commentId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

}
