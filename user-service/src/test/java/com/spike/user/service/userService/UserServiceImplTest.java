package com.spike.user.service.userService;

import com.spike.user.customMapper.UserMapper;
import com.spike.user.dto.*;
import com.spike.user.entity.*;
import com.spike.user.exceptions.*;
import com.spike.user.helper.UserHelper;
import com.spike.user.repository.UserProfilePictureRepository;
import com.spike.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserProfilePictureRepository userProfilePictureRepository;

    @Mock
    private UserHelper userHelper;

    private UserMapper userMapper;

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test case execution completed.");
    }

    @Test
    void shouldCreateNewUserSuccessfully() {
        // Arrange
        UserCreationRequestDTO userRequest = new UserCreationRequestDTO();
        userRequest.setName("userName");
        userRequest.setEmail("userEmail");
        userRequest.setEmployeeCode("76587688");
        userRequest.setPrimaryMobileNumber("1234567890");

        UserAddressDTO addressDTO = new UserAddressDTO();
        userRequest.setAddresses(Collections.singletonList(addressDTO));

        User user = new User();
        user.setName("userName");  // Ensure properties are set
        user.setEmail("userEmail");
        user.setEmployeeCode("76587688");
        user.setPrimaryMobileNumber("1234567890");

        when(userHelper.dtoToEntityForUserMaster(any())).thenReturn(user);
        when(userHelper.dtoToEntityForUserAddress(any())).thenReturn(new UserAddress());
        when(userHelper.dtoToEntityForUserSocials(any())).thenReturn(new UserSocials());
        when(userRepository.save(any())).thenReturn(user);

        // Act
        User createdUser = userService.createNewUser(userRequest);

        // Assert
        assertNotNull(createdUser);
        assertEquals("userName", createdUser.getName());
        assertEquals("userEmail", createdUser.getEmail());
        assertEquals("76587688", createdUser.getEmployeeCode());
        assertEquals("1234567890", createdUser.getPrimaryMobileNumber());
        verify(userRepository).save(user);
        verify(userHelper).dtoToEntityForUserMaster(userRequest);
        verify(userHelper, times(1)).dtoToEntityForUserSocials(userRequest);
        verify(userHelper, times(1)).dtoToEntityForUserAddress(any());
    }


    @Test
    void shouldThrowDepartmentNotFoundException() {
        // Arrange
        UserCreationRequestDTO userRequest = new UserCreationRequestDTO();
        when(userHelper.dtoToEntityForUserMaster(any())).thenThrow(new DepartmentNotFoundException("DEPARTMENT_NOT_FOUND", "Department not found"));

        // Act & Assert
        DepartmentNotFoundException exception = assertThrows(DepartmentNotFoundException.class, () -> userService.createNewUser(userRequest));
        assertEquals("Department not found", exception.getMessage());
        assertEquals("DEPARTMENT_NOT_FOUND", exception.getError());
        assertTrue(exception.toString().contains("DepartmentNotFoundException"));
    }

    @Test
    void shouldThrowRoleNotFoundException() {
        // Arrange
        UserCreationRequestDTO userRequest = new UserCreationRequestDTO();
        when(userHelper.dtoToEntityForUserMaster(any())).thenThrow(new RoleNotFoundException("ROLE_NOT_FOUND", "Role not found"));

        // Act & Assert
        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, () -> userService.createNewUser(userRequest));
        assertEquals("Role not found", exception.getMessage());
        assertEquals("ROLE_NOT_FOUND", exception.getError());
        assertTrue(exception.toString().contains("RoleNotFoundException"));
    }

    @Test
    void shouldThrowDtoToEntityConversionException() {
        // Arrange
        UserCreationRequestDTO userRequest = new UserCreationRequestDTO();
        when(userHelper.dtoToEntityForUserMaster(any())).thenThrow(new DtoToEntityConversionException("DTO_TO_ENTITY_CONVERSION_ERROR", "Conversion failed"));

        // Act & Assert
        DtoToEntityConversionException exception = assertThrows(DtoToEntityConversionException.class, () -> userService.createNewUser(userRequest));
        assertEquals("Conversion failed", exception.getMessage());
        assertEquals("DTO_TO_ENTITY_CONVERSION_ERROR", exception.getError());
        assertTrue(exception.toString().contains("DtoToEntityConversionException"));
    }

    @Test
    void getAllUsers_success() {
        // Arrange
        List<ManagerDropdownDTO> mockUsers = new ArrayList<>();
        mockUsers.add(new ManagerDropdownDTO(1L, "Manager One")); // Use Long for ID
        mockUsers.add(new ManagerDropdownDTO(2L, "Manager Two"));

        when(userRepository.findAllManagers()).thenReturn(mockUsers);

        // Act
        List<ManagerDropdownDTO> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(Long.valueOf(1), result.get(0).getId()); // Compare as Long
        assertEquals("Manager One", result.get(0).getName());
        assertEquals(Long.valueOf(2), result.get(1).getId()); // Compare as Long
        assertEquals("Manager Two", result.get(1).getName());
        verify(userRepository).findAllManagers();
    }

//    @Test
//    void updateSelfPassword_success() {
//        // Arrange
//        User existingUser = mock(User.class); // Mock the User class
//
//        // Define the plain and encoded passwords
//        String oldPassword = "plainOldPassword";
//        String encodedOldPassword = passwordEncoder.encode(oldPassword); // Encode the old password
//        String newPassword = "newPassword";
//        String encodedNewPassword = passwordEncoder.encode(newPassword); // Encode the new password
//
//        // Mock the user's getPassword() method to return the encoded password
//        when(existingUser.getPassword()).thenReturn(encodedOldPassword);
//        when(userRepository.findByUsername("testUser")).thenReturn(existingUser); // Return the mocked user
//
//        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO();
//        userChangePasswordDTO.setOldPassword(oldPassword); // Old password in plain text
//        userChangePasswordDTO.setNewPassword(newPassword); // New password in plain text
//
//        // Mock the passwordEncoder to match the old password
//        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(true);
//
//        // Mock the encoding of the new password
//        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);
//
//        // Act
//        String result = userService.updateSelfPassword("testUser", userChangePasswordDTO);
//
//        // Assert
//        assertEquals("Successfully updated password for testUser", result);
//        verify(userRepository).save(existingUser);
//        verify(existingUser).setPassword(encodedNewPassword); // Verify setPassword is called with the new password
//    }





    @Test
    void updateSelfPassword_userNotFound() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(null); // User not found

        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO();
        userChangePasswordDTO.setOldPassword("plainOldPassword");
        userChangePasswordDTO.setNewPassword("newPassword");

        // Act & Assert
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateSelfPassword("testUser", userChangePasswordDTO);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updateSelfPassword_oldPasswordMismatch() {
        // Arrange
        User existingUser = mock(User.class);
        when(existingUser.getPassword()).thenReturn("encodedOldPassword");
        when(userRepository.findByUsername("testUser")).thenReturn(existingUser);

        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO();
        userChangePasswordDTO.setOldPassword("wrongOldPassword");
        userChangePasswordDTO.setNewPassword("newPassword");

        // Mock the passwordEncoder to return false for old password check
        when(passwordEncoder.matches("wrongOldPassword", "encodedOldPassword")).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(PasswordNotMatchException.class, () -> {
            userService.updateSelfPassword("testUser", userChangePasswordDTO);
        });

        assertEquals("Old password does not match.", exception.getMessage());
    }



    @Test
    void updateUserFull() {
        Long userId = 1L;
        UserFullUpdateDTO userFullUpdateDTO = new UserFullUpdateDTO();
        User existingUser = new User();
        existingUser.setId(userId);

        when(userHelper.updateUserFromDTO(eq(userId), any(UserFullUpdateDTO.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUserFull(userId, userFullUpdateDTO);

        verify(userHelper).updateUserFromDTO(eq(userId), any(UserFullUpdateDTO.class));
        assertEquals(existingUser, updatedUser);
    }

    @Test
    void updateUserProfilePicture() throws IOException {
        Long userId = 1L;
        MockMultipartFile profilePicture = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[1]);
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setProfilePicture(new UserProfilePicture());

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userHelper.updateUserProfilePicture(any(MultipartFile.class), eq(existingUser))).thenReturn(new UserProfilePicture());

        userService.updateUserProfilePicture(userId, profilePicture);

        verify(userRepository).findById(userId);
        verify(userProfilePictureRepository).save(any(UserProfilePicture.class)); // Ensure this is verified
        verify(userRepository).save(existingUser);
    }


    @Test
    void deleteUser() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(userId);

        verify(userRepository).delete(existingUser);
    }

//    @Test
//    public void testGetUserContacts() {
//        // Given
//        User user1 = new User(); // initialize with appropriate values
//        User user2 = new User(); // initialize with appropriate values
//        List<User> userList = Arrays.asList(user1, user2);
//        Page<User> userPage = new PageImpl<>(userList); // Wrap in a PageImpl
//
//        // Mock the repository method to return the Page<User>
//        when(userRepository.findAll((Specification<User>) any(), (Pageable) any())).thenReturn(userPage);
//
//        // When
//        List<UserContactsDTO> result = userService.getUserContacts("testName", 0, 10, "name,asc");
//
//        // Then
//        assertNotNull(result);
//        assertEquals(2, result.size()); // Adjust based on expected outcome
//        // Add more assertions as needed
//    }


    @Test
    public void testUserToUserContactsDto() throws Exception {
        // Create a User instance with sample data for this test
        User user = new User();
        user.setId(1L);
        user.setPrimaryMobileNumber("1234567890");


        UserAddress address1 = new UserAddress();
        address1.setLine1("123 Main St");
        user.setAddresses(Arrays.asList(address1));

        UserSocials socials = new UserSocials();
        socials.setInstagramUrl("http://instagram.com/user");
        socials.setFacebookUrl("http://facebook.com/user");
        socials.setLinkedinUrl("http://linkedin.com/user");
        user.setUserSocials(socials);

        UserProfilePicture profilePicture = new UserProfilePicture();
        profilePicture.setFilePath("path/to/profile/picture.jpg");
        user.setProfilePicture(profilePicture);

        // Call the method under test using the mapper
        UserContactsDTO resultDto = userMapper.entityToDtoContact(user);

        // Verify the results
        assertNotNull(resultDto);
        assertEquals(user.getId(), resultDto.getId());
        assertEquals(user.getPrimaryMobileNumber(), resultDto.getPrimaryMobile());
        assertEquals(socials.getInstagramUrl(), resultDto.getInstagramUrl());
        assertEquals(socials.getFacebookUrl(), resultDto.getFacebookUrl());
        assertEquals(socials.getLinkedinUrl(), resultDto.getLinkedinUrl());
        assertEquals(1, resultDto.getAddresses().size()); // Check the count of addresses
    }

    @Test
    public void testUserToUserContactsDto_WithIOException() {
        // Create a User instance for this test
        User user = new User();
        user.setId(1L);
        user.setPrimaryMobileNumber("1234567890");
        user.setProfilePicture(null); // No profile picture

        // Call the method under test using the mapper
        UserContactsDTO resultDto = userMapper.entityToDtoContact(user);

        // Verify the results
        assertNotNull(resultDto);
        assertNull(resultDto.getProfilePicture()); // Profile picture should be null
    }

    @Test
    void getUserByUsername() throws IOException {
        // Arrange
        String username = "testUser";
        User mockUser = new User(); // Assuming User has a default constructor
        mockUser.setUsername(username);

        UserInfoDTO expectedUserInfoDTO = new UserInfoDTO(); // Create a DTO and set expected values
        expectedUserInfoDTO.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(mockUser);
        when(userHelper.entityToUserInfoDto(mockUser)).thenReturn(expectedUserInfoDTO);
        when(userHelper.fetchUserProfilePicture(mockUser)).thenReturn("base64Image");

        // Act
        UserInfoDTO actualUserInfoDTO = userService.getUserByUsername(username);

        // Assert
        assertNotNull(actualUserInfoDTO);
        assertEquals(expectedUserInfoDTO.getUsername(), actualUserInfoDTO.getUsername());
        assertEquals("base64Image", actualUserInfoDTO.getPicture());
    }

    @Test
    void getDepartmentsByUserId() {
        // Arrange
        long userId = 1L;
        User mockUser = new User();
        Department department = new Department();
        department.setId(1L); // Set a valid ID
        department.setName("HR"); // Set the name explicitly
        Set<Department> departments = new HashSet<>();
        departments.add(department);
        mockUser.setDepartments(departments);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        List<DepartmentDropdownDTO> departmentDTOs = userService.getDepartmentsByUserId(userId);

        // Assert
        assertNotNull(departmentDTOs, "Department DTOs should not be null");
        assertEquals(1, departmentDTOs.size(), "Should return one department DTO");
        assertEquals("HR", departmentDTOs.get(0).getName(), "Department name should be 'HR'");
        assertEquals(1L, departmentDTOs.get(0).getId(), "Department ID should match");
    }


}