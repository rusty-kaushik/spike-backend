package com.spike.SecureGate.service.externalUserService;

import com.spike.SecureGate.DTO.userDto.UserAddressDTO;
import com.spike.SecureGate.DTO.userDto.UserChangePasswordDTO;
import com.spike.SecureGate.DTO.userDto.UserSocialUpdateDTO;
import com.spike.SecureGate.DTO.userDto.UserUpdateRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    // CREATE USER
    ResponseEntity<Object> createUser(String username, MultipartFile profilePicture, String data);

    // UPDATE SELF PASSWORD
    ResponseEntity<Object> updateSelfPassword(String userName, UserChangePasswordDTO userChangePasswordDTO);

    // UPDATE SELF DETAILS
    ResponseEntity<Object> updateSelfDetails(Long userId, UserUpdateRequestDTO userUpdateRequestDTO);

    ResponseEntity<Object> updateSelfSocialDetails(Long userId, String userName, UserSocialUpdateDTO userSocialUpdateDTO);

    ResponseEntity<Object> updateSelfAddressDetails(Long userId, String userName, List<UserAddressDTO> addresses);

    ResponseEntity<Object> updateSelfProfilePictureDetails(Long userId, MultipartFile profilePicture);

    ResponseEntity<Object> deleteUser(Long userId);

    ResponseEntity<Object> fetchUsersForEmployeePage(String name, String email, Double salary, int page, int size, String sort);

    ResponseEntity<Object> fetchUsersForContactPage(String name, int pageSize, int pageNo, String sort);
}
