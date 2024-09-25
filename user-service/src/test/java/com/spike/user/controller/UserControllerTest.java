package com.spike.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.user.dto.*;
import com.spike.user.entity.User;
import com.spike.user.service.userService.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
        // Clean up resources if needed
    }

    @Test
    void createNewUser() {
        UserCreationRequestDTO request = new UserCreationRequestDTO();
        request.setEmployeeCode("E123");
        request.setName("Mahesh");
        request.setEmail("mahesh11@gmail.com");
        request.setRole("MANAGER");
        request.setPrimaryMobileNumber("9879543210");
        // Set other necessary fields...

        User mockUser = new User();
        mockUser.setEmployeeCode("E123");
        // Set necessary fields in mockUser...

        when(userService.createNewUser(any(UserCreationRequestDTO.class))).thenReturn(mockUser);

        ResponseEntity<Object> response = userController.createNewUser(request, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check the response body structure
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("user successfully created", responseBody.get("message"));
        assertNotNull(responseBody.get("data")); // Check if data is present
    }


    @Test
    void addProfilePictureOfAUser() {
        MultipartFile mockFile = mock(MultipartFile.class);
        UserInfoDTO mockUserInfo = new UserInfoDTO();
        // Set necessary fields for mockUserInfo...

        when(userService.addProfilePictureOfAUser(anyLong(), any(MultipartFile.class))).thenReturn(mockUserInfo);

        ResponseEntity<Object> response = userController.addProfilePictureOfAUser(1L, "admin", mockFile);

        // Assert the HTTP status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Extract the body and perform necessary checks
        assertNotNull(response.getBody());

        // Assuming the response body is structured like this:
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        assertEquals("user successfully added profile picture", responseBody.get("message"));
        assertNotNull(responseBody.get("data"));
    }


    @Test
    void updateSelfPassword() {
        UserChangePasswordDTO request = new UserChangePasswordDTO();
        request.setOldPassword("oldPassword123"); // Set old password
        request.setNewPassword("newPassword456"); // Set new password

        when(userService.updateSelfPassword(anyString(), any(UserChangePasswordDTO.class))).thenReturn("Password updated");

        ResponseEntity<Object> response = userController.updateSelfPassword("admin", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assuming the response body is an object with a message field
        assertEquals("user password update successful", ((Map<String, Object>) response.getBody()).get("message"));
    }


    @Test
    void getAllUsers() {
        List<ManagerDropdownDTO> mockUsers = Collections.singletonList(new ManagerDropdownDTO());
        when(userService.getAllUsers()).thenReturn(mockUsers);

        List<ManagerDropdownDTO> response = userController.getAllUsers();

        assertEquals(1, response.size());
    }

    @Test
    void updateUser() {
        UserFullUpdateDTO request = new UserFullUpdateDTO();
        request.setUsername("mahesh07");
        request.setBackupEmail("mahesh07@gmail.com");
        request.setPrimaryMobileNumber("7987654587");
        request.setSecondaryMobileNumber("8976543489");

        User updatedUser = new User();
        updatedUser.setUsername("mahesh07");
        updatedUser.setBackupEmail("mahesh07@gmail.com");
        updatedUser.setPrimaryMobileNumber("7987654587");
        updatedUser.setSecondaryMobileNumber("8976543489");

        when(userService.updateUserFull(anyLong(), any(UserFullUpdateDTO.class))).thenReturn(updatedUser);

        ResponseEntity<Object> response = userController.updateUser(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assuming the response body is structured as follows:
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        assertEquals("User successfully updated.", responseBody.get("message"));
        assertNotNull(responseBody.get("data")); // Ensure that the data is not null
    }


    @Test
    void updateProfilePicture() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        doNothing().when(userService).updateUserProfilePicture(anyLong(), any(MultipartFile.class));

        ResponseEntity<Object> response = userController.updateProfilePicture(1L, mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check the response body structure
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Profile picture updated successfully.", responseBody.get("message"));
        assertNotNull(responseBody.get("data")); // Optionally check if data is present
    }


    @Test
    void deleteUser() {
        doNothing().when(userService).deleteUser(anyLong());

        ResponseEntity<Object> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check the response body structure
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("User successfully deleted.", responseBody.get("message"));
        assertNotNull(responseBody.get("data")); // Optionally check if data is present
    }


//    @Test
//    void getUserContact() {
//        // Prepare mock data
//        List<UserContactsDTO> mockContacts = Collections.singletonList(new UserContactsDTO());
//        when(userService.getUserContacts(anyString(), anyInt(), anyInt(), anyString())).thenReturn(mockContacts);
//
//        // Call the controller method
//        ResponseEntity<Object> response = userController.getUserContact("admin", 5, 0, "updatedAt,desc");
//
//        // Assertions
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        // Check the response body structure
//        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
//        assertEquals("user contacts found successfully", responseBody.get("message"));
//        assertEquals(mockContacts, responseBody.get("data")); // Check if the data matches
//    }


    @Test
    void getUserByUsername() throws IOException {
        UserInfoDTO mockUser = new UserInfoDTO();
        when(userService.getUserByUsername(anyString())).thenReturn(mockUser);

        UserInfoDTO response = userController.getUserByUsername("admin");

        assertNotNull(response);
    }

    @Test
    void getDepartmentsByUserId() {
        List<DepartmentDropdownDTO> mockDepartments = Collections.singletonList(new DepartmentDropdownDTO());
        when(userService.getDepartmentsByUserId(anyLong())).thenReturn(mockDepartments);

        ResponseEntity<Object> response = userController.getDepartmentsByUserId(1L);

        // Assert the HTTP status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Extract the body and perform necessary checks
        assertNotNull(response.getBody());

        // Assuming the response body is structured like this:
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        assertEquals("Departments successfully retrieved.", responseBody.get("message"));
        assertNotNull(responseBody.get("data"));
        assertTrue(((List<?>) responseBody.get("data")).size() > 0);
    }


    @Test
    void getUserById() throws IOException {
        // Create a mock UserProfileDTO and set necessary fields
        UserProfileDTO mockUserProfile = new UserProfileDTO();
        mockUserProfile.setName("John Doe"); // example field
        mockUserProfile.setEmail("john@example.com"); // example field

        // Mock the service method to return the mock user profile
        when(userService.getUserById(anyLong())).thenReturn(mockUserProfile);

        // Call the controller method
        ResponseEntity<Object> response = userController.getUserById(1L);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check the response body structure
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("User information fetched.", responseBody.get("message"));

        // Check if the data contains the expected user profile
        UserProfileDTO data = (UserProfileDTO) responseBody.get("data");
        assertNotNull(data, "The user profile data should not be null.");
        assertEquals(mockUserProfile, data, "Expected the returned user profile to match the mock.");

        // Verify that the service method was called
        verify(userService).getUserById(1L);
    }


    @Test
    void getSpecificUserInfoByUsername() {
        // Create a mock user
        UserDashboardDTO mockUser = new UserDashboardDTO();
        mockUser.setName("John Doe"); // Set required fields

        // Setup the mock service response
        when(userService.getUserFilteredDashboard(anyString(), anyString(), anyDouble(), anyInt(), anyInt(), anyString()))
                .thenReturn(Collections.singletonList(mockUser));

        // Call the controller method
        ResponseEntity<Object> response = userController.getSpecificUserInfoByUsername("admin", "admin@gmail.com", 134563.0, 0, 6, "updatedAt,desc");

        // Validate the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Validate the response body
        Map<String, Object> responseBody = objectMapper.convertValue(response.getBody(), Map.class);
        assertNotNull(responseBody);
        assertEquals("user info dashboard displayed successfully", responseBody.get("message"));

        // Validate the data size
        List<UserDashboardDTO> data = objectMapper.convertValue(responseBody.get("data"), new TypeReference<List<UserDashboardDTO>>() {});
        assertNotNull(data);
        assertEquals(1, data.size());
        assertEquals(mockUser.getName(), data.get(0).getName()); // Compare relevant fields instead of the whole object

        // Verify the service method was called
        verify(userService).getUserFilteredDashboard(anyString(), anyString(), anyDouble(), anyInt(), anyInt(), anyString());
    }


}
