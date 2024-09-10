package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.userDto.UserAddressDTO;
import com.spike.SecureGate.DTO.userDto.UserChangePasswordDTO;
import com.spike.SecureGate.DTO.userDto.UserSocialUpdateDTO;
import com.spike.SecureGate.DTO.userDto.UserUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "userClient",  url = "${spike.service.user_service}")
public interface UserFeignClient {

    // create new user
    @PostMapping(value = "/spike/user/new-user/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object> createNewUser(
            @PathVariable("username") String username,
            @RequestPart("file") MultipartFile profilePicture,
            @RequestPart("data") String data
    );

    // update user password
    @PutMapping("/spike/user/reset-password/{username}")
    ResponseEntity<Object> updateSelfPassword(@PathVariable("username") String username,@RequestBody UserChangePasswordDTO userChangePasswordDTO);

    // UPDATE SELF DETAILS
    @PutMapping(value = "/spike/user/self/{userId}")
    ResponseEntity<Object> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserUpdateRequestDTO userRequest
    );

    // UPDATE SELF SOCIAL URLS
    @PutMapping("/spike/user/self/socials/{userId}/{username}")
    ResponseEntity<Object> updateSocialUrls(
            @PathVariable Long userId,
            @PathVariable String username,
            @RequestBody UserSocialUpdateDTO userSocialUpdateDTO
    );

    // UPDATE SELF ADDRESSES
    @PutMapping("/spike/user/self/addresses/{userId}/{username}")
    ResponseEntity<Object> updateAddresses(
            @PathVariable Long userId,
            @PathVariable String username,
            @RequestBody List<UserAddressDTO> addresses
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

}
