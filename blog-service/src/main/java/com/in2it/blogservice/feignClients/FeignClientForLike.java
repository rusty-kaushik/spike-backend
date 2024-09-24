package com.in2it.blogservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.in2it.blogservice.dto.Response;

@FeignClient(name = "Like-service", url = "${spike.like-service}")
public interface FeignClientForLike 
{
	@DeleteMapping("/unlike/deleted-blog/{blogId}")
    public ResponseEntity<Object> unlikeDeletedBlog(@RequestParam("blogId") String blogId);

}
