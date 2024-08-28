package com.blog.service.service.departmentService;

import com.blog.repository.entity.Department;
import com.blog.repository.repository.DepartmentRepository;
import com.blog.service.exceptions.InvalidAccessException;
import com.blog.service.exceptions.DepartmentNotFoundException;
import com.blog.service.exceptions.UnexpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private static final Pattern DEPARTMENT_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9 _-]{3,50}$"); // Example regex pattern for department names
    private static final Pattern ID_PATTERN = Pattern.compile("^[1-9][0-9]*$"); // Regex for positive integers

    // Create a new department
    @Override
    public Department createDepartment(String departmentName, String description) {
        try {
            validateDepartmentName(departmentName); // Validate the department name
            Department department = new Department(departmentName, description);
            return departmentRepository.save(department);
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while creating the department", e);
        }
    }

    // Retrieve a department by its ID
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        try {
            validateId(id); // Validate the ID
            return departmentRepository.findByIdActive(id);
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while retrieving the department by ID", e);
        }
    }

    // Retrieve a department by its name
    @Override
    public Department getDepartmentByName(String name) {
        try {
            validateDepartmentName(name); // Validate the department name
            return departmentRepository.findByNameContainingIgnoreCaseActive(name, Pageable.unpaged())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while retrieving the department by name", e);
        }
    }

    // Retrieve all active departments with pagination
    @Override
    public Page<Department> getAllDepartments(Pageable pageable) {
        try {
            return departmentRepository.findAllActive(pageable);
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while retrieving all departments", e);
        }
    }

    // Search departments by name with pagination
    @Override
    public Page<Department> searchDepartmentsByName(String name, Pageable pageable) {
        try {
            validateDepartmentName(name); // Validate the department name
            return departmentRepository.findByNameContainingIgnoreCaseActive(name, pageable);
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while searching departments by name", e);
        }
    }

    // Update a department
    @Override
    public Department updateDepartment(Long id, String departmentName) {
        try {
            validateId(id); // Validate the ID
            validateDepartmentName(departmentName); // Validate the department name

            Department department = departmentRepository.findByIdActive(id)
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found or not authorized to update"));
            if (!isAuthorizedToUpdate(department)) {
                throw new InvalidAccessException("Not authorized to update this department");
            }
            department.setName(departmentName);
            return departmentRepository.save(department);
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while updating the department", e);
        }
    }

    // Soft delete a department
    @Transactional
    @Override
    public void deleteDepartment(Long id) {
        try {
            validateId(id); // Validate the ID
            String currentUser = getCurrentUsername();
            departmentRepository.softDelete(id, currentUser, LocalDateTime.now());
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while deleting the department", e);
        }
    }

    // Restore a soft-deleted department
    @Transactional
    @Override
    public Department restoreDepartment(Long id) {
        try {
            validateId(id); // Validate the ID
            Department department = departmentRepository.findByIdActive(id)
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found or not authorized to restore"));
            if (!isAuthorizedToRestore(department)) {
                throw new InvalidAccessException("Not authorized to restore this department");
            }
            departmentRepository.restore(id);
            return departmentRepository.findByIdActive(id)
                    .orElseThrow(() -> new DepartmentNotFoundException("Failed to restore department"));
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while restoring the department", e);
        }
    }

    // Get deleted departments for restoration
    @Override
    public List<Department> getDeletedDepartments() {
        try {
            return departmentRepository.findAllDeleted();
        } catch (Exception e) {
            throw new UnexpectedException("An error occurred while retrieving deleted departments", e);
        }
    }

    // Utility method to get the current username from the security context
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        throw new UnexpectedException("Unable to retrieve current user");
    }

    // Utility method to check if the current user is authorized to update the department
    private boolean isAuthorizedToUpdate(Department department) {
        String currentUsername = getCurrentUsername();
        return currentUsername.equals(department.getCreatedBy());
    }

    // Utility method to check if the current user is authorized to restore the department
    private boolean isAuthorizedToRestore(Department department) {
        String currentUsername = getCurrentUsername();
        return currentUsername.equals(department.getDeletedBy());
    }

    // Utility method to validate department name using regex
    private void validateDepartmentName(String departmentName) {
        if (departmentName == null || !DEPARTMENT_NAME_PATTERN.matcher(departmentName).matches()) {
            throw new IllegalArgumentException("Department name must be between 3 and 50 characters and can only contain letters, numbers, spaces, underscores, and hyphens");
        }
    }

    // Utility method to validate ID using regex
    private void validateId(Long id) {
        if (id == null || !ID_PATTERN.matcher(id.toString()).matches()) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }
}
