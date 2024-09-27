package com.spike.user.service.departmentService;

import com.spike.user.customMapper.DepartmentMapper;
import com.spike.user.dto.DepartmentCreationDTO;
import com.spike.user.dto.DepartmentDropdownDTO;
import com.spike.user.dto.DepartmentResponseDTO;
import com.spike.user.entity.Department;
import com.spike.user.exceptions.DepartmentNotFoundException;
import com.spike.user.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository; // Mocked repository to simulate database interactions

    @Mock
    private DepartmentMapper departmentMapper; // Mocked mapper for DTO-entity conversions

    @InjectMocks
    private DepartmentServiceImpl departmentService; // Service under test, with mocks injected

    @BeforeEach
    void setUp() {
        // Initializes the mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        // Optional cleanup after each test
        System.out.println("Test case execution completed.");
    }

    @Test
    void createDepartment() {
        // Prepare test data
        DepartmentCreationDTO dto = new DepartmentCreationDTO();
        dto.setName("HR");

        Department department = new Department();
        department.setName("HR");

        DepartmentResponseDTO responseDTO = new DepartmentResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("HR");

        // Define behavior for mocked methods
        when(departmentMapper.departmentCreationDtoToEntity(dto)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentMapper.entityToDepartmentResponseDTO(department)).thenReturn(responseDTO);

        // Call the method under test
        DepartmentResponseDTO result = departmentService.createDepartment(dto);

        // Assertions to verify the expected outcome
        assertNotNull(result);
        assertEquals("HR", result.getName());
        verify(departmentRepository).save(department); // Verify save method was called
    }

    @Test
    void createDepartment_Exception() {
        DepartmentCreationDTO dto = new DepartmentCreationDTO();
        dto.setName("HR");

        // Prepare mocks to throw a DataAccessException during save
        when(departmentMapper.departmentCreationDtoToEntity(dto)).thenReturn(new Department());
        when(departmentRepository.save(any())).thenThrow(new RuntimeException("Database error"));

        // Call the method and expect a RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            departmentService.createDepartment(dto);
        });

        // Assert that the exception message matches the expected message from the service
        assertEquals("Error creating department", exception.getMessage());
    }


    @Test
    void getAllDepartments() {
        // Prepare test data
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");
        List<Department> departments = Collections.singletonList(department);

        // Prepare expected DTO
        List<DepartmentDropdownDTO> dropdownDTOs = Collections.singletonList(new DepartmentDropdownDTO(1L, "IT"));

        // Define behavior for mocked methods
        when(departmentRepository.findAll()).thenReturn(departments); // Mock repository to return departments
        when(departmentMapper.entityListToDropdownDTOList(departments)).thenReturn(dropdownDTOs); // Mock mapper behavior

        // Call the method under test
        List<DepartmentDropdownDTO> result = departmentService.getAllDepartments();

        // Assertions to verify the expected outcome
        assertNotNull(result);
        assertEquals(1, result.size()); // Check if size matches
        assertEquals("IT", result.get(0).getName()); // Check the name
        assertEquals(1L, result.get(0).getId()); // Check the ID
        verify(departmentRepository).findAll(); // Verify findAll method was called
        verify(departmentMapper).entityListToDropdownDTOList(departments); // Verify mapper method was called
    }

    @Test
    void getAllDepartments_Exception() {
        // Mock the repository to throw an exception
        when(departmentRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Call the method and expect a RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            departmentService.getAllDepartments();
        });
        assertEquals("Error fetching all departments", exception.getMessage());
    }

    @Test
    void getDepartmentById() {
        // Prepare test data
        Long id = 1L;
        Department department = new Department();
        department.setId(id);
        department.setName("Finance");

        DepartmentResponseDTO responseDTO = new DepartmentResponseDTO();
        responseDTO.setId(id);
        responseDTO.setName("Finance");

        // Define behavior for mocked methods
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        when(departmentMapper.entityToDepartmentResponseDTO(department)).thenReturn(responseDTO);

        // Call the method under test
        DepartmentResponseDTO result = departmentService.getDepartmentById(id);

        // Assertions to verify the expected outcome
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Finance", result.getName());
        verify(departmentRepository).findById(id); // Verify findById method was called
    }

    @Test
    void getDepartmentById_NotFound() {
        Long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        // Call the method and expect a DepartmentNotFoundException
        DepartmentNotFoundException exception = assertThrows(DepartmentNotFoundException.class, () -> {
            departmentService.getDepartmentById(id);
        });
        assertEquals("Department not found with id: " + id, exception.getMessage());
    }

    @Test
    void updateDepartment() {
        // Prepare test data
        Long id = 1L;
        DepartmentCreationDTO dto = new DepartmentCreationDTO();
        dto.setName("Marketing");

        Department existingDepartment = new Department();
        existingDepartment.setId(id);
        existingDepartment.setName("Sales");

        Department updatedDepartment = new Department();
        updatedDepartment.setId(id);
        updatedDepartment.setName("Marketing");

        DepartmentResponseDTO responseDTO = new DepartmentResponseDTO();
        responseDTO.setId(id);
        responseDTO.setName("Marketing");

        // Define behavior for mocked methods
        when(departmentRepository.findById(id)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(existingDepartment)).thenReturn(updatedDepartment);
        when(departmentMapper.entityToDepartmentResponseDTO(updatedDepartment)).thenReturn(responseDTO);

        // Call the method under test
        DepartmentResponseDTO result = departmentService.updateDepartment(id, dto);

        // Assertions to verify the expected outcome
        assertNotNull(result);
        assertEquals("Marketing", result.getName());
        verify(departmentRepository).save(existingDepartment); // Verify save method was called
    }

    @Test
    void updateDepartment_NotFound() {
        Long id = 1L;
        DepartmentCreationDTO dto = new DepartmentCreationDTO();
        dto.setName("Marketing");

        // Mock the repository to return an empty optional
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        // Call the method and expect a DepartmentNotFoundException
        DepartmentNotFoundException exception = assertThrows(DepartmentNotFoundException.class, () -> {
            departmentService.updateDepartment(id, dto);
        });
        assertEquals("Department not found with id: " + id, exception.getMessage());
    }

    @Test
    void deleteDepartment() {
        // Prepare test data
        Long id = 1L;
        when(departmentRepository.existsById(id)).thenReturn(true); // Simulate existence

        // Call the method under test and expect no exceptions
        assertDoesNotThrow(() -> departmentService.deleteDepartment(id));
        verify(departmentRepository).deleteById(id); // Verify deleteById method was called
    }

    @Test
    void deleteDepartment_NotFound() {
        // Prepare test data
        Long id = 1L;
        when(departmentRepository.existsById(id)).thenReturn(false); // Simulate non-existence

        // Call the method and expect an exception
        DepartmentNotFoundException exception = assertThrows(DepartmentNotFoundException.class, () -> {
            departmentService.deleteDepartment(id);
        });

        // Assertions to verify the expected exception message and error type
        assertEquals("Department not found with id: " + id, exception.getMessage());
        assertEquals("ValidationError", exception.getError()); // Verify the error type
    }

    @Test
    void checkDepartmentExistence() {
        // Prepare test data
        Long id = 1L;
        when(departmentRepository.existsById(id)).thenReturn(true); // Simulate existence

        // Call the method under test
        boolean exists = departmentService.checkDepartmentExistence(id);

        // Assertions to verify the expected outcome
        assertTrue(exists);
        verify(departmentRepository).existsById(id); // Verify existsById method was called
    }
}
