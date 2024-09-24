package com.spike.user.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.user.customMapper.UserMapper;
import com.spike.user.dto.*;
import com.spike.user.entity.*;
import com.spike.user.exceptions.DepartmentNotFoundException;
import com.spike.user.exceptions.DtoToEntityConversionException;
import com.spike.user.exceptions.RoleNotFoundException;
import com.spike.user.exceptions.UserNotFoundException;
import com.spike.user.repository.DepartmentRepository;
import com.spike.user.repository.RoleRepository;
import com.spike.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserHelper.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UserMapper userMapper;

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User dtoToEntityForUserMaster(UserCreationRequestDTO userRequest){
        try{
            logger.info("Converting UserCreationRequestDTO to User entity");
            User user = userMapper.dtoToEntity(userRequest);
            user.setPassword(passwordEncoder.encode("in2it")); // Set the password manually
            // Handle complex fields or relations
            Set<Department> departments = userRequest.getDepartment().stream()
                    .map(deptName -> departmentRepository.findByName(deptName)
                            .orElseThrow(() -> new DepartmentNotFoundException("ValidationError","Department not found: " + deptName)))
                    .collect(Collectors.toSet());
            user.setDepartments(departments);
            logger.debug("Departments set: {}", departments);
            Role role = roleRepository.findByName(userRequest.getRole())
                    .orElseThrow(() -> new RoleNotFoundException("ValidationError","Role not found: " + userRequest.getRole()));
            user.setRole(role);
            logger.debug("Role set: {}", role);
            return user;
        } catch (DepartmentNotFoundException |  RoleNotFoundException e){
            logger.error("Department or Role not found", e);
            throw e;
        } catch (Exception e){
            logger.error("Could not convert UserCreationRequestDTO to User entity", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert user DTO to user entity");
        }

    }

    public UserAddress dtoToEntityForUserAddress(UserAddressDTO userAddressDTO){
        try{
            return userMapper.dtoToEntityAddress(userAddressDTO);
        } catch (Exception e) {
            logger.error("Could not convert UserAddressDTO to UserAddress entity", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert user address DTO to user address entity");
        }
    }

    public UserSocials dtoToEntityForUserSocials(UserCreationRequestDTO userRequest){
        try{
            return userMapper.dtoToEntitySocials(userRequest);
        } catch (Exception e) {
            logger.error("Could not convert UserCreationRequestDTO to UserSocials entity", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert user social url DTO to user social url entity");
        }
    }

    public UserProfilePicture dtoToEntityForUserPicture(MultipartFile profilePicture, User user) {
        try {
            logger.info("Processing profile picture: {}", profilePicture.getOriginalFilename());

            // Check if the profile picture is not null and not empty
            if (profilePicture != null && !profilePicture.isEmpty()) {

                // Get original file details
                String originalFileName = profilePicture.getOriginalFilename();
                String fileType = profilePicture.getContentType();
                Long fileSize = profilePicture.getSize();

                // Get file extension and create a new file name using the employee code
                String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.')) : "";
                String newFileName = user.getEmployeeCode() + "_profile_picture" + fileExtension;

                // Create the file path in the upload directory
                String filePath = uploadDir + File.separator + newFileName;
                Path path = Paths.get(filePath);

                // Save the file to the destination folder
                Files.copy(profilePicture.getInputStream(), path);
                logger.debug("Profile picture saved to path: {}", filePath);

                // Create and populate the UserProfilePicture entity
                UserProfilePicture userProfilePicture = new UserProfilePicture();
                userProfilePicture.setOriginalFileName(originalFileName);
                userProfilePicture.setFileName(newFileName);
                userProfilePicture.setFilePath(filePath);
                userProfilePicture.setFileType(fileType);
                userProfilePicture.setFileSize(fileSize);

                // Return the populated UserProfilePicture entity
                return userProfilePicture;
            } else {
                logger.warn("No profile picture provided");
                return null;
            }
        } catch (Exception e) {
            logger.error("Could not convert user profile picture DTO to UserProfilePicture entity", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert user profile picture DTO to user profile picture entity"+ e);
        }
    }

    //this specification will filter record based on user name
    public Specification<Contacts> hasName(String name) {
        return (root, query, cb) -> {
            if (name != null) {
                return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
            }
            return null;
        };
    }

//this specification will filter user contacts record based on filter
public Specification<User> filterByName(String name) {
    return (root, query, cb) -> {
        if (name != null) {
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        }
        return null;
    };
}
    //this specification will filter record based on user email
    public Specification<User> filterByEmail(String email) {
        return (root, query, cb) -> {
            if (email != null) {
                return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
            }
            return null;
        };
    }

    public Specification<Contacts> filterByUserId(Long userId) {
        return (root, query, cb) -> userId != null ? cb.equal(root.get("userId"), userId) : null;
    }

    //this specification will filter record based on user salary
    public Specification<User> filterBySalary(Double salary) {
        return (root, query, cb) -> salary != null ? cb.equal(root.get("salary"), salary) : null;
    }


    public UserContactsDTO entityToUserContactsDto(User user){
        try{
            return userMapper.entityToDtoContact(user);
        } catch (Exception e) {
            logger.error("Could not convert User entity to UserContactsDTO", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert user entity to user contacts DTO");
        }
    }

    public UserAddressDTO entityToUserAddressDto(UserAddress address){
        try{
            return userMapper.entityToDtoAddress(address);
        } catch (Exception e) {
            logger.error("Could not convert UserAddress entity to UserAddressDTO", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert user address entity to user address DTO");
        }
    }

    public UserDashboardDTO entityToUserDashboardDto(User user){
        try{
            return userMapper.entityToDtoDashboard(user);
        } catch (Exception e) {
            logger.error("Could not convert User entity to UserDashboardDTO", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert user entity to user dashboard DTO");
        }
    }

    public UserInfoDTO entityToUserInfoDto(User byUsername) {
        UserInfoDTO info = new UserInfoDTO();
        info.setId(byUsername.getId());
        info.setUsername(byUsername.getUsername());
        info.setEmail(byUsername.getEmail());
        info.setDesignation(byUsername.getDesignation());
        info.setRole(byUsername.getRole().getName());
        return info;
    }

    public String fetchUserProfilePicture(User user) throws IOException {
        if (user.getProfilePicture() == null) {
            return null;  // No profile picture set for the user
        }

        String filePath = uploadDirectory + File.separator + user.getProfilePicture().getFileName();
        File imageFile = new File(filePath);

        // Initialize the Base64 image string as null
        String base64Image = null;

        // Read and encode image file if it exists
        if (imageFile.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(imageFile)) {
                byte[] imageBytes = new byte[(int) imageFile.length()];
                fileInputStream.read(imageBytes);

                // Convert to Base64
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                // Handle exception (log it or rethrow it)
                throw new RuntimeException("Error reading or encoding image file", e);
            }
        } else {
            // Handle the case where the image file does not exist
            base64Image = null;  // Or use a placeholder image
        }

        return base64Image;
    }

    public UserProfileDTO entityToUserProfileDto(User user, String base64Image) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setDesignation(user.getDesignation());
        dto.setEmployeeCode(user.getEmployeeCode());
        dto.setRole(user.getRole().getName());
        dto.setPrimaryMobileNumber(user.getPrimaryMobileNumber());
        dto.setJoiningDate(String.valueOf(user.getJoiningDate()));
        dto.setDepartment(user.getDepartments());
        dto.setAddresses(user.getAddresses());
        dto.setProfilePicture(base64Image);
        return dto;
    }

    // Update user helper function
    public User updateUserFromDTO(Long userId, UserFullUpdateDTO userFullUpdateDTO) {
        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("ValidationError","User not found with id: " + userId));

            // Use the mapper to update fields
            userMapper.updateUserFromDTO(userFullUpdateDTO, existingUser);

            // Update social fields
            userMapper.mapSocials(userFullUpdateDTO, existingUser);


            // Handle complex fields like departments if needed
            if (userFullUpdateDTO.getAddresses() != null) {
                // You can handle addresses here if necessary
                existingUser.getAddresses().clear(); // Clear existing addresses
                for (UserAddressDTO addressDTO : userFullUpdateDTO.getAddresses()) {
                    UserAddress address = userMapper.dtoToEntityAddress(addressDTO);
                    address.setUser(existingUser);
                    existingUser.getAddresses().add(address);
                }
            }

            // Save the updated user
            return userRepository.save(existingUser);
        } catch (Exception e) {
            logger.error("Could not update user with id: {}", userId, e);
            throw new DtoToEntityConversionException("ConversionError","Could not update user"+ e);
        }
    }


    public UserProfilePicture updateUserProfilePicture(MultipartFile profilePicture, User user) {
        try {
            logger.info("Updating profile picture for user: {}", user.getEmployeeCode());

            if (profilePicture != null && !profilePicture.isEmpty()) {
                String originalFileName = profilePicture.getOriginalFilename();
                String fileType = profilePicture.getContentType();
                Long fileSize = profilePicture.getSize();

                // Create a new file name based on employee code
                String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.')) : "";
                String newFileName = user.getEmployeeCode() + "_profile_picture" + fileExtension;

                // Save the new file in the same directory
                String filePath = uploadDir + File.separator + newFileName;
                Path path = Paths.get(filePath);
                Files.copy(profilePicture.getInputStream(), path);

                // Update the UserProfilePicture entity
                UserProfilePicture userProfilePicture = user.getProfilePicture();
                if (userProfilePicture == null) {
                    userProfilePicture = new UserProfilePicture();
                    userProfilePicture.setUser(user);
                }

                userProfilePicture.setOriginalFileName(originalFileName);
                userProfilePicture.setFileName(newFileName);
                userProfilePicture.setFilePath(filePath);
                userProfilePicture.setFileType(fileType);
                userProfilePicture.setFileSize(fileSize);

                return userProfilePicture;
            } else {
                logger.warn("No profile picture provided");
                return null;
            }
        } catch (Exception e) {
            logger.error("Could not update profile picture for user: {}", user.getEmployeeCode(), e);
            throw new DtoToEntityConversionException("ConversionError","Could not update user profile picture"+ e);
        }
    }
    public ContactsDto entityToContactsDto(Contacts contacts){
        try{
            return userMapper.entityToContactsDto(contacts);
        } catch (Exception e) {
            logger.error("Could not convert contacts entity to ContactsDTO", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert contacts entity to contacts DTO"+e);
        }
    }

    public Contacts contactsDtoToEntity(ContactsDto contactsDto){
        try{
            return userMapper.contactDtoToEntity(contactsDto);
        } catch (Exception e) {
            logger.error("Could not convert contacts dto to Contacts", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert contacts dto to Contacts");
        }
    }

    public UserAddressDTO entityToAddressDto(ContactAddress address) {
        try{
            return userMapper.contactToAddressDto(address);
        } catch (Exception e) {
            logger.error("Could not convert contacts dto to Contacts", e);
            throw new DtoToEntityConversionException("ConversionError","Could not convert contacts dto to Contacts");
        }
    }

    public UserContactsDTO entityToPersonalContactsDto(Contacts contacts) {
        try {
            return userMapper.entityToPersonalContactsDto(contacts);
        } catch (Exception e) {
            logger.error("Could not convert contacts to Contacts dto ", e);
            throw new DtoToEntityConversionException("ConversionError", "Could not convert contacts  to Contacts dto ");
        }
    }


}