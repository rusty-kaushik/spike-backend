package com.in2it.blogmongo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogmongo.dto.CommentDto;
import com.in2it.blogmongo.model.Comment;
import com.in2it.blogmongo.service.CommentService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	CommentService service;
	
	@Hidden
	@PostMapping
	public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
		
		Comment savedComment = service.addComment(comment);
		return ResponseEntity.ok(savedComment);
	}

	@Hidden
	@GetMapping
	public ResponseEntity<List<CommentDto>> getAllComments() {
		
		List<CommentDto> allComments = service.getAllComments();
		return ResponseEntity.ok(allComments);
	}
	
	@PostMapping(path =  "/comment-on-blog", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public CommentDto commentOnBlog(@RequestParam("content") String content,@RequestParam(name = "media", required = false) MultipartFile media,@RequestParam("blogId") String blogId,@RequestParam() String authorid) {
		return service.commentOnBlog(content, media, blogId, authorid);
	}


	@GetMapping("/get-all-comments")
	public List<CommentDto> getAllActiveComments() {
		return service.getAllActiveComments();
	}
		

	@GetMapping("/blog/{blogId}/comment")
	public List<CommentDto> getCommentsByBlogId(@PathVariable String blogId) {
		return service.getCommentsByBlogId(blogId);
	}


	@GetMapping("/author/{authorId}/comment")
	public List<CommentDto> getCommentsByAuthorId(@PathVariable String authorId) {
		System.out.println("Author id in controller "+ authorId);
		return service.getCommentsByAuthorId(authorId);
	}
	
	
	

	@DeleteMapping("delete-comment/{id}")
	public CommentDto deleteComment(@PathVariable String id) {
		return service.deleteComment(id);
	}


	@DeleteMapping("/delete-comment/blog/{blogId}/comment")
	public List<CommentDto> deleteCommentsByBlogId(@PathVariable String blogId) {
		return service.deleteCommentsByBlogId(blogId);
	}


	@DeleteMapping("/delete-comment/author/{authorId}/comment")
	public List<CommentDto> deleteCommentsByAuthorId(@PathVariable String authorId) {
		return service.deleteCommentsByAuthorId(authorId);
	}

		

}
