package com.spike.SecureGate.service.externalUserService;

import com.spike.SecureGate.DTO.userDto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    // CREATE USER
    ResponseEntity<Object> createUser(String username, UserCreationRequestDTO data);

    // UPDATE SELF PASSWORD
    ResponseEntity<Object> updateSelfPassword(String userName, UserChangePasswordDTO userChangePasswordDTO);

    // UPDATE SELF DETAILS
    ResponseEntity<Object> updateSelfDetails(Long userId, UserFullUpdateDTO userUpdateRequestDTO);

    ResponseEntity<Object> updateSelfProfilePictureDetails(Long userId, MultipartFile profilePicture);

    ResponseEntity<Object> deleteUser(Long userId);

    ResponseEntity<Object> fetchUsersForEmployeePage(String name, String email, Double salary, int page, int size, String sort);

    ResponseEntity<Object> fetchUsersForContactPage(String name, int pageSize, int pageNo, String sort);

    ResponseEntity<Object> addProfilePicture(MultipartFile profilePicture, String username, long userId);

    ResponseEntity<Object> fetchSelfDetails(long userId);

    ResponseEntity<Object> fetchDepartmentsOfAUser(long userId);

    List<String> getCountriesWithStates();
}
