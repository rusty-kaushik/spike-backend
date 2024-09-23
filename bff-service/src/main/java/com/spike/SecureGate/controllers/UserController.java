package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.userDto.*;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.helper.DropdownHelper;
import com.spike.SecureGate.service.externalUserService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.io.IOException;
import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private DropdownHelper dropdownHelper;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // CREATE A NEW USER
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
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createUser(
            @RequestBody UserCreationRequestDTO data
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new User");
        ResponseEntity<Object> user = userService.createUser(userName, data);
        logger.info("Finished creating new User");
        return user;
    }

    // ADD PROFILE PICTURE
    @Operation(
            summary = "User adds a new profile picture",
            description = "Adds a new profile picture"
    )
    @PostMapping(value = "/add/picture/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> addProfilePicture(
            @PathVariable long userId,
            @RequestBody MultipartFile profilePicture
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new User");
        ResponseEntity<Object> user = userService.addProfilePicture(profilePicture, userName, userId);
        logger.info("Finished creating new User");
        return user;
    }

    // CHANGE USER SELF PASSWORD
    @Operation(
            summary = "Update self password",
            description = "Updates the password of user. The API takes old password and new  password in json.")
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
    @PutMapping("/reset-password")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> updateSelfPassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO)

    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started updating user password");
        ResponseEntity<Object> user = userService.updateSelfPassword(userName, userChangePasswordDTO);
        logger.info("Finished updating user password");
        return user;
    }

    // UPDATE SELF DETAILS
    @Operation(
            summary = "Update self details",
            description = "Updates the details of user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the details of user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @PutMapping("/update/details/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> updateSelfDetails(
            @PathVariable long userId,
            @RequestBody UserFullUpdateDTO userFullUpdateDTO
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Stared updating self details");
        ResponseEntity<Object> user = userService.updateSelfDetails(userId, userFullUpdateDTO);
        logger.info("Finished updating self details");
        return user;
    }

    // UPDATE SELF PROFILE PICTURE
    @Operation(
            summary = "Update self profile picture",
            description = "Updates the profile picture of user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the profile of user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @PutMapping(value = "/update/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> updateSelfPictureDetails(
            @RequestBody MultipartFile profilePicture
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        long userId = userDbService.getIdByUsername(userName);
        logger.info("Started updating user profile picture");
        ResponseEntity<Object> user = userService.updateSelfProfilePictureDetails(userId,profilePicture);
        logger.info("Finished updating user profile picture");
        return user;
    }

    // DELETE USER
    @Operation(
            summary = "Delete a user",
            description = "Delete a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteUser(
            @PathVariable Long userId
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started deleting a user");
        ResponseEntity<Object> user = userService.deleteUser(userId);
        logger.info("Finished deleting a user");
        return user;
    }

    // FETCH USERS FOR EMPLOYEE PAGE
    @Operation(
            summary = "Fetch users for employee page",
            description = "Fetch users for employee page."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the addresses of user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/employees")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> fetchUsersForEmployeePage(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "salary", required = false) Double salary,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "updatedAt,desc") String sort
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching users for employee page");
        ResponseEntity<Object> user = userService.fetchUsersForEmployeePage(name,email,salary,page,size,sort);
        logger.info("Finished fetching users for employee page");
        return user;
    }

    // FETCH USERS FOR CONTACT PAGE
    @Operation(
            summary = "Fetch users for contacts page",
            description = "Fetch users for contacts page."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the addresses of user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/contacts")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> fetchUsersForContactPage(
            @RequestParam(required=false) String name,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "sort", defaultValue = "updatedAt,desc") String sort
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching users for contact page");
        ResponseEntity<Object> user = userService.fetchUsersForContactPage(name,pageSize,pageNo,sort);
        logger.info("Finished fetching users for contact page");
        return user;
    }

    // FETCH USER DETAILS FOR MY ACCOUNT PAGE
    @Operation(
            summary = "Fetch users for my account page",
            description = "Fetch users for contacts page."
    )
    @GetMapping("/self/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> fetchSelfDetails(
         @PathVariable long userId
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching users for contact page");
        ResponseEntity<Object> user = userService.fetchSelfDetails(userId);
        logger.info("Finished fetching users for contact page");
        return user;
    }

    // FETCH USER Departments
    @Operation(
            summary = "Fetch users departments",
            description = "Department dropdown list for my a specific user"
    )
    @GetMapping("/department/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> fetchDepartmentsOfAUser(
            @PathVariable long userId
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching users department");
        ResponseEntity<Object> user = userService.fetchDepartmentsOfAUser(userId);
        logger.info("Finished fetching users departments");
        return user;
    }

    @GetMapping("/countries")
    public List<Country> getCountries() {
        try {
            return dropdownHelper.readCsvFileForCountry();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/states")
    public List<State> getStatesByCountry(@RequestParam String countryName) throws IOException {
        return dropdownHelper.getStatesByCountryName(countryName);
    }

    @GetMapping("/cities")
    public List<City> getCitiesByState(@RequestParam String stateName) throws IOException {
        return dropdownHelper.getCitiesByStateName(stateName);
    }

    @GetMapping("/managers")
    public List<Object> getAllManagers(){
        return dropdownHelper.getAllManagers();
    }
}
