package com.in2it.blogservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.in2it.blogservice.dto.Response;

@FeignClient(name = "comment-service", url = "${spike.comment-service}")
public interface FeignClientForComment {

	@DeleteMapping("/deleteByBlogId/{blogId}")
	public abstract ResponseEntity<Response<Boolean>> deleteCommentsByblogId(@PathVariable String blogId);
	
}
