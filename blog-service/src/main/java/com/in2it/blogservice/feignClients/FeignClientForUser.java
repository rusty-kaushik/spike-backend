package com.in2it.blogservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.in2it.blogservice.dto.UserInfoDTO;

@FeignClient(name = "user-service", url = "http://99.99.99.185:8181/spike/user")
public interface FeignClientForUser {

	@GetMapping("/username/{username}")
    public UserInfoDTO getUserByUsername(@PathVariable String username);
}
