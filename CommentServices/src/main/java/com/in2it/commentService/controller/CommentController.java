package com.in2it.commentService.controller;

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

import com.in2it.commentService.dto.CommentDeleteDto;
import com.in2it.commentService.dto.CommentDto;
import com.in2it.commentService.dto.CommentUpdateDto;
import com.in2it.commentService.dto.CreateCommentDto;
import com.in2it.commentService.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;


	@PostMapping(path = "/post", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<CommentDto> createComment(@ModelAttribute CreateCommentDto commentDto){
		try {
			CommentDto createComment= commentService.saveComment(commentDto);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(createComment);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("getByBlogId/{bId}")
	public ResponseEntity<List<CommentDto>> getCommentByBlogId(@PathVariable("bId") String bId){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.getCommentsByBlogId(bId));
		
	}
	
	


	@GetMapping("/getByUserName/{authorId}")
	public ResponseEntity<List<CommentDto>> getCommentsByAutherId(@PathVariable("authorId") String authorId) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.getCommentsByAutherId(authorId));
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteCommentById(@RequestBody CommentDeleteDto deleteDto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.deleteCommentById(deleteDto));
	}


	@PutMapping(path="/updateComment",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<CommentDto> updateComment( @ModelAttribute CommentUpdateDto updateDto)
	{
		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.updateComment(updateDto));
	}
}
