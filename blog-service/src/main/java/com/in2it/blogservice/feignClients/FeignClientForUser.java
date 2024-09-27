package com.in2it.blogservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.in2it.blogservice.dto.UserInfoDTO;

@FeignClient(name = "user-service", url = "${spike.user-service}")
public interface FeignClientForUser {

	@GetMapping("/username/{username}")
    public UserInfoDTO getUserByUsername(@PathVariable String username);
	
    @GetMapping("/self/{userId}")
	public ResponseEntity<Object> getUserById(@PathVariable long userId);
    
    
}
