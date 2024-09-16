package com.in2it.commentandlikeservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.in2it.commentandlikeservice.dto.BlogDto;

import jakarta.validation.Valid;

@FeignClient(name="blog-service" , url="http://99.99.99.185:8282/spike/blog")
public interface BlogFeign {

	@GetMapping("/getByBlogId/{blogId}")
    public ResponseEntity<BlogResponce<BlogDto>>  getBlogById(@PathVariable(value = "blogId") @Valid String blogId) ;
	@PutMapping("/updateComment")
	public ResponseEntity<BlogResponce<BlogDto>> updateComment(@RequestParam String blogId, @RequestParam Long totalCommentCount);
}
