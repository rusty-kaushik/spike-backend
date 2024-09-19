package com.spike.SecureGate.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spike.SecureGate.DTO.departmentDto.DepartmentCreationDTO;
import com.spike.SecureGate.DTO.userDto.UserChangePasswordDTO;
import com.spike.SecureGate.DTO.userDto.UserFullUpdateDTO;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.service.externalDepartmentService.DepartmentService;
import com.spike.SecureGate.service.externalUserService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//@CrossOrigin("*")
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    // CREATE NEW DEPARTMENT
    @Operation(
            summary = "CREATE NEW DEPARTMENT",
            description = "CREATE NEW DEPARTMENT"
    )
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createDepartment(
            @RequestBody DepartmentCreationDTO department
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching users for contact page");
        ResponseEntity<Object> user = departmentService.createDepartment(department);
        logger.info("Finished fetching users for contact page");
        return user;
    }

    @Operation(
            summary = "DEPARTMENT DROPDOWN",
            description = "DEPARTMENT DROPDOWN WHILE CREATING USER"
    )
    @GetMapping("/dropdown")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> departmentDropdown()
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching users for contact page");
        ResponseEntity<Object> user = departmentService.departmentDropdown();
        logger.info("Finished fetching users for contact page");
        return user;
    }

//    @Operation(
//            summary = "DEPARTMENT BY ID ",
//            description = "DEPARTMENT BY ID"
//    )
//    @GetMapping("/{id}")
//    //@PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Object> departmentById(
//            @PathVariable long id
//    )
//    {
//        logger.info("Started authenticating");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        logger.info("Authentication Successful");
//        logger.info("Started fetching users for contact page");
//        ResponseEntity<Object> user = departmentService.departmentById(id);
//        logger.info("Finished fetching users for contact page");
//        return user;
//    }

    @Operation(
            summary = "UPDATE DEPARTMENT BY ID ",
            description = "UPDATE DEPARTMENT BY ID"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateDepartmentById(
            @PathVariable Long id,
            @RequestBody DepartmentCreationDTO department
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching users for contact page");
        ResponseEntity<Object> user = departmentService.updateDepartmentById(id,department);
        logger.info("Finished fetching users for contact page");
        return user;
    }

    @Operation(
            summary = "DELETE DEPARTMENT BY ID ",
            description = "DELETE DEPARTMENT BY ID"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteDepartmentById(
            @PathVariable Long id
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching users for contact page");
        ResponseEntity<Object> user = departmentService.deleteDepartmentById(id);
        logger.info("Finished fetching users for contact page");
        return user;
    }


}
