package com.spike.user.controller;

import com.spike.user.auditing.AuditorAwareImpl;
import com.spike.user.dto.DepartmentCreationDTO;
import com.spike.user.dto.DepartmentDropdownDTO;
import com.spike.user.dto.DepartmentResponseDTO;
import com.spike.user.entity.Department;
import com.spike.user.exceptions.DepartmentNotFoundException;
import com.spike.user.response.ResponseHandler;
import com.spike.user.service.departmentService.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            return ResponseHandler.responseBuilder("Error Occurred ",HttpStatus.UNPROCESSABLE_ENTITY,"Try again");
        } finally {
            logger.info("End: Creating new department");
            AuditorAwareImpl.clear(); // Clear the auditor context
        }
    }

    // it is used for the dropdown purpose - get all department

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
            return ResponseHandler.responseBuilder("Department not found",HttpStatus.NOT_FOUND,"Try again");
        } finally {
            logger.info("End: Fetching all departments");
            // Clear the auditor context if necessary
            AuditorAwareImpl.clear(); // Ensure this is relevant to your use case
        }
    }

    // get the single department
    @Operation(
            summary = "Get department by ID",
            description = "Fetches a department by its ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartmentById(@PathVariable Long id) {
        logger.info("Start: Fetching department with ID {}", id);
        try {
            Optional<DepartmentResponseDTO> department = Optional.ofNullable(departmentService.getDepartmentById(id));
            return ResponseHandler.responseBuilder("Department fetched successful", HttpStatus.OK, department);
        } catch (Exception e) {
            logger.error("Error fetching department with ID {}: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Department not found",HttpStatus.NOT_FOUND,"Try again");
        } finally {
            logger.info("End: Fetching department with ID {}", id);
            AuditorAwareImpl.clear(); // Clear the auditor context
        }
    }

    // check if department exists

    // get the single department
    @Operation(
            summary = "Get department by Name",
            description = "Fetches a department by its name."
    )
    @GetMapping("/exist/{name}")
    public boolean checkDepartmentExistence(@PathVariable String name) {
       return departmentService.checkDepartmentExistence(name);
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
        } catch (Exception e) {
            logger.error("Error updating department with ID {}: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Department update failed",HttpStatus.UNPROCESSABLE_ENTITY,"Try again");
        } finally {
            logger.info("End: Updating department with ID {}", id);
            AuditorAwareImpl.clear(); // Clear the auditor context
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
            // Set the current auditor
            AuditorAwareImpl.setCurrentAuditor("admin");

            // Attempt to delete the department
            departmentService.deleteDepartment(id);

            // Return 204 No Content if deletion was successful
            logger.info("Successfully deleted department with ID {}", id);
            return ResponseHandler.responseBuilder("Department deleted successfully",HttpStatus.OK,"delete department");
        } catch (DepartmentNotFoundException e) {
            // Handle the case where the department was not found
            logger.warn("Department with ID {} not found: {}", id, e.getMessage());
            return ResponseHandler.responseBuilder("Department with ID {} not found",HttpStatus.NOT_FOUND,"Try again");
        } catch (Exception e) {
            // Handle unexpected errors
            logger.error("Error deleting department with ID {}: {}", id, e.getMessage(), e);
            return ResponseHandler.responseBuilder("Error deleting the department",HttpStatus.UNPROCESSABLE_ENTITY,"Error");
        } finally {
            // Clear the auditor context
            AuditorAwareImpl.clear();
            logger.info("End: Deleting department with ID {}", id);
        }
    }


}
