package com.in2it.spykeemployee.service;

import java.util.List;

import com.in2it.spykeemployee.entity.Project;

public interface ProjectService {
	
	public Project createProject(String name, String Description);
	public Project addEmployeeToProject(String projectId, List<String> employeeIds);
	public Project removeEmployeeFromProject(String projectId, List<String> employeeIds);

}
