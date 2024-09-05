package com.in2it.commentandlikeservice.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.in2it.blogservice.dto.BlogDto;

import jakarta.validation.Valid;

@FeignClient(name="blog-service" , url="http://localhost:8282/spike/blog")
public interface BlogFeign {

	@GetMapping("/getByBlogId/{blogId}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable(value = "blogId") @Valid UUID blogId) ;
	@PutMapping("/updateComment")
	public ResponseEntity<BlogDto> updateComment(@RequestParam UUID blogId, @RequestParam Long totalCommentCount);
}
