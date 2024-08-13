package com.in2it.commentandlikeservice.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.model.Comment;
import com.in2it.commentandlikeservice.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	//hello everyone 
	
	@PostMapping(path = "/post", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<CommentDto> createComment(@ModelAttribute CommentDto commentDto) {
		try {

			CommentDto createComment = commentService.saveComment(commentDto, commentDto.getFile());

			return ResponseEntity.status(HttpStatus.CREATED).body(createComment);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

//	@GetMapping(path = "/get")
//	public ResponseEntity<List<CommentDto>> getAllComment() {
//		try {
//			return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComment());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}

	@GetMapping("/getByBlogId/{id}")
	public ResponseEntity<List<CommentDto>> getCommentByBlogId(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.getByBlogId(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

//	@GetMapping("/getByUserName/{username}")
//	public ResponseEntity<List<CommentDto>> getCommentByUserName(@PathVariable String username) {
//		try {
//			return ResponseEntity.status(HttpStatus.OK).body(commentService.getByUserName(username));
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteCommentById(@PathVariable Long id) {
		
		return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteCommentId(id));

	}

	@DeleteMapping("/deleteByBlogId/{blogId}/{commentId}")
	public ResponseEntity<List<Comment>> deleteCommentByBlogId(@PathVariable Long blogId,
			@PathVariable Long commentId) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteByBlogId(blogId, commentId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	public Comment updateComment(Comment comment, Long id) {
		return null;

	}
}
