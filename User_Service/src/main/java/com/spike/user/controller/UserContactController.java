package com.spike.user.controller;

import com.spike.user.dto.UserContactsDTO;
import com.spike.user.response.ResponseHandler;
import com.spike.user.service.userService.UserContactService;
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

    @Autowired
    private UserContactService userContactService;
    //get api to get a particular user contact details with the user name
    @GetMapping("usercontacts")
    public ResponseEntity<Object> getUserContact(@RequestParam("name") String name, @RequestParam(name = "pagesize", required = false, defaultValue = "5") int pagesize, @RequestParam(name = "pageno", required = false, defaultValue = "0") int pageno, @RequestParam(name = "sort", defaultValue = "name,asc") String sort) {
        try {
            List<UserContactsDTO> user = userContactService.getUserContacts(name, pageno, pagesize, sort);
            return ResponseHandler.responseBuilder("user contacts found successfully", HttpStatus.FOUND, user);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }


    }

    //get api to display list of all the user contacts on user-dashboard
    @GetMapping("/users-contacts-dashboard")
    public ResponseEntity<Object> getAllUserContact(@RequestParam(name = "pagesize", required = false, defaultValue = "5") int pagesize, @RequestParam(name = "pageno", required = false, defaultValue = "0") int pageno, @RequestParam(name = "sort", defaultValue = "name,asc") String sort) {
        try {
            List<UserContactsDTO> userContacts = userContactService.getAllUsersContact(pagesize, pageno, sort);
            return ResponseHandler.responseBuilder("Users Contact found successfully", HttpStatus.FOUND, userContacts);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e.getCause());

        }
    }
}
