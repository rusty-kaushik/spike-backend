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
import org.springframework.web.bind.annotation.RequestBody;
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
	public Department createDepartment(@RequestParam String name, @RequestParam String description) {
		return service.createDepartment(name, description);
	}

	@GetMapping
	public List<Department> getAllDepartment() {
		return service.getAllDepartment();
	}

	@GetMapping("/department/{id}")
	public Optional<Department> getDepartmentById(@PathVariable String id) {
		return service.getDepartmentById(id);
	}

	@GetMapping("/departmet-name")
	public Optional<Department> getDepartmentByName(@RequestParam String name) {
		return service.getDepartmentByName(name);
	}

	@PostMapping("/{departmentId}/employees")
	public ResponseEntity<Department> addEmployeeToDepartment(@PathVariable String departmentId,
			@RequestBody List<String> employeeIds) {
		Department department = service.addEmployeeToDepartment(departmentId, employeeIds);
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	// Remove employees from a department
	@DeleteMapping("/{departmentId}/employees")
	public ResponseEntity<Department> removeEmployeeFromDepartment(@PathVariable String departmentId,
			@RequestBody List<String> employeeIds) {
		Department department = service.removeEmployeeFromDepartment(departmentId, employeeIds);
		return new ResponseEntity<>(department, HttpStatus.OK);
	}
	
	 // Assign manager to a department
    @PostMapping("/{departmentId}/manager/{managerId}")
    public ResponseEntity<Department> assignManagerToDepartment(
            @PathVariable String departmentId, 
            @PathVariable String managerId) {
        Department department = service.assignManagerToDeprtment(departmentId, managerId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    // Remove manager from a department
    @DeleteMapping("/{departmentId}/manager/{managerId}")
    public ResponseEntity<Department> removeManagerFromDepartment(
            @PathVariable String departmentId, 
            @PathVariable String managerId) {
        Department department = service.removeManagerFromDepartment(departmentId, managerId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    // Change manager of a department
    @PutMapping("/{departmentId}/manager")
    public ResponseEntity<Department> changeManagerOfDepartment(
            @PathVariable String departmentId, 
            @RequestParam String oldManagerId, 
            @RequestParam String newManagerId) {
        Department department = service.changeManagerOfDepartment(departmentId, oldManagerId, newManagerId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

}
