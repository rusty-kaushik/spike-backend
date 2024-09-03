package com.spike.user.database;

import com.spike.user.entity.Department;
import com.spike.user.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//*The DepartmentSeeder class initializes the department database when the application starts.
//* It checks if specific departments are already in the repository. If not, it creates and saves them.
//* This ensures that predefined departments (like "JAVA", "PYTHON", etc.) are available in the database at startup.
@Component
public class DepartmentSeeder {

    @Autowired
    private DepartmentRepository departmentRepository;

    public void seedDepartments() {
        String[] departmentNames = {
                "JAVA", "PYTHON", "PHP", "ANGULAR", "DATABASE",
                "UI", "HR", "IT", "SALES", "QA", "SUPPORT"
        };

        for (String name : departmentNames) {
            if (departmentRepository.findByName(name).isEmpty()) {
                Department department = new Department();
                department.setName(name);
                departmentRepository.save(department);
                System.out.println("Department '" + name + "' created.");
            } else {
                System.out.println("Department '" + name + "' already exists.");
            }
        }
    }
}