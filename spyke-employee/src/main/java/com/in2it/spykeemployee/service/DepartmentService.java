package com.in2it.spykeemployee.service;

import java.util.List;
import java.util.Optional;

import com.in2it.spykeemployee.entity.Department;

public interface DepartmentService {
	
	Department createDepartment(String name, String description);
	List<Department> getAllDepartment();
	Optional<Department> getDepartmentById(String id);
	Optional<Department> getDepartmentByName(String name);
	Department addEmployeeToDepartment(String departmentId, List<String> employeeIds);
	Department removeEmployeeFromDepartment(String departmentId, List<String> employeeIds);
	Department assignManagerToDeprtment(String departmentId, String managerId);
	Department removeManagerFromDepartment(String departmentId, String managerId);
	Department changeManagerOfDepartment(String departmentId, String oldManagerId, String newManagerId);
}
