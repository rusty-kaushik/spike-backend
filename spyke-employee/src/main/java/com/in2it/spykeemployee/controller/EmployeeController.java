package com.in2it.spykeemployee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("in2it/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Operation(summary = "Create an Employee ")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created an employee", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)) }) })
//@ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content), 
//        @ApiResponse(responseCode = "404",description = "Employee not found",content = @Content), 
//        @ApiResponse(responseCode = "400",description = "Invalid employee id",content = @Content)}) 

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String username, @RequestParam String password, @RequestParam String gender, @RequestParam String desingnation) {
		Employee employee = employeeService.createEmployee(firstName, lastName, username, password, gender, desingnation);
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}

	@Operation(summary = "Get employee by ID  ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Employee found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)) }) })

	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
		Optional<Employee> employee = employeeService.getEmployeeById(id);
		return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username) {
		Optional<Employee> employee = employeeService.getEmployeeByUsername(username);
		return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "Get employee  by EmployeeID  ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Employee found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)) }) })
    @GetMapping("/employee-id/{employeeId}")
    public ResponseEntity<Employee> getEmployeeByEmployeeId(@PathVariable String employeeId) {
        Optional<Employee> employee = employeeService.getEmployeeByEmployeeId(employeeId);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}
	@Operation(summary = "Assign a new role to employee  ") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "201", description = "New role assigned  ", 
	content = {@Content(mediaType = "application/json")})})
	@PostMapping("/{userId}/roles/{roleId}")
	public ResponseEntity<Void> addRoleToEmployee(@PathVariable String userId, @PathVariable int roleId) {
		employeeService.addRoleToEmployee(userId, roleId);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Remove a role from employee  ") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "204", description = "Role has been removed  ", 
	content = {@Content(mediaType = "application/json")})})

	@DeleteMapping("/{userId}/roles/{roleId}")
	public ResponseEntity<Void> removeRoleFromEmployee(@PathVariable String userId, @PathVariable int roleId) {
		employeeService.removeRoleFromEmployee(userId, roleId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String username, @RequestParam String email,
			@RequestParam String password) {
		Employee updatedEmployee = employeeService.updateEmployee(id, firstName, lastName, username, email, password);
		return ResponseEntity.ok(updatedEmployee);
	}

}
