package com.spike.SecureGate.Validations;

import com.spike.SecureGate.DTO.departmentDto.DepartmentCreationDTO;
import com.spike.SecureGate.feignClients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DepartmentValidator {

    @Autowired
    private UserFeignClient userFeignClient;

    public boolean validateDepartmentCreation(DepartmentCreationDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty() ) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        return true;
    }

    public boolean validateDepartmentExists(Long id) {
        if(!userFeignClient.checkDepartmentExistence(id)) {
            throw new IllegalArgumentException("Department does not exist.");
        }
        return true;
    }



}
