package com.in2it.spykeemployee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.entity.Department;
import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.exception.DepartmentNotFoundException;
import com.in2it.spykeemployee.repository.DepartmentRepository;
import com.in2it.spykeemployee.repository.EmployeeRepository;
import com.in2it.spykeemployee.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	EmployeeRepository employeeRepository;

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

	@Override
	public Department addEmployeeToDepartment(String departmentId, List<String> employeeIds) {
		Department department = repository.findById(UUID.fromString(departmentId))
				.orElseThrow(() -> new DepartmentNotFoundException("Department dosen't exist with given Id"));
		Set<Employee> employees = department.getEmployees();
		employeeIds.forEach((id -> {

			Employee employee = employeeRepository.findById(UUID.fromString(id))
					.orElseThrow(() -> new UsernameNotFoundException("Employee dosen't exist with given Id"));
			employee.setDepartment(department);
			employeeRepository.save(employee);

			employees.add(employee);
		}));
		department.setEmployees(employees);
		return repository.save(department);
	}

	@Override
	public Department removeEmployeeFromDepartment(String departmentId, List<String> employeeIds) {

		Department department = repository.findById(UUID.fromString(departmentId))
				.orElseThrow(() -> new DepartmentNotFoundException("Department doesn't exist with given Id"));

		Set<Employee> employees = department.getEmployees();

		employeeIds.forEach(id -> {
			Employee employee = employeeRepository.findById(UUID.fromString(id))
					.orElseThrow(() -> new UsernameNotFoundException("Employee doesn't exist with given Id: " + id));

			employees.remove(employee);

			employee.setDepartment(null);

			employeeRepository.save(employee);
		});

		department.setEmployees(employees);

		return repository.save(department);
	}

	@Override
	public Department assignManagerToDeprtment(String departmentId, String managerId) {
		Department department = repository.findById(UUID.fromString(departmentId))
				.orElseThrow(() -> new DepartmentNotFoundException("Department dosen't exist with given Id.."));
		Employee manager = employeeRepository.findById(UUID.fromString(managerId))
				.orElseThrow(() -> new UsernameNotFoundException("Employee dosen't exist with given Id..."));
		department.setManager(manager);
		return repository.save(department);
	}

	@Override
	public Department removeManagerFromDepartment(String departmentId, String managerId) {
		Department department = repository.findById(UUID.fromString(departmentId))
				.orElseThrow(() -> new DepartmentNotFoundException("Department doesn't exist with given Id.."));

		Employee currentManager = department.getManager();
		if (currentManager == null || !currentManager.getId().toString().equals(managerId)) {
			throw new UsernameNotFoundException("Manager with given Id is not currently assigned to this department.");
		}

		department.setManager(null);
		return repository.save(department);
	}

	@Override
	public Department changeManagerOfDepartment(String departmentId, String oldManagerId, String newManagerId) {

		Department department = repository.findById(UUID.fromString(departmentId))
				.orElseThrow(() -> new DepartmentNotFoundException("Department doesn't exist with given Id.."));

		Employee oldManager = employeeRepository.findById(UUID.fromString(oldManagerId))
				.orElseThrow(() -> new UsernameNotFoundException("Old manager doesn't exist with given Id..."));

		if (department.getManager() == null || !department.getManager().equals(oldManager)) {
			throw new UsernameNotFoundException("Old manager is not currently assigned to this department.");
		}

		Employee newManager = employeeRepository.findById(UUID.fromString(newManagerId))
				.orElseThrow(() -> new UsernameNotFoundException("New manager doesn't exist with given Id..."));

		department.setManager(newManager);

		return repository.save(department);
	}

}
