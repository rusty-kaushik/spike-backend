package com.in2it.spykeemployee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.entity.Project;
import com.in2it.spykeemployee.exception.ProjectNotFoundException;
import com.in2it.spykeemployee.repository.EmployeeRepository;
import com.in2it.spykeemployee.repository.ProjectRepository;
import com.in2it.spykeemployee.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ProjectRepository repository;

	@Override
	public Project createProject(String name, String Description) {
		return repository.save(Project.builder().name(name).description(Description).build());
	}

	@Override
	public Project addEmployeeToProject(String projectId, List<String> employeeIds) {

		Project project = repository.findById(UUID.fromString(projectId))
				.orElseThrow(() -> new ProjectNotFoundException("Project dosent exist with given Id"));
		List<Employee> employees = project.getEmployees();
		employeeIds.forEach((id) -> {
			Employee employee = employeeRepository.findById(UUID.fromString(id))
					.orElseThrow(() -> new UsernameNotFoundException("Employee dosn't exist with given Id..."));
			employees.add(employee);
			List<Project> projects = employee.getProjects();
			projects.add(project);
			employee.setProjects(projects);
			employeeRepository.save(employee);
		});
		project.setEmployees(employees);

		return repository.save(project);
	}



	@Override
	public Project removeEmployeeFromProject(String projectId, List<String> employeeIds) {

		Project project = repository.findById(UUID.fromString(projectId))
				.orElseThrow(() -> new ProjectNotFoundException("Project doesn't exist with given Id"));

		List<Employee> employees = project.getEmployees();

		employeeIds.forEach(id -> {
			Employee employee = employeeRepository.findById(UUID.fromString(id))
					.orElseThrow(() -> new UsernameNotFoundException("Employee doesn't exist with given Id: " + id));

			employees.remove(employee);

			List<Project> projects = employee.getProjects();
			projects.remove(project);
			employee.setProjects(projects);

			employeeRepository.save(employee);
		});

		project.setEmployees(employees);

		return repository.save(project);
	}

	@Override
	public List<Project> getAllProject() {
		
		return repository.findAll();
	}

}
