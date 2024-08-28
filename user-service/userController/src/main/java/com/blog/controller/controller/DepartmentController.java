package com.blog.controller.controller;

import com.blog.controller.response.ResponseHandler;
import com.blog.repository.entity.Department;
import com.blog.service.service.departmentService.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/in2it/blog/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Operation(summary = "Creates a new department with the provided name and description.", description = "Creates a new department with the provided name & description.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Department created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient role or invalid JWT token")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createDepartment(@RequestBody @Parameter(description = "Details of the department to be created") Map<String, String> request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            return ResponseHandler.responseBuilder("Department created successfully", HttpStatus.CREATED, departmentService.createDepartment(request.get("name"), request.get("description")));
        } catch (AccessDeniedException e) {
            return ResponseHandler.responseBuilder("Forbidden, insufficient role", HttpStatus.FORBIDDEN, null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error occurred while creating department", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Get a department by ID", description = "Retrieves a department by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department found"),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient role or invalid JWT token")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getDepartmentById(@PathVariable @Parameter(description = "ID of the department to retrieve") Long id) {
        try {
            return departmentService.getDepartmentById(id)
                    .map(department -> ResponseHandler.responseBuilder("Department found", HttpStatus.OK, department))
                    .orElseGet(() -> ResponseHandler.responseBuilder("Department not found", HttpStatus.NOT_FOUND, null));
        } catch (AccessDeniedException e) {
            return ResponseHandler.responseBuilder("Forbidden, insufficient role", HttpStatus.FORBIDDEN, null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error occurred while retrieving department", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Get all active departments", description = "Retrieves all active departments with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active departments retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient role or invalid JWT token")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        try {
            return ResponseHandler.responseBuilder("Active departments retrieved successfully", HttpStatus.OK, departmentService.getAllDepartments(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy))));
        } catch (AccessDeniedException e) {
            return ResponseHandler.responseBuilder("Forbidden, insufficient role", HttpStatus.FORBIDDEN, null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error occurred while retrieving departments", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Search departments by name", description = "Searches for departments by their name with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of departments matching the search criteria retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient role or invalid JWT token")
    })
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> searchDepartmentsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            return ResponseHandler.responseBuilder("Departments matching the search criteria retrieved successfully", HttpStatus.OK, departmentService.searchDepartmentsByName(name, PageRequest.of(page, size)));
        } catch (AccessDeniedException e) {
            return ResponseHandler.responseBuilder("Forbidden, insufficient role", HttpStatus.FORBIDDEN, null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error occurred while searching departments", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Update a department", description = "Updates the details of an existing department.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient role or invalid JWT token")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateDepartment(
            @PathVariable @Parameter(description = "ID of the department to be updated") Long id,
            @RequestBody @Parameter(description = "Updated department details") Map<String, String> request
    ) {
        try {
            return ResponseHandler.responseBuilder("Department updated successfully", HttpStatus.OK, departmentService.updateDepartment(id, request.get("name")));
        } catch (AccessDeniedException e) {
            return ResponseHandler.responseBuilder("Forbidden, insufficient role", HttpStatus.FORBIDDEN, null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error occurred while updating department", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Delete a department", description = "Soft deletes a department by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient role or invalid JWT token")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteDepartment(@PathVariable @Parameter(description = "ID of the department to be deleted") Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseHandler.responseBuilder("Department soft deleted successfully", HttpStatus.OK, null);
        } catch (AccessDeniedException e) {
            return ResponseHandler.responseBuilder("Forbidden, insufficient role", HttpStatus.FORBIDDEN, null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error occurred while deleting department", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Restore a soft-deleted department", description = "Restores a previously soft-deleted department by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department restored successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient role or invalid JWT token")
    })
    @PostMapping("/restore/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> restoreDepartment(@PathVariable @Parameter(description = "ID of the department to be restored") Long id) {
        try {
            return ResponseHandler.responseBuilder("Department restored successfully", HttpStatus.OK, departmentService.restoreDepartment(id));
        } catch (AccessDeniedException e) {
            return ResponseHandler.responseBuilder("Forbidden, insufficient role", HttpStatus.FORBIDDEN, null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error occurred while restoring department", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Get deleted departments for restoration", description = "Retrieves departments that have been soft-deleted.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of deleted departments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No deleted departments found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient role or invalid JWT token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getDeletedDepartments() {
        try {
            List<Department> departments = departmentService.getDeletedDepartments();
            if (departments.isEmpty()) {
                return ResponseHandler.responseBuilder("No deleted departments found", HttpStatus.NOT_FOUND, null);
            }
            return ResponseHandler.responseBuilder("Deleted departments retrieved successfully", HttpStatus.OK, departments);
        } catch (AccessDeniedException e) {
            return ResponseHandler.responseBuilder("Forbidden, insufficient role", HttpStatus.FORBIDDEN, null);
        } catch (AuthenticationException e) {
            return ResponseHandler.responseBuilder("Invalid authentication token", HttpStatus.UNAUTHORIZED, null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error occurred while retrieving deleted departments", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
