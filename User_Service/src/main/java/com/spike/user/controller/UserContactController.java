package com.spike.user.controller;

import com.spike.user.dto.UserContactsDTO;
import com.spike.user.exceptions.EmployeeNotFoundException;
import com.spike.user.response.ResponseHandler;
import com.spike.user.service.userService.UserContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spike/user")
public class UserContactController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserContactService userContactService;

    //get api to get a particular user contact details with the user name
    @Operation(
            summary = "Fetch user contact details",
            description = "fetch user contact details by user name",
            tags = {"Admin", "Manager", "Employee", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "user contacts found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not-Found",
                    content = {@Content(schema = @Schema())})})

    @GetMapping("usercontacts")
    public ResponseEntity<Object> getUserContact(@RequestParam("name") String name, @RequestParam(name = "pagesize", required = false, defaultValue = "5") int pagesize, @RequestParam(name = "pageno", required = false, defaultValue = "0") int pageno, @RequestParam(name = "sort", defaultValue = "name,asc") String sort) {
        try {
            logger.info("getting user contact information "+ name);
            List<UserContactsDTO> user = userContactService.getUserContacts(name, pageno, pagesize, sort);
            return ResponseHandler.responseBuilder("user contacts found successfully", HttpStatus.FOUND, user);
        } catch (EmployeeNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error("Error occur while fetching user data: {}", e.getMessage());
            throw new RuntimeException("Error fetching user", e.getCause());
        }


    }

    //get api to display list of all the user contacts on user-dashboard
    @Operation(
            summary = "Fetch all user contact details",
            description = "fetch all user contact details ",
            tags = {"Admin", "Manager", "Employee", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "user contacts found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not-Found",
                    content = {@Content(schema = @Schema())})})

    @GetMapping("/users-contacts-dashboard")
    public ResponseEntity<Object> getAllUserContact(@RequestParam(name = "pagesize", required = false, defaultValue = "5") int pagesize, @RequestParam(name = "pageno", required = false, defaultValue = "0") int pageno, @RequestParam(name = "sort", defaultValue = "name,asc") String sort) {
        try {
            logger.info("getting user contacts information");
            List<UserContactsDTO> userContacts = userContactService.getAllUsersContact(pagesize, pageno, sort);
            return ResponseHandler.responseBuilder("Users Contact found successfully", HttpStatus.FOUND, userContacts);
        } catch (EmployeeNotFoundException ex) {
            throw ex;
        } catch
        (Exception e) {
            logger.error("Error occur while fetching user data: {}", e.getMessage());
            throw new RuntimeException("Error fetching user", e.getCause());

        }
    }
}
