package com.in2it.spykeemployee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.entity.Department;
import com.in2it.spykeemployee.repository.DepartmentRepository;
import com.in2it.spykeemployee.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository repository;

	@Override
	public Department createDepartment(String name, String Description) {
		return repository.save(Department.builder().name(name).description(Description).build());
	}

	@Override
	public List<Department> getAllDepartment() {
		return repository.findAll();
	}

	@Override
	public Optional<Department> getDepartmentById(String id) {

		return repository.findById(UUID.fromString(id));
	}

	@Override
	public Optional<Department> getDepartmentByName(String name) {
		
		return repository.findByName(name);
	}

}
