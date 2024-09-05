package com.in2it.commentandlikeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.in2it.blogservice.dto.BlogDto;

import jakarta.validation.Valid;

@FeignClient(name="blog-service" , url="http://localhost:8080/in2it-blog")
public interface BlogFeign {

	@GetMapping("/getByBlogId/{blogId}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable(value = "blogId") @Valid Long blogId) ;
	@PutMapping("/updateComment")
	public ResponseEntity<BlogDto> updateComment(@RequestParam Long blogId, @RequestParam Long totalCommentCount);
}
