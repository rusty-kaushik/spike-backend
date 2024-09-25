package com.spike.user.service.userService;

import com.spike.user.dto.*;
import com.spike.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User createNewUser(UserCreationRequestDTO userRequest1);

    List<ManagerDropdownDTO> getAllUsers();

    String updateSelfPassword(String username, UserChangePasswordDTO userChangePasswordDTO);

    // User self update
    User updateUserFull(Long userId, UserFullUpdateDTO userRequest);

    // Update user profile picture
    void updateUserProfilePicture(Long userId, MultipartFile profilePicture) throws IOException;

    // Delete user by ID
    void deleteUser(Long userId);

    //fetch particular user contacts if name is provided otherwise all the user contacts
    List<UserContactsDTO> getUserContacts(Long userId ,String name, int pageno, int pagesize, String sort);

    //fetch user dashboard data with filtration if provided otherwise show all the user details
    List<UserDashboardDTO> getUserFilteredDashboard(String name, String email, Double salary, int page, int size, String sort);

    // Fetch user by username for login api
    UserInfoDTO getUserByUsername(String username) throws IOException;

    // Fetch User in how many department is currently
    List<DepartmentDropdownDTO> getDepartmentsByUserId(long userId);

    UserInfoDTO addProfilePictureOfAUser(long userId, MultipartFile profilePicture);

    UserProfileDTO getUserById(long userId) throws IOException;

    ContactsDto createContacts(ContactsDto contactDto, Long userId) ;
    
    //fetch All-Contacts
    List<ContactsDto> getAllContacts();
    
    //Delete contact by user id
    public void deleteContacts(Long id);
}
