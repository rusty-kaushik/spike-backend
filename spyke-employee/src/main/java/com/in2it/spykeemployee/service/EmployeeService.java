package com.in2it.spykeemployee.service;

import java.util.List;
import java.util.Optional;

import com.in2it.spykeemployee.dto.EmployeeDto;
import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.request.dto.CreateEmployeeDto;
import com.in2it.spykeemployee.request.dto.UpdateEmployeeDto;
import com.in2it.spykeemployee.responce.dto.CreateEmployeeResponceDto;


public interface EmployeeService {



	Employee createEmployee(String firstName, String lastName, String username, String password, String gender, String desingnation);

	Optional<Employee> getEmployeeById(String id);

	Optional<Employee> getEmployeeByUsername(String username);

	Optional<Employee> getEmployeeByEmployeeId(String email);

	List<Employee> getAllEmployees();

	void deleteEmployee(String id);

	void addRoleToEmployee(String userId, int roleId);

	void removeRoleFromEmployee(String userId, int roleId);

	Employee updateEmployee(String id, String firstName, String lastName, String username, String email, String password);
	
	
	public CreateEmployeeResponceDto createEmployee(CreateEmployeeDto employeeDto);
	
	UpdateEmployeeDto updateEmployee(String employeeId,UpdateEmployeeDto employeeDto);
	
	
}
