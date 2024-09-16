package com.spike.user.service.userService;

import com.spike.user.dto.*;
import com.spike.user.entity.*;
import com.spike.user.exceptions.*;
import com.spike.user.helper.UserHelper;
import com.spike.user.repository.DepartmentRepository;
import com.spike.user.repository.UserProfilePictureRepository;
import com.spike.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfilePictureRepository userProfilePictureRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createNewUser(UserCreationRequestDTO userRequest) {
        logger.info("Starting user creation process");
        try {
            // SETS USER
            User user = userHelper.dtoToEntityForUserMaster(userRequest);
            // SETS ADDRESSES
            for (UserAddressDTO addressDTO : userRequest.getAddresses()) {
                UserAddress userAddress = userHelper.dtoToEntityForUserAddress(addressDTO);
                user.addAddress(userAddress);
            }
            //SETS SOCIAL URL
            user.addSocial(userHelper.dtoToEntityForUserSocials(userRequest));

            User savedUser = userRepository.save(user);
            logger.info("User saved successfully: {}", savedUser);
            return savedUser;
        } catch (DepartmentNotFoundException | RoleNotFoundException | DtoToEntityConversionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error creating user", e);
            throw new UnexpectedException("Error creating user", e.getCause());
        }

    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }
    }

    @Override
    public String updateSelfPassword(String username, UserChangePasswordDTO userChangePasswordDTO) {
        logger.info("Attempting to update password for user: {}", username);
        try {
            User existingUser = userRepository.findByUsername(username);
            if (existingUser == null) {
                logger.warn("User not found: {}", username);
                throw new UserNotFoundException("User not found");
            }
            if (!passwordEncoder.matches(userChangePasswordDTO.getOldPassword(), existingUser.getPassword())) {
                logger.warn("Old password does not match for user: {}", username);
                throw new PasswordNotMatchException("Old password does not match");
            }
            existingUser.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
            userRepository.save(existingUser);
            logger.info("Successfully updated password for user: {}", username);
            return "Successfully updated password for " + username;
        } catch (UserNotFoundException | PasswordNotMatchException e) {
            logger.error("Error updating password for user: {}", username, e);
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating password for user: {}", username, e);
            throw new UnexpectedException("An unexpected error occurred while updating the password", e);
        }
    }

    // Update User Method

    @Override
    @Transactional
    public User updateUserFull(Long userId, UserFullUpdateDTO userFullUpdateDTO) {
        logger.info("Starting update process for user ID: {}", userId);
        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> {
                        logger.error("User not found with id: {}", userId);
                        return new UserNotFoundException("User not found with id: " + userId);
                    });

            // update user fields
            setUserFields(existingUser, userFullUpdateDTO);
            logger.info("User fields updated for user ID: {}", userId);

            // update user socials
            setSocialUrls(existingUser, userFullUpdateDTO);
            logger.info("User socials updated for user ID: {}", userId);

            // update user addresses
            setUserAddresses(existingUser,userFullUpdateDTO.getAddresses());
            logger.info("User addresses updated for user ID: {}", userId);

            return userRepository.save(existingUser);
        } catch (UserNotFoundException e) {
            logger.error("Error updating user - user not found with id: {}", userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error updating user with id: {}", userId, e);
            throw new RuntimeException("Unexpected error updating user", e);
        }
    }


    // it will set the fields to update the user - logic
    private void setUserFields(User user, UserFullUpdateDTO userFullUpdateDTO) {

        user.setBackupEmail(userFullUpdateDTO.getBackupEmail());
        user.setPrimaryMobileNumber(userFullUpdateDTO.getPrimaryMobileNumber());
        user.setSecondaryMobileNumber(userFullUpdateDTO.getSecondaryMobileNumber());
        if (userFullUpdateDTO.getUsername() == null || userFullUpdateDTO.getUsername().isEmpty()) {
            throw new UserNotFoundException("Username cannot be null or empty");
        }
        user.setUsername(userFullUpdateDTO.getUsername());
    }


    // it will take fields that we need to update - logic
    private void setSocialUrls(User user, UserFullUpdateDTO userFullUpdateDTO) {
        UserSocials userSocials = user.getUserSocials();

        if (userSocials == null) {
            userSocials = new UserSocials();
            userSocials.setUser(user);
            user.setUserSocials(userSocials);
        }

        userSocials.setLinkedinUrl(userFullUpdateDTO.getLinkedinUrl());
        userSocials.setFacebookUrl(userFullUpdateDTO.getFacebookUrl());
        userSocials.setInstagramUrl(userFullUpdateDTO.getInstagramUrl());
    }


    // it will take fields that we need to update - logic
    private void setUserAddresses(User existingUser, List<UserAddressDTO> newAddresses) {
        // Clear existing addresses
        existingUser.getAddresses().clear();

        // Add new addresses
        for (UserAddressDTO addressDTO : newAddresses) {
            UserAddress address = new UserAddress();
            address.setUser(existingUser); // Set the user reference
            address.setLine1(addressDTO.getLine1());
            address.setLine2(addressDTO.getLine2());
            address.setState(addressDTO.getState());
            address.setDistrict(addressDTO.getDistrict());
            address.setZip(addressDTO.getZip());
            address.setCity(addressDTO.getCity());
            address.setNearestLandmark(addressDTO.getNearestLandmark());
            address.setCountry(addressDTO.getCountry());
            address.setType(addressDTO.getType());

            existingUser.getAddresses().add(address);
        }
    }

    // User Profile Picture update
    @Override
    @Transactional
    public void updateUserProfilePicture(Long userId, MultipartFile profilePicture) throws IOException {
        logger.info("Starting profile picture update for user ID: {}", userId);
        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> {
                        logger.error("User not found with id: {}", userId);
                        return new UserNotFoundException("User not found with id: " + userId);
                    });

            UserProfilePicture userProfilePicture = existingUser.getProfilePicture();
            String currentProfilePictureFilename = userProfilePicture != null ? userProfilePicture.getFileName() : null;

            // Delete old profile picture
            if (currentProfilePictureFilename != null) {
                Path oldFilePath = Paths.get(uploadDirectory + currentProfilePictureFilename);
                try {
                    Files.deleteIfExists(oldFilePath);
                    logger.info("Deleted old profile picture for user ID: {}", userId);
                } catch (IOException e) {
                    logger.error("Failed to delete old profile picture for user ID: {}", userId, e);
                    throw new IOException("Failed to delete old profile picture", e);
                }
            }

            // Save new profile picture
            String newProfilePictureFilename = profilePicture.getOriginalFilename();
            Path newFilePath = Paths.get(uploadDirectory + newProfilePictureFilename);
            try {
                Files.write(newFilePath, profilePicture.getBytes());
                logger.info("Saved new profile picture for user ID: {}", userId);
            } catch (IOException e) {
                logger.error("Failed to save new profile picture for user ID: {}", userId, e);
                throw new IOException("Failed to save new profile picture", e);
            }

            // Update UserProfilePicture entity
            if (userProfilePicture == null) {
                userProfilePicture = new UserProfilePicture();
                userProfilePicture.setUser(existingUser);
            }

            userProfilePicture.setOriginalFileName(profilePicture.getOriginalFilename());
            userProfilePicture.setFileName(newProfilePictureFilename);
            userProfilePicture.setFilePath(newFilePath.toString());
            userProfilePicture.setFileType(profilePicture.getContentType());
            userProfilePicture.setFileSize(profilePicture.getSize());

            userProfilePictureRepository.save(userProfilePicture);
            existingUser.setProfilePicture(userProfilePicture);

            userRepository.save(existingUser);
        } catch (UserNotFoundException e) {
            logger.error("User not found with id: {}", userId, e);
            throw e;
        } catch (IOException e) {
            logger.error("File operation error for profile picture update of user ID: {}", userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error updating profile picture for user with id: {}", userId, e);
            throw new RuntimeException("Unexpected error updating profile picture", e);
        }
    }


    @Override
    public void deleteUser(Long userId) {
        logger.info("Starting user deletion for user ID: {}", userId);

        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> {
                        logger.error("User not found with id: {}", userId);
                        return new UserNotFoundException("User not found with id: " + userId);
                    });

            userRepository.delete(existingUser);
            logger.info("User deleted successfully with ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", userId, e);
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public List<UserContactsDTO> getUserContacts(String name, int pageno, int pagesize, String sort) {
        logger.info("starts fetching user contacts");
        try {
            Specification<User> specs = Specification.where(userHelper.filterByName(name));
            String[] sortParams = sort.split(",");
            Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
            PageRequest pageRequest = PageRequest.of(pageno, pagesize, Sort.by(direction, sortParams[0]));
            Page<User> user = userRepository.findAll(specs, pageRequest);
            if (user.isEmpty()) {
                throw new UserNotFoundException("list is empty,no user found with the given name: " + name);
            } else {
                return user.stream().map(this::userToUserContacsDto).collect(Collectors.toList());
            }
        } catch (UserNotFoundException e) {
            logger.error("user doesn't exist");
            throw e;
        } catch (Exception ex) {
            logger.error("Unexpected error Occur while fetching user contacts", ex);
            throw new RuntimeException("Unexpected error while fetching user contacts");
        }

    }


    private UserContactsDTO userToUserContacsDto(User user) {

        UserContactsDTO userContactsDto = userHelper.entityToUserContactsDto(user);
        // Filter and set primary address

        List<UserAddressDTO> primaryAddress = user.getAddresses().stream()
                .filter(address -> "PRIMARY".equals(address.getType()))
                .map(address -> userHelper.entityToUserAddressDto(address))
                .collect(Collectors.toList());
        userContactsDto.setPrimaryMobile(user.getPrimaryMobileNumber());
        userContactsDto.setPrimaryAddress(primaryAddress);
        userContactsDto.setInstagramUrl(user.getUserSocials() != null ? user.getUserSocials().getInstagramUrl() : null);
        userContactsDto.setFacebookUrl(user.getUserSocials() != null ? user.getUserSocials().getFacebookUrl() : null);
        userContactsDto.setLinkedinUrl(user.getUserSocials() != null ? user.getUserSocials().getLinkedinUrl() : null);

        //convert profile picture into base64
        UserProfilePicture profilePicture = user.getProfilePicture();
        if (profilePicture != null) {
            try {
                File file = new File(profilePicture.getFilePath());
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = fileInputStream.readAllBytes();
                String picture = Base64.getEncoder().encodeToString(bytes);
                userContactsDto.setProfilePicture(picture);

            } catch (IOException e) {
                userContactsDto.setProfilePicture(null);
            }
        } else {
            userContactsDto.setProfilePicture(null);
        }
        return userContactsDto;
    }


    //this service will return list of all users with filtration
    @Override
    public List<UserDashboardDTO> getUserFilteredDashboard(String name, String email, Double salary, int page, int size, String sort) {
        logger.info("starts fetching user details");
        try {
            Specification<User> specs = Specification.where(userHelper.filterByName(name))
                    .or(userHelper.filterByEmail(email))
                    .or(userHelper.filterBySalary(salary));

            //created PageRequest for pagination and sorting
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort.split(",")[0]).with(Sort.Direction.fromString(sort.split(",")[1]))));

            //fetched filtered , paginated , sorted users
            Page<User> user = userRepository.findAll(specs, pageRequest);
            if (user.isEmpty()) {
                throw new UserNotFoundException("No user found");
            }
            //convert to user dashboard dto
            return user.stream().map(this::userToUserDashboardDto).collect(Collectors.toList());
        } catch (UserNotFoundException e) {
            logger.error("user doesn't exist");
            throw e;
        } catch (Exception ex) {
            logger.error("Unexpected error Occur while fetching user details", ex);
            throw new RuntimeException("Error occur while fetching user details");
        }
    }


    //this method will set data of user in userDashboardDto
    private UserDashboardDTO userToUserDashboardDto(User user) {
        UserDashboardDTO userDashboardDTO = userHelper.entityToUserDashboardDto(user);
        //convert image into base64
        userDashboardDTO.setPrimaryMobile(user.getPrimaryMobileNumber());

        if (user.getProfilePicture() != null) {
            String filePath = user.getProfilePicture().getFilePath();
            File file = new File(filePath);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileData = fileInputStream.readAllBytes();
                String picture = Base64.getEncoder().encodeToString(fileData);
                userDashboardDTO.setProfilePicture(picture);

            } catch (IOException e) {
                userDashboardDTO.setProfilePicture(null);
            }
        } else {
            userDashboardDTO.setProfilePicture(null);
        }

        return userDashboardDTO;
    }

    // FETCH USER DETAILS FOR LOGIN API
    @Override
    public UserInfoDTO getUserByUsername(String username) throws IOException {
        User byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }

        if (byUsername.getProfilePicture() == null) {
            UserInfoDTO userInfoDTO = userHelper.entityToUserInfoDto(byUsername);
            userInfoDTO.setPicture(null);
            return userInfoDTO;
        }

        // Initialize the Base64 image string as null
        String base64Image = null;
        String filePath = uploadDirectory + File.separator + byUsername.getProfilePicture().getFileName();

        System.out.println(filePath);
        File imageFile = new File(filePath);

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
            base64Image = null; // Or use a placeholder image
        }

        // Convert the User entity to DTO
        UserInfoDTO userInfoDTO = userHelper.entityToUserInfoDto(byUsername);

        // Set the Base64 image string in the DTO
        userInfoDTO.setPicture(base64Image);

        return userInfoDTO;
    }

    @Override
    public List<DepartmentDropdownDTO> getDepartmentsByUserId(Long userId) {
        logger.info("Fetching departments for user with id: {}", userId);
        try {
            // Fetch the user by ID
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

            // Get the departments associated with the user
            Set<Department> departments = user.getDepartments();

            // Convert to DTO
            return departments.stream()
                    .map(department -> {
                        DepartmentDropdownDTO dto = new DepartmentDropdownDTO();
                        dto.setId(department.getId());
                        dto.setName(department.getName());
                        return dto;
                    })
                    .collect(Collectors.toList());
        } catch (UserNotFoundException e) {
            logger.error("User not found with id: {}", userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error fetching departments for user with id: {}", userId, e);
            throw new RuntimeException("Unexpected error fetching departments", e);
        }
    }

    @Override
    public UserInfoDTO addProfilePictureOfAUser(long userId, MultipartFile profilePicture) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        user.addPicture(userHelper.dtoToEntityForUserPicture(profilePicture, user));
        User saved = userRepository.save(user);
        String base64Image = null;
        String filePath = uploadDirectory + File.separator + saved.getProfilePicture().getFileName();
        File imageFile = new File(filePath);
        if (imageFile.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(imageFile)) {
                byte[] imageBytes = new byte[(int) imageFile.length()];
                fileInputStream.read(imageBytes);
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("Error reading or encoding image file", e);
            }
        } else {
            base64Image = null;
        }
        UserInfoDTO userInfoDTO = userHelper.entityToUserInfoDto(saved);
        userInfoDTO.setPicture(base64Image);
        return userInfoDTO;
    }

}