package com.blog.service.service.departmentService;

import com.blog.repository.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Department createDepartment(String teamName, String description);

    Optional<Department> getDepartmentById(Long id);

    Department getDepartmentByName(String name);

    Page<Department> getAllDepartments(Pageable pageable);

    Page<Department> searchDepartmentsByName(String name, Pageable pageable);

    Department updateDepartment(Long id, String teamName);

    void deleteDepartment(Long id);

    Department restoreDepartment(Long id);

    List<Department> getDeletedDepartments();
}
