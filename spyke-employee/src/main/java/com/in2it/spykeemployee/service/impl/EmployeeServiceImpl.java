package com.in2it.spykeemployee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.entity.Role;
import com.in2it.spykeemployee.repository.EmployeeRepository;
import com.in2it.spykeemployee.repository.RoleRepository;
import com.in2it.spykeemployee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	Random random = new Random();

	int randomId = random.nextInt(100, 65100);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Employee createEmployee(String firstName, String lastName, String username, String password, String gender,
			String desingnation) {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setUsername(username);
		employee.setPassword(passwordEncoder.encode(password));
		employee.setGender(gender);
		employee.setEmployeeId(randomId + lastName + firstName);
		employee.setDesignation(desingnation);
		employee.setRoles(Set.of(roleRepository.findByName("ROLE_EMPLOYEE").get()));
		return employeeRepository.save(employee);
	}

	@Override
	public Optional<Employee> getEmployeeById(String id) {
		return employeeRepository.findById(UUID.fromString(id));
	}

	@Override
	public Optional<Employee> getEmployeeByUsername(String username) {
		return employeeRepository.findByUsername(username);
	}

	@Override
	public Optional<Employee> getEmployeeByEmployeeId(String employeeId) {
		return employeeRepository.findByEmployeeId(employeeId);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public void deleteEmployee(String id) {
		employeeRepository.deleteById(UUID.fromString(id));
	}

	@Override
	public void addRoleToEmployee(String employeeId, int roleId) {
		Optional<Employee> employeeOptional = employeeRepository.findById(UUID.fromString(employeeId));
		Optional<Role> roleOptional = roleRepository.findById(roleId);

		if (employeeOptional.isPresent() && roleOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			Role role = roleOptional.get();
			employee.getRoles().add(role);
			employeeRepository.save(employee);
		}
	}

	@Override
	public void removeRoleFromEmployee(String employeeId, int roleId) {
		Optional<Employee> employeeOptional = employeeRepository.findById(UUID.fromString(employeeId));
		Optional<Role> roleOptional = roleRepository.findById(roleId);

		if (employeeOptional.isPresent() && roleOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			Role role = roleOptional.get();
			employee.getRoles().remove(role);
			employeeRepository.save(employee);
		}
	}

	@Override
	public Employee updateEmployee(String id, String firstName, String lastName, String username, String email,
			String password) {
		Optional<Employee> employeeOptional = employeeRepository.findById(UUID.fromString(id));

		if (employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employee.setUsername(username);
//	            employee.setEmail(email);
			if (password != null && !password.isEmpty()) {
				employee.setPassword(passwordEncoder.encode(password));
			}
			return employeeRepository.save(employee);
		}
		return null;
	}

}
