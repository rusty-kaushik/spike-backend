package com.in2it.blogservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service2", url = "${spike.department}")
public interface FeignClientDepartment {

	 @GetMapping("/{id}")
	 public ResponseEntity<Object> getDepartmentById(@PathVariable Long id);
}
