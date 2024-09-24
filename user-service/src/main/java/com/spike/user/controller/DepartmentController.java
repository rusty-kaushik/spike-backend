package com.spike.user.controller;

import com.spike.user.auditing.AuditorAwareImpl;
import com.spike.user.dto.DepartmentCreationDTO;
import com.spike.user.dto.DepartmentDropdownDTO;
import com.spike.user.dto.DepartmentResponseDTO;
import com.spike.user.exceptions.DepartmentNotFoundException;
import com.spike.user.response.ResponseHandler;
import com.spike.user.service.departmentService.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spike/department")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    // create a new department
    @Operation(
            summary = "Create a new department",
            description = "Creates a new department."
    )
    @PostMapping("/create-new")
    public ResponseEntity<Object> createDepartment(@RequestBody DepartmentCreationDTO department) {
        logger.info("Start: Creating new department");
        try {
            AuditorAwareImpl.setCurrentAuditor("admin");
            DepartmentResponseDTO createdDepartment = departmentService.createDepartment(department);
            return ResponseHandler.responseBuilder("Department creation successful", HttpStatus.OK, createdDepartment);
        } catch (Exception e) {
            logger.error("Error creating department: {}", e.getMessage());
            return ResponseHandler.responseBuilder("Error creating department: " + e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "Creation failed");
        } finally {
            logger.info("End: Creating new department");
            AuditorAwareImpl.clear(); // Clear the auditor context
        }
    }

    // department dropdown list
    @Operation(
            summary = "Department Dropdown",
            description = "Fetches a list of all departments for dropdown list."
    )
    @GetMapping("/dropdown")
    public ResponseEntity<Object> departmentDropdown() {
        logger.info("Start: Fetching all departments");
        try {
            List<DepartmentDropdownDTO> departments = departmentService.getAllDepartments();
            return ResponseHandler.responseBuilder("List of all departments", HttpStatus.OK, departments);
        } catch (Exception e) {
            logger.error("Error fetching departments: {}", e.getMessage());
            return ResponseHandler.responseBuilder("Error fetching departments: " + e.getMessage(), HttpStatus.NOT_FOUND, "Fetch failed");
        } finally {
            logger.info("End: Fetching all departments");
            AuditorAwareImpl.clear();
        }
    }

    // get department by ID
    @Operation(
            summary = "Get department by ID",
            description = "Fetches a department by its ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartmentById(@PathVariable long id) {
        logger.info("Start: Fetching department with ID {}", id);
        try {
            DepartmentResponseDTO department = departmentService.getDepartmentById(id);
            return ResponseHandler.responseBuilder("Department fetched successfully", HttpStatus.OK, department);
        } catch (DepartmentNotFoundException e) {
            logger.warn("Department with ID {} not found: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Department not found: " + e.getMessage(), HttpStatus.NOT_FOUND, "Department not found");
        } catch (Exception e) {
            logger.error("Error fetching department with ID {}: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Error fetching department: " + e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "Fetch failed");
        } finally {
            logger.info("End: Fetching department with ID {}", id);
            AuditorAwareImpl.clear();
        }
    }

    // check if department exists
    @Operation(
            summary = "Check if department exists",
            description = "Checks if a department exists by its ID."
    )
    @GetMapping("/exist/{id}")
    public ResponseEntity<Object> checkDepartmentExistence(@PathVariable Long id) {
        boolean exists = departmentService.checkDepartmentExistence(id);
        return ResponseHandler.responseBuilder("Department existence check", HttpStatus.OK, exists);
    }

    // update the department
    @Operation(
            summary = "Update an existing department",
            description = "Updates an existing department."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable Long id, @RequestBody DepartmentCreationDTO department) {
        logger.info("Start: Updating department with ID {}", id);
        try {
            AuditorAwareImpl.setCurrentAuditor("admin");
            DepartmentResponseDTO updatedDepartment = departmentService.updateDepartment(id, department);
            return ResponseHandler.responseBuilder("Department update successful", HttpStatus.OK, updatedDepartment);
        } catch (DepartmentNotFoundException e) {
            logger.warn("Error updating department - not found with ID {}: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Department not found: " + e.getMessage(), HttpStatus.NOT_FOUND, "Update failed");
        } catch (Exception e) {
            logger.error("Error updating department with ID {}: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Error updating department: " + e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "Update failed");
        } finally {
            logger.info("End: Updating department with ID {}", id);
            AuditorAwareImpl.clear();
        }
    }

    // delete the department
    @Operation(
            summary = "Delete a department",
            description = "Deletes a department by its ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable Long id) {
        logger.info("Start: Deleting department with ID {}", id);
        try {
            AuditorAwareImpl.setCurrentAuditor("admin");
            departmentService.deleteDepartment(id);
            logger.info("Successfully deleted department with ID {}", id);
            return ResponseHandler.responseBuilder("Department deleted successfully", HttpStatus.OK, null);
        } catch (DepartmentNotFoundException e) {
            logger.warn("Department with ID {} not found: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Department not found: " + e.getMessage(), HttpStatus.NOT_FOUND, "Delete failed");
        } catch (Exception e) {
            logger.error("Error deleting department with ID {}: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Error deleting department: " + e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "Delete failed");
        } finally {
            AuditorAwareImpl.clear();
            logger.info("End: Deleting department with ID {}", id);
        }
    }
}