package com.spike.user.controller;

import com.spike.user.dto.UserDashboardDTO;
import com.spike.user.response.ResponseHandler;
import com.spike.user.service.userService.UserDashBoardService;
import com.spike.user.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/spike/user")
public class UserDashBoardController {

    @Autowired
    private UserDashBoardService userDashBoardService;


    //get api to display list of users on dashboard with filtration , pagination and sorting
    @GetMapping("/userinfo-dashboard")
    public ResponseEntity<Object> getSpecificUserInfoByUsername(@RequestParam(name = "name", required = false) String name,
                                                                @RequestParam(name = "email", required = false) String email,
                                                                @RequestParam(name = "joiningDate", required = false) Date joiningDate,
                                                                @RequestParam(name = "salary", required = false) Double salary,
                                                                @RequestParam(name = "page", defaultValue = "0") int page,
                                                                @RequestParam(name = "size", defaultValue = "6") int size,
                                                                @RequestParam(name = "sort", defaultValue = "joiningDate,desc") String sort) {
        try {
            List<UserDashboardDTO> userDashBoard = userDashBoardService.getUserFilteredDashboard(name, email, joiningDate, salary, page, size, sort);
            return ResponseHandler.responseBuilder("user info dashboard displayed successfully", HttpStatus.FOUND, userDashBoard);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }
    }


    //get api to display list of all users on dashboard
    @GetMapping("user-dashboard")
    public ResponseEntity<Object> getAllUsersDashBoard(@RequestParam(name = "pagesize", required = false, defaultValue = "6") int pagesize, @RequestParam(name = "pageno", required = false, defaultValue = "0") int pageno, @RequestParam(name = "sort", defaultValue = "name,asc") String sort) {
        try {
            List<UserDashboardDTO> userDashBoard = userDashBoardService.getUsersForDashboard(pagesize, pageno, sort);
            return ResponseHandler.responseBuilder("users dashboard displayed successfully", HttpStatus.FOUND, userDashBoard);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }
    }

}
