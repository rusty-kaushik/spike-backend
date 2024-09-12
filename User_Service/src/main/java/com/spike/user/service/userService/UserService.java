package com.spike.user.service.userService;

import com.spike.user.dto.*;
import com.spike.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User createNewUser(MultipartFile profilePicture, UserCreationRequestDTO userRequest1);

    List<User> getAllUsers();

    String updateSelfPassword(String username, UserChangePasswordDTO userChangePasswordDTO);

    // Update user details
    User updateUser(Long userId, UserUpdateDTO userRequest1);

    // Update user social URLs
    User updateSocialUrls(Long userId, UserSocialDTO userRequest) ;

    // Update user addresses
    User updateAddresses(Long userId, List<UserAddressDTO> addresses);

    // Update user profile picture
    void updateUserProfilePicture(Long userId, MultipartFile profilePicture) throws IOException;

    // Delete user by ID
    void deleteUser(Long userId);

    //fetch particular user contacts if name is provided otherwise all the user contacts
    List<UserContactsDTO> getUserContacts(String name, int pageno, int pagesize, String sort);



    //fetch user dashboard data with filtration if provided otherwise show all the user details
    List<UserDashboardDTO> getUserFilteredDashboard(String name, String email, Double salary, int page, int size, String sort);


    UserInfoDTO getUserByUsername(String username) throws IOException;
}
