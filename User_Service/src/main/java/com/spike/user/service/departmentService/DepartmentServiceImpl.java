package com.spike.user.service.departmentService;

import com.spike.user.customMapper.UserMapper;
import com.spike.user.dto.DepartmentCreationDTO;
import com.spike.user.dto.DepartmentDropdownDTO;
import com.spike.user.dto.DepartmentResponseDTO;
import com.spike.user.entity.Department;
import com.spike.user.exceptions.DepartmentNotFoundException;
import com.spike.user.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserMapper userMapper;

    // Create a new department
    @Override
    public DepartmentResponseDTO createDepartment(DepartmentCreationDTO department) {
        logger.info("Creating new department: {}", department);
        try {
            Department newDepartment = new Department();
            newDepartment.setName(department.getName());
            Department savedDepartment = departmentRepository.save(newDepartment);
            DepartmentResponseDTO responseDTO = userMapper.entityToDepartmentDtoResponse(savedDepartment);
            logger.info("Successfully created department with id: {}", savedDepartment.getId());
            return responseDTO;
        } catch (Exception e) {
            logger.error("Error creating department", e);
            throw new RuntimeException("Error creating department", e);
        }
    }

    //Get list of all departments
    @Override
    public List<DepartmentDropdownDTO> getAllDepartments() {
        logger.info("Fetching all departments");
        try {
            List<DepartmentDropdownDTO> allDepartmentDTOs = departmentRepository.findAllDepartmentDTOs();
            logger.info("Successfully fetched all departments");
            return allDepartmentDTOs;
        } catch (Exception e) {
            logger.error("Error fetching all departments", e);
            throw new RuntimeException("Error fetching all departments", e);
        }
    }

    // Get department by id
    @Override
    public DepartmentResponseDTO getDepartmentById(Long id) {
        logger.info("Fetching department with id: {}", id);
        try {
            // Fetch the department entity
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));

            // Convert the Department entity to DepartmentResponseDTO
            DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();
            departmentResponseDTO.setId(department.getId());
            departmentResponseDTO.setName(department.getName());

            logger.info("Successfully fetched department with id: {}", id);
            return departmentResponseDTO;
        } catch (DepartmentNotFoundException e) {
            logger.error("Department not found with id: {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error fetching department with id: {}", id, e);
            throw new RuntimeException("Unexpected error fetching department", e);
        }
    }


    // Update department by id
    @Override
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentCreationDTO departmentCreationDTO) {
        logger.info("Updating department with id: {}", id);
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));
            department.setName(departmentCreationDTO.getName());
            Department updatedDepartment = departmentRepository.save(department);
            logger.info("Successfully updated department with id: {}", id);
            DepartmentResponseDTO responseDTO = userMapper.entityToDepartmentDtoResponse(updatedDepartment);
            return responseDTO;
        } catch (DepartmentNotFoundException e) {
            logger.error("Error updating department - not found with id: {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error updating department with id: {}", id, e);
            throw new RuntimeException("Unexpected error updating department", e);
        }
    }

    // Delete department by id
    @Override
    public void deleteDepartment(Long id) {
        logger.info("Attempting to delete department with id: {}", id);
        try {
            if (departmentRepository.existsById(id)) {
                departmentRepository.deleteById(id);
                logger.info("Successfully deleted department with id: {}", id);
            } else {
                logger.warn("Department with id {} not found", id);
                throw new DepartmentNotFoundException("Department not found with id: " + id);
            }
        } catch (DepartmentNotFoundException e) {
            logger.error("Department not found with id: {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting department with id: {}", id, e);
            throw new RuntimeException("Unexpected error deleting department", e);
        }
    }


    @Override
    public boolean checkDepartmentExistence(String name) {
        Optional<Department> byName = departmentRepository.findByName(name);
        return byName.isPresent();
    }
}
