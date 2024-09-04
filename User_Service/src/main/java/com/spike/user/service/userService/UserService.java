package com.spike.user.service.userService;

import com.spike.user.dto.UserChangePasswordDTO;
import com.spike.user.dto.UserCreationRequestDTO;
import com.spike.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User createNewUser(MultipartFile profilePicture, UserCreationRequestDTO userRequest1);

    List<User> getAllUsers();

    String updateSelfPassword(String username, UserChangePasswordDTO userChangePasswordDTO);
}
