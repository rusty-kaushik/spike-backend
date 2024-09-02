package com.in2it.spykeemployee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.spykeemployee.entity.Project;
import com.in2it.spykeemployee.service.ProjectService;

@RestController
@RequestMapping("in2it/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestParam String name, @RequestParam String description) {
        Project project = projectService.createProject(name, description);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @PostMapping("/{projectId}/employees")
    public ResponseEntity<Project> addEmployeeToProject(
            @PathVariable String projectId, 
            @RequestBody List<String> employeeIds) {
        Project project = projectService.addEmployeeToProject(projectId, employeeIds);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/employees")
    public ResponseEntity<Project> removeEmployeeFromProject(
            @PathVariable String projectId, 
            @RequestBody List<String> employeeIds) {
        Project project = projectService.removeEmployeeFromProject(projectId, employeeIds);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
}