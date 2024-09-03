package com.spike.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.user.auditing.AuditorAwareImpl;
import com.spike.user.dto.UserChangePasswordDTO;
import com.spike.user.dto.UserCreationRequestDTO;
import com.spike.user.entity.User;
import com.spike.user.response.ResponseHandler;
import com.spike.user.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/spike/user")
public class UserController {

    @Autowired
    private UserService userService;

    // ObjectMapper instance to handle JSON conversion
    private final ObjectMapper objectMapper = new ObjectMapper();


    // API TO CREATE A NEW USER
    @PostMapping(value = "/new-user/{username}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<Object>  createNewUser(
            @RequestPart("file") MultipartFile profilePicture,
            @RequestPart("data") String data,
            @PathVariable(value = "username") String username
    ) {
        try {
            AuditorAwareImpl.setCurrentAuditor(username);
            UserCreationRequestDTO userRequest = objectMapper.readValue(data, UserCreationRequestDTO.class);
            User createdUser = userService.createNewUser(profilePicture, userRequest);
            return ResponseHandler.responseBuilder("user successfully created", HttpStatus.OK, createdUser);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("user creation unsuccessful", HttpStatus.UNPROCESSABLE_ENTITY, "Please try again");
        } finally {
            AuditorAwareImpl.clear();
        }
    }


    // API TO UPDATE USER SELF PASSWORD
    @PutMapping("/reset-password/{username}")
    public ResponseEntity<Object> updateSelfPassword(@PathVariable String username, @RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        try {
            AuditorAwareImpl.setCurrentAuditor(username);
            String createdUser = userService.updateSelfPassword(username, userChangePasswordDTO);
            return ResponseHandler.responseBuilder("user password update successful", HttpStatus.OK, createdUser);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("user password update unsuccessful", HttpStatus.UNPROCESSABLE_ENTITY, "Please try again");
        } finally {
            AuditorAwareImpl.clear();
        }
    }


    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try{
            return ResponseHandler.responseBuilder("user creation unsuccessful", HttpStatus.OK, userService.getAllUsers());
        } catch ( Exception e ) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }
    }
}