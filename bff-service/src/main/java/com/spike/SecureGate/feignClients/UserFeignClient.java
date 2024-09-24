package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.departmentDto.DepartmentCreationDTO;
import com.spike.SecureGate.DTO.publicDto.UserInfoDTO;
import com.spike.SecureGate.DTO.userDto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "userClient",  url = "${spike.service.user_service}")
public interface UserFeignClient {

    // create new user
    @PostMapping("/spike/user/new-user/{username}")
    ResponseEntity<Object> createNewUser(
            @PathVariable("username") String username,
            @RequestBody UserCreationRequestDTO data
    );

    // update user password
    @PutMapping("/spike/user/reset-password/{username}")
    ResponseEntity<Object> updateSelfPassword(@PathVariable("username") String username,@RequestBody UserChangePasswordDTO userChangePasswordDTO);

    // UPDATE SELF DETAILS
    @PutMapping(value = "/spike/user/self/{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserFullUpdateDTO userFullUpdateDTO
    );

    // UPDATE SELF PROFILE PICTURE
    @PutMapping(value = "/spike/user/self/profile-picture/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object> updateProfilePicture(
            @PathVariable Long userId,
            @RequestBody MultipartFile profilePicture
    );

    // DELETE USER
    @DeleteMapping("/spike/user/delete-user/{userId}")
    ResponseEntity<Object> deleteUser(
            @PathVariable Long userId
    );

    // FIND USER BY USERNAME
    @GetMapping("/spike/user/username/{username}")
    UserInfoDTO getUserByUsername(
            @PathVariable String username
    );

    // GET USERS FOR EMPLOYEE PAGE
    @GetMapping("/spike/user/userinfo-dashboard")
    ResponseEntity<Object> getSpecificUserInfoByUsername(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam Double salary,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort
    );

    // GET USERS FOR CONTACT PAGE
    @GetMapping("/spike/user/usercontacts")
    public ResponseEntity<Object> getUserContact(
            @RequestParam String name,
            @RequestParam int pagesize,
            @RequestParam int pageno,
            @RequestParam String sort
    );

    @PostMapping("/spike/department/create-new")
    ResponseEntity<Object> createDepartment(
            @RequestBody DepartmentCreationDTO department
    );

    @GetMapping("/spike/department/dropdown")
    ResponseEntity<Object> departmentDropdown();

    @GetMapping("/spike/department/{`id`}")
    ResponseEntity<Object> getDepartmentById(
            @PathVariable Long id
    );

    @GetMapping("/spike/department/exist/{id}")
    boolean checkDepartmentExistence(
            @PathVariable Long id
    );

    @PutMapping("/spike/department/{id}")
    ResponseEntity<Object> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentCreationDTO department
    );

    @DeleteMapping("/spike/department/{id}")
    ResponseEntity<Object> deleteDepartment(
            @PathVariable Long id
    );

    @PostMapping(value = "/spike/user/add/picture/{userId}/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object>  addProfilePictureOfAUser(
            @PathVariable long userId,
            @PathVariable String username,
            @RequestBody MultipartFile profilePicture
    );

    @GetMapping("/spike/user/self/{userId}")
    ResponseEntity<Object> getUserById(
            @PathVariable long userId
    );

    @GetMapping("/spike/user/departments/{userId}")
    ResponseEntity<Object> getDepartmentsByUserId(
            @PathVariable Long userId
    );

    @GetMapping("/spike/user")
    List<Object> getAllManagers();
}
