package com.in2it.spykeemployee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.spykeemployee.entity.Department;
import com.in2it.spykeemployee.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/in2it/departments")
@RequiredArgsConstructor
public class DepartmentController {
	
	@Autowired
	DepartmentService service;
	
	@PostMapping
	public Department createDepartment(@RequestParam String name,@RequestParam String description) {
		return service.createDepartment(name, description);
	}
	
	@GetMapping
	public List<Department> getAllDepartment(){
		return service.getAllDepartment();
	}
	
	@GetMapping("/department/{id}")
	public Optional<Department> getDepartmentById(@PathVariable String id){
		return service.getDepartmentById(id);
	}
	
	@GetMapping("/departmet-name")
	public Optional<Department> getDepartmentByName(@RequestParam String name){
		return service.getDepartmentByName(name);
	}


}
