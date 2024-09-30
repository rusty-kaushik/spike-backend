package com.taskboard_service.userFeign;




import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taskboard_service.dto.UserInfoDTO;



@FeignClient(name = "user-service", url = "${spike.user-service}")
@Component
public interface FeignClientForUser {

	@GetMapping("/username/{username}")
    public UserInfoDTO getUserByUsername(@PathVariable String username);
	
    @GetMapping("/self/{userId}")
	public ResponseEntity<Object> getUserById(@PathVariable long userId);
    
    
}
