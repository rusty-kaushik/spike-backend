package com.in2it.spykeemployee.service;

import java.util.List;
import java.util.Optional;

import com.in2it.spykeemployee.entity.Employee;


public interface EmployeeService {

//	Employee createUser(String username, String password, String email);
//    Optional<User> getUserById(Long id);
//    Optional<User> getUserByUsername(String username);
//    Optional<User> getUserByEmail(String email);
//    List<User> getAllUsers();
//    void deleteUser(Long id);
//    void addRoleToUser(Long userId, Long roleId);
//    void removeRoleFromUser(Long userId, Long roleId);
//    User updateUser(Long id, String username, String email, String password);

	Employee createEmployee(String firstName, String lastName, String username, String password, String gender, String desingnation);

	Optional<Employee> getEmployeeById(String id);

	Optional<Employee> getEmployeeByUsername(String username);

	Optional<Employee> getEmployeeByEmployeeId(String email);

	List<Employee> getAllEmployees();

	void deleteEmployee(String id);

	void addRoleToEmployee(String userId, int roleId);

	void removeRoleFromEmployee(String userId, int roleId);

	Employee updateEmployee(String id, String firstName, String lastName, String username, String email, String password);
	
	
	
	

}
