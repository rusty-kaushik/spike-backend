package com.spike.SecureGate.service.externalDepartmentService;

import com.spike.SecureGate.DTO.departmentDto.DepartmentCreationDTO;
import com.spike.SecureGate.Validations.DepartmentValidator;
import com.spike.SecureGate.feignClients.UserFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentValidator departmentValidator;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public ResponseEntity<Object> createDepartment(DepartmentCreationDTO department) {
        try {
            if (!departmentValidator.validateDepartmentCreation(department)) {
                return ResponseEntity.badRequest().body("Invalid department data");
            }
            ResponseEntity<Object> department1 = userFeignClient.createDepartment(department);
            return department1;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }
    }

    @Override
    public ResponseEntity<Object> departmentDropdown() {
        try {
            ResponseEntity<Object> responseEntity = userFeignClient.departmentDropdown();
            return responseEntity;
        } catch (FeignException e) {
                return ResponseEntity.status(e.status()).body(e.contentUTF8());
            }
    }

    @Override
    public ResponseEntity<Object> departmentById(Long id) {
        try {
            if (!departmentValidator.validateDepartmentExists(id)) {
                return ResponseEntity.badRequest().body("Invalid department");
            }
            ResponseEntity<Object> departmentById = userFeignClient.getDepartmentById(id);
            return departmentById;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }
    }

    @Override
    public ResponseEntity<Object> updateDepartmentById(Long id, DepartmentCreationDTO department) {
        try {
            if (!departmentValidator.validateDepartmentCreation(department)) {
                return ResponseEntity.badRequest().body("Invalid department data");
            }
            if (!departmentValidator.validateDepartmentExists(id)) {
                return ResponseEntity.badRequest().body("Invalid department");
            }
            ResponseEntity<Object> responseEntity = userFeignClient.updateDepartment(id, department);
            return responseEntity;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }
    }

    @Override
    public ResponseEntity<Object> deleteDepartmentById(Long id) {
        try {
            if (!departmentValidator.validateDepartmentExists(id)) {
                return ResponseEntity.badRequest().body("Invalid department");
            }
            ResponseEntity<Object> responseEntity = userFeignClient.deleteDepartment(id);
            return responseEntity;
        } catch (FeignException e) {
                return ResponseEntity.status(e.status()).body(e.contentUTF8());
            }
    }
}
