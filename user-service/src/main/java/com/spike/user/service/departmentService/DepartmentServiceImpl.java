package com.spike.user.service.departmentService;

import com.spike.user.customMapper.DepartmentMapper;
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

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponseDTO createDepartment(DepartmentCreationDTO departmentCreationDTO) {
        logger.info("Creating new department: {}", departmentCreationDTO);
        try {
            // Use MapStruct to convert DTO to entity
            Department newDepartment = departmentMapper.departmentCreationDtoToEntity(departmentCreationDTO);
            Department savedDepartment = departmentRepository.save(newDepartment);
            return departmentMapper.entityToDepartmentResponseDTO(savedDepartment);
        } catch (Exception e) {
            logger.error("Error creating department", e);
            throw new RuntimeException("Error creating department", e);
        }
    }

    @Override
    public List<DepartmentDropdownDTO> getAllDepartments() {
        logger.info("Fetching all departments");
        try {
            List<Department> departments = departmentRepository.findAll();
            return departmentMapper.entityListToDropdownDTOList(departments);
        } catch (Exception e) {
            logger.error("Error fetching all departments", e);
            throw new RuntimeException("Error fetching all departments", e);
        }
    }

    @Override
    public DepartmentResponseDTO getDepartmentById(Long id) {
        logger.info("Fetching department with id: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("ValidationError","Department not found with id: " + id));

        // Use MapStruct to convert entity to DTO
        return departmentMapper.entityToDepartmentResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO getDepartmentByName(String name) {
        logger.info("Fetching department with name: {}", name);
        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new DepartmentNotFoundException("ValidationError","Department not found with name: " + name));

        // Use MapStruct to convert entity to DTO
        return departmentMapper.entityToDepartmentResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentCreationDTO departmentCreationDTO) {
        logger.info("Updating department with id: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("ValidationError","Department not found with id: " + id));

        // Update entity with new data
        department.setName(departmentCreationDTO.getName());
        Department updatedDepartment = departmentRepository.save(department);

        // Convert updated entity to response DTO
        return departmentMapper.entityToDepartmentResponseDTO(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        logger.info("Deleting department with id: {}", id);
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new DepartmentNotFoundException("ValidationError","Department not found with id: " + id);
        }
    }

    @Override
    public boolean checkDepartmentExistence(Long id) {
        return departmentRepository.existsById(id);
    }
}
