package com.in2it.spykeemployee.service;

import java.util.List;
import java.util.Optional;

import com.in2it.spykeemployee.entity.Department;

public interface DepartmentService {
	
	Department createDepartment(String name, String description);
	List<Department> getAllDepartment();
	Optional<Department> getDepartmentById(String id);
	Optional<Department> getDepartmentByName(String name);

}
