package com.spike.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.user.auditing.AuditorAwareImpl;
import com.spike.user.dto.*;
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

import java.util.List;

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
            description = "Creates a new user with a profile picture. Pass the JSON body in the 'data' part and the file in the 'file' part."
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
            description = "Updates the password of user. The API takes old password and new  password in json."
    )
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
        } catch (UserNotFoundException | PasswordNotMatchException | UnexpectedException e) {
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

    // API TO UPDATE USER FULL DETAILS
    @Operation(
            summary = "Self updates a user full details",
            description = "Updates an existing user. Pass the JSON body in the 'data' part."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping(value = "/self/{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserFullUpdateDTO userFullUpdateDTO
    ) {
        logger.info("Received request to update user with ID: {}", userId);
        try {
            AuditorAwareImpl.setCurrentAuditor(userFullUpdateDTO.getUsername());
            logger.info("Setting current auditor to 'username'");

            // Perform the update operation
            logger.info("Updating user with ID: {}", userId);
            User updatedUser = userService.updateUserFull(userId, userFullUpdateDTO);
            logger.info("User updated successfully with ID: {}", userId);

            return ResponseHandler.responseBuilder("User successfully updated", HttpStatus.OK, updatedUser);
        } catch (UserNotFoundException e) {
            logger.error("User with ID {} not found: {}", userId, e.getMessage());
            return ResponseHandler.responseBuilder("User not found", HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating user: {}", e.getMessage());
            return ResponseHandler.responseBuilder("Error updating user", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            logger.info("Clearing current auditor");
            AuditorAwareImpl.clear();
        }
    }


    // API TO UPDATE USER PROFILE PICTURE
    @Operation(
            summary = "Self updates user's profile picture",
            description = "Updates the profile picture of a user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated user profile picture",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping(value = "/self/profile-picture/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateProfilePicture(
            @PathVariable Long userId,
            @RequestBody MultipartFile profilePicture) {
        logger.info("Received request to update profile picture for user ID: {}", userId);
        try {
            userService.updateUserProfilePicture(userId, profilePicture);
            logger.info("User profile picture updated successfully for user ID: {}", userId);
            return ResponseHandler.responseBuilder("User profile updated", HttpStatus.OK, "Successful");
        } catch (UserNotFoundException e) {
            logger.error("User with ID {} not found: {}", userId);
            return ResponseHandler.responseBuilder("User profile not found", HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating profile picture for user ID {}: {}", userId, e.getMessage());
            return ResponseHandler.responseBuilder("User profile update unsuccessful", HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    // API TO DELETE USER
    @Operation(
            summary = "Admin deletes a user",
            description = "Deletes a user based on user ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted the user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long userId) {
        logger.info("Received request to delete user with ID: {}", userId);
        try {
            userService.deleteUser(userId);
            logger.info("User deleted successfully with ID: {}", userId);
            return ResponseHandler.responseBuilder("User successfully deleted", HttpStatus.OK, "User deleted");
        } catch (UserNotFoundException e) {
            logger.error("User with ID {} not found: {}", userId);
            return ResponseHandler.responseBuilder("User not found", HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", userId, e.getMessage());
            return ResponseHandler.responseBuilder("User deletion unsuccessful", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    //get api to get a particular user contact details if provided , otherwise all the user contacts details with the user name
    @Operation(
            summary = "Fetch user contact details",
            description = "fetch user contact details by user name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "user contacts found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not-Found",
                    content = {@Content(schema = @Schema())})})

    @GetMapping("usercontacts")
    public ResponseEntity<Object> getUserContact(@RequestParam(name="name", required=false) String name, 
                                                 @RequestParam(name = "pagesize", required = false, defaultValue = "5") int pagesize, 
                                                 @RequestParam(name = "pageno", required = false, defaultValue = "0") int pageno, 
                                                 @RequestParam(name = "sort", defaultValue = "name,asc") String sort) {
        try {
            logger.info("getting user contact information " + name);
            List<UserContactsDTO> user = userService.getUserContacts(name, pageno, pagesize, sort);
            return ResponseHandler.responseBuilder("user contacts found successfully", HttpStatus.OK, user);
        } catch (UserNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error("Error occur while fetching user data: {}", e.getMessage());
            throw new RuntimeException("Error fetching user", e.getCause());
        }

    }


    //get api to display list of all users on dashboard with filtration , pagination and sorting
    @Operation(
            summary = "Fetch user details for dashboard",
            description = "fetch user details by user name , email , joiningDate, salary"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "user details found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not-Found",
                    content = {@Content(schema = @Schema())})})
    @GetMapping("/userinfo-dashboard")
    public ResponseEntity<Object> getSpecificUserInfoByUsername(@RequestParam(name = "name", required = false) String name,
                                                                @RequestParam(name = "email", required = false) String email,
                                                                @RequestParam(name = "salary", required = false) Double salary,
                                                                @RequestParam(name = "page", defaultValue = "0") int page,
                                                                @RequestParam(name = "size", defaultValue = "6") int size,
                                                                @RequestParam(name = "sort", defaultValue = "name,desc") String sort) {
        try {
            List<UserDashboardDTO> userDashBoard = userService.getUserFilteredDashboard(name, email, salary, page, size, sort);
            return ResponseHandler.responseBuilder("user info dashboard displayed successfully", HttpStatus.OK, userDashBoard);
        } catch (UserNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }
    }

    @GetMapping("/username/{username}")
    public UserInfoDTO getUserByUsername(@PathVariable String username) {
        try {
            UserInfoDTO user = userService.getUserByUsername(username);
            return user;
        } catch (UserNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }

    }

}
