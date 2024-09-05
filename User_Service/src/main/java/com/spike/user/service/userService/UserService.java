package com.spike.user.service.userService;

import com.spike.user.dto.UserAddressDTO;
import com.spike.user.dto.UserChangePasswordDTO;
import com.spike.user.dto.UserCreationRequestDTO;
import com.spike.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User createNewUser(MultipartFile profilePicture, UserCreationRequestDTO userRequest1);

    List<User> getAllUsers();

    String updateSelfPassword(String username, UserChangePasswordDTO userChangePasswordDTO);

    // Update user details
    User updateUser(Long userId, UserCreationRequestDTO userRequest1) throws IOException;

    // Update user social URLs
    User updateSocialUrls(Long userId, UserCreationRequestDTO userRequest) throws IOException;

    // Update user addresses
    User updateAddresses(Long userId, List<UserAddressDTO> addresses) throws IOException;

    // Update user profile picture
    void updateUserProfilePicture(Long userId, MultipartFile profilePicture) throws IOException;

    // Delete user by ID
    void deleteUser(Long userId);
}
