package com.spike.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.user.auditing.AuditorAwareImpl;
import com.spike.user.dto.UserChangePasswordDTO;
import com.spike.user.dto.UserCreationRequestDTO;
import com.spike.user.entity.User;
import com.spike.user.exceptions.*;
import com.spike.user.response.ResponseHandler;
import com.spike.user.service.userService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/spike/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // API TO CREATE A NEW USER
    @Operation(
            summary = "Admin creates a new user",
            description = "Creates a new user with a profile picture. Pass the JSON body in the 'data' part and the file in the 'file' part.",
            tags = { "Admin", "post" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created a new user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @PostMapping(value = "/new-user/{username}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<Object>  createNewUser(
            @RequestPart("file") MultipartFile profilePicture,
            @RequestPart("data") String data,
            @PathVariable(value = "username") String username
    ) throws JsonProcessingException {
        logger.info("Received request to create a new user");
        try {
            logger.info("Setting current auditor to username: {}", username);
            AuditorAwareImpl.setCurrentAuditor(username);
            UserCreationRequestDTO userRequest = objectMapper.readValue(data, UserCreationRequestDTO.class);
            logger.info("Creating new user with username: {}", userRequest.getEmployeeCode());
            User createdUser = userService.createNewUser(profilePicture, userRequest);
            logger.info("User created successfully with username: {}", username);
            return ResponseHandler.responseBuilder("user successfully created", HttpStatus.OK, createdUser);
        } catch (DepartmentNotFoundException | RoleNotFoundException | DtoToEntityConversionException | UnexpectedException     e ) {
            throw e;
        }  catch (JsonProcessingException e) {
            logger.error("Error parsing user creation request data: {}", e.getMessage());
            return ResponseHandler.responseBuilder("Invalid user creation request data", HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
        finally {
            logger.info("Clearing current auditor");
            AuditorAwareImpl.clear();
        }
    }


    // API TO UPDATE USER SELF PASSWORD
    @Operation(
            summary = "Update self password",
            description = "Updates the password of user. The API takes old password and new  password in json.",
            tags = { "Admin","Manager","Employee", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the password of user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @PutMapping("/reset-password/{username}")
    public ResponseEntity<Object> updateSelfPassword(@PathVariable String username, @RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        try {
            logger.info("Setting current auditor to username: {}", username);
            AuditorAwareImpl.setCurrentAuditor(username);
            String createdUser = userService.updateSelfPassword(username, userChangePasswordDTO);
            logger.info("User password Successfully changed for username: {}", username);
            return ResponseHandler.responseBuilder("user password update successful", HttpStatus.OK, createdUser);
        } catch (EmployeeNotFoundException | PasswordNotMatchException | UnexpectedException e) {
            logger.error("Error updating password for user: {}", username, e);
            throw e;
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("user password update unsuccessful", HttpStatus.UNPROCESSABLE_ENTITY, "Please try again");
        } finally {
            logger.info("Clearing current auditor");
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