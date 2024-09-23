package com.spike.user.controller;

import com.spike.user.dto.DepartmentCreationDTO;
import com.spike.user.dto.DepartmentDropdownDTO;
import com.spike.user.dto.DepartmentResponseDTO;
import com.spike.user.exceptions.DepartmentNotFoundException;
import com.spike.user.service.departmentService.DepartmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test case execution completed.");
    }

    @Test
    void createDepartment() {
        DepartmentCreationDTO departmentDTO = new DepartmentCreationDTO();
        DepartmentResponseDTO responseDTO = new DepartmentResponseDTO();

        when(departmentService.createDepartment(any())).thenReturn(responseDTO);

        ResponseEntity<Object> response = departmentController.createDepartment(departmentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Department creation successful", responseBody.get("message"));
        assertEquals(responseDTO, responseBody.get("data"));
    }

    @Test
    void departmentDropdown() {
        DepartmentDropdownDTO dropdownDTO1 = new DepartmentDropdownDTO();
        DepartmentDropdownDTO dropdownDTO2 = new DepartmentDropdownDTO();
        List<DepartmentDropdownDTO> departments = Arrays.asList(dropdownDTO1, dropdownDTO2);

        when(departmentService.getAllDepartments()).thenReturn(departments);

        ResponseEntity<Object> response = departmentController.departmentDropdown();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("List of all departments", responseBody.get("message"));
        assertEquals(departments, responseBody.get("data"));
    }

    @Test
    void getDepartmentById() {
        Long departmentId = 1L;
        DepartmentResponseDTO responseDTO = new DepartmentResponseDTO();

        when(departmentService.getDepartmentById(departmentId)).thenReturn(responseDTO);

        ResponseEntity<Object> response = departmentController.getDepartmentById(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Department fetched successfully", responseBody.get("message"));
        assertEquals(responseDTO, responseBody.get("data"));
    }

    @Test
    void checkDepartmentExistence() {
        Long departmentId = 1L;
        when(departmentService.checkDepartmentExistence(departmentId)).thenReturn(true);

        ResponseEntity<Object> response = departmentController.checkDepartmentExistence(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Department existence check", responseBody.get("message"));
        assertTrue((Boolean) responseBody.get("data"));
    }

    @Test
    void updateDepartment() {
        Long departmentId = 1L;
        DepartmentCreationDTO departmentDTO = new DepartmentCreationDTO();
        DepartmentResponseDTO responseDTO = new DepartmentResponseDTO();

        when(departmentService.updateDepartment(eq(departmentId), any())).thenReturn(responseDTO);

        ResponseEntity<Object> response = departmentController.updateDepartment(departmentId, departmentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Department update successful", responseBody.get("message"));
        assertEquals(responseDTO, responseBody.get("data"));
    }

    @Test
    void deleteDepartment() {
        Long departmentId = 1L;

        doNothing().when(departmentService).deleteDepartment(departmentId);

        ResponseEntity<Object> response = departmentController.deleteDepartment(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Department deleted successfully", responseBody.get("message"));
        assertNull(responseBody.get("data"));
    }

    @Test
    void getDepartmentById_notFound() {
        Long departmentId = 1L;
        String error = "DEPARTMENT_NOT_FOUND";
        String message = "Department not found";

        when(departmentService.getDepartmentById(departmentId)).thenThrow(new DepartmentNotFoundException(error, message));

        ResponseEntity<Object> response = departmentController.getDepartmentById(departmentId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.get("message").toString().contains(message));
    }

    @Test
    void updateDepartment_notFound() {
        Long departmentId = 1L;
        DepartmentCreationDTO departmentDTO = new DepartmentCreationDTO();
        String error = "DEPARTMENT_NOT_FOUND";
        String message = "Department not found";

        when(departmentService.updateDepartment(eq(departmentId), any())).thenThrow(new DepartmentNotFoundException(error, message));

        ResponseEntity<Object> response = departmentController.updateDepartment(departmentId, departmentDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.get("message").toString().contains(message));
    }

    @Test
    void deleteDepartment_notFound() {
        Long departmentId = 1L;
        String error = "DEPARTMENT_NOT_FOUND";
        String message = "Department not found";

        doThrow(new DepartmentNotFoundException(error, message)).when(departmentService).deleteDepartment(departmentId);

        ResponseEntity<Object> response = departmentController.deleteDepartment(departmentId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.get("message").toString().contains(message));
    }

    @Test
    void createDepartment_exception() {
        DepartmentCreationDTO departmentDTO = new DepartmentCreationDTO();
        String error = "CREATE_DEPARTMENT_FAILED";

        when(departmentService.createDepartment(any())).thenThrow(new RuntimeException(error));

        ResponseEntity<Object> response = departmentController.createDepartment(departmentDTO);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.get("message").toString().contains("Error creating department: " + error));
    }

    @Test
    void departmentDropdown_exception() {
        String error = "FETCH_DEPARTMENTS_FAILED";

        when(departmentService.getAllDepartments()).thenThrow(new RuntimeException(error));

        ResponseEntity<Object> response = departmentController.departmentDropdown();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.get("message").toString().contains("Error fetching departments: " + error));
    }

    @Test
    void checkDepartmentExistence_notFound() {
        Long departmentId = 1L;
        when(departmentService.checkDepartmentExistence(departmentId)).thenReturn(false);

        ResponseEntity<Object> response = departmentController.checkDepartmentExistence(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Department existence check", responseBody.get("message"));
        assertFalse((Boolean) responseBody.get("data"));
    }
}
