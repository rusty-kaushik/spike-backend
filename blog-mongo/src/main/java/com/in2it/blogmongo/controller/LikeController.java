package com.in2it.blogmongo.controller;

import java.nio.file.NoSuchFileException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.blogmongo.model.Like;
import com.in2it.blogmongo.service.LikeService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/likes")
public class LikeController {
	
	@Autowired
	LikeService service;
	
	@Hidden
	@PostMapping
	public ResponseEntity<Like> addLike(@RequestBody Like like) {
		
		Like savedLike = service.addLike(like);
		return ResponseEntity.ok(savedLike);
	}

	@GetMapping
	public ResponseEntity<List<Like>> getAllLikes() {
		
		List<Like> allLikes = service.getAllLikes();
		return ResponseEntity.ok(allLikes);
	}
	
	@PostMapping("/author/{authorId}/blog/{blogId}")
	public Like likeBlog(@PathVariable String authorId, @PathVariable String blogId, @RequestParam String type) throws NoSuchFileException {
		return service.likeBlog(authorId,blogId,type);
		
	}

}
