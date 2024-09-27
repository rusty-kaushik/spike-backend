package com.spike.user.database;

import com.spike.user.entity.Department;
import com.spike.user.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//*The DepartmentSeeder class initializes the department database when the application starts.
//* It checks if specific departments are already in the repository. If not, it creates and saves them.
//* This ensures that predefined departments (like "JAVA", "PYTHON", etc.) are available in the database at startup.
@Component
public class DepartmentSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentSeeder.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    public void seedDepartments() {
        for (DepartmentType departmentType : DepartmentType.values()) {
            String name = departmentType.name();

            if (departmentRepository.findByName(name).isEmpty()) {
                Department department = new Department();
                department.setName(name);
                departmentRepository.save(department);
                logger.info("Department '{}' created.", name);
            } else {
                logger.info("Department '{}' already exists.", name);
            }
        }
    }
}