package com.spike.SecureGate.service.externalDepartmentService;

import com.spike.SecureGate.DTO.departmentDto.DepartmentCreationDTO;
import com.spike.SecureGate.DTO.userDto.UserChangePasswordDTO;
import com.spike.SecureGate.DTO.userDto.UserFullUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DepartmentService {
    // CREATE DEPARTMENT
    ResponseEntity<Object> createDepartment(DepartmentCreationDTO department);


    ResponseEntity<Object> departmentDropdown();

    ResponseEntity<Object> departmentById(Long id);

    ResponseEntity<Object> updateDepartmentById(Long id, DepartmentCreationDTO department);

    ResponseEntity<Object> deleteDepartmentById(Long id);
}
