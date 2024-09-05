package com.spike.user.service.userService;

import com.spike.user.dto.UserAddressDTO;
import com.spike.user.dto.UserChangePasswordDTO;
import com.spike.user.dto.UserCreationRequestDTO;
import com.spike.user.entity.User;
import com.spike.user.entity.UserAddress;
import com.spike.user.entity.UserProfilePicture;
import com.spike.user.entity.UserSocials;
import com.spike.user.exceptions.*;
import com.spike.user.helper.UserHelper;
import com.spike.user.repository.UserProfilePictureRepository;
import com.spike.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfilePictureRepository userProfilePictureRepository;

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createNewUser(MultipartFile profilePicture, UserCreationRequestDTO userRequest) {
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
            //SETS PROFILE PICTURE
            user.addPicture(userHelper.dtoToEntityForUserPicture(profilePicture, userRequest));
            User savedUser = userRepository.save(user);
            logger.info("User saved successfully: {}", savedUser);
            return savedUser;
        } catch (DepartmentNotFoundException | RoleNotFoundException | DtoToEntityConversionException e ) {
            throw e;
        } catch ( Exception e ) {
            logger.error("Error creating user", e);
            throw new UnexpectedException("Error creating user", e.getCause());
        }

    }

    @Override
    public List<User> getAllUsers() {
        try{
            return userRepository.findAll();
        } catch ( Exception e ) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }
    }

    @Override
    public String updateSelfPassword(String username, UserChangePasswordDTO userChangePasswordDTO) {
        logger.info("Attempting to update password for user: {}", username);
        try{
            User existingUser = userRepository.findByUsername(username);
            if (existingUser == null) {
                logger.warn("User not found: {}", username);
                throw new EmployeeNotFoundException("User not found");
            }
            if (!passwordEncoder.matches(userChangePasswordDTO.getOldPassword(), existingUser.getPassword())) {
                logger.warn("Old password does not match for user: {}", username);
                throw new PasswordNotMatchException("Old password does not match");
            }
            existingUser.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
            userRepository.save(existingUser);
            logger.info("Successfully updated password for user: {}", username);
            return "Successfully updated password for " + username;
        } catch (EmployeeNotFoundException | PasswordNotMatchException e) {
            logger.error("Error updating password for user: {}", username, e);
            throw e;
        } catch ( Exception e ) {
            logger.error("An unexpected error occurred while updating password for user: {}", username, e);
            throw new UnexpectedException("An unexpected error occurred while updating the password", e);
        }
    }

    // Update User Method

    @Override
    @Transactional
    public User updateUser(Long userId, UserCreationRequestDTO userRequest) throws IOException {
        logger.info("Starting update process for user ID: {}", userId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new EntityNotFoundException("User not found with id: " + userId);
                });

        // update user fields
        setUserFields(existingUser, userRequest);
        logger.info("User fields updated for user ID: {}", userId);

        return userRepository.save(existingUser);
    }

    // it will set the fields to update the user - logic
    private void setUserFields(User user, UserCreationRequestDTO userRequest) {

        user.setBackupEmail(userRequest.getBackupEmail());
        user.setPrimaryMobileNumber(userRequest.getPrimaryMobileNumber());
        user.setSecondaryMobileNumber(userRequest.getSecondaryMobileNumber());
        if (userRequest.getEmployeeCode() == null || userRequest.getEmployeeCode().isEmpty()) {
            throw new RuntimeException("Username cannot be null or empty");
        }
        user.setUsername(userRequest.getEmployeeCode());
    }

    // Update User Social Urls
    @Override
    @Transactional
    public User updateSocialUrls(Long userId, UserCreationRequestDTO userRequest) throws IOException {
        logger.info("Starting social URL update for user ID: {}", userId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new EntityNotFoundException("User not found with id: " + userId);
                });

        setSocialUrls(existingUser, userRequest);
        logger.info("Social URLs updated for user ID: {}", userId);

        return userRepository.save(existingUser);
    }

    // it will take fields that we need to update - logic
    private void setSocialUrls(User user, UserCreationRequestDTO userRequest) {
        UserSocials userSocials = user.getUserSocials();

        if (userSocials == null) {
            userSocials = new UserSocials();
            userSocials.setUser(user);
            user.setUserSocials(userSocials);
        }

        userSocials.setLinkedinUrl(userRequest.getLinkedinUrl());
        userSocials.setFacebookUrl(userRequest.getFacebookUrl());
        userSocials.setInstagramUrl(userRequest.getInstagramUrl());
    }

    // Update User Address
    @Override
    @Transactional
    public User updateAddresses(Long userId, List<UserAddressDTO> addresses) throws IOException {
        logger.info("Starting address update for user ID: {}", userId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new EntityNotFoundException("User not found with id: " + userId);
                });

        setUserAddresses(existingUser, addresses);
        logger.info("Addresses updated for user ID: {}", userId);

        return userRepository.save(existingUser);
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

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new EntityNotFoundException("User not found with id: " + userId);
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
    }

    @Override
    public void deleteUser(Long userId) {
        logger.info("Starting user deletion for user ID: {}", userId);

        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> {
                        logger.error("User not found with id: {}", userId);
                        return new RuntimeException("User not found with id: " + userId);
                    });

            userRepository.delete(existingUser);
            logger.info("User deleted successfully with ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", userId, e);
            throw new RuntimeException("Error deleting user", e);
        }
    }
}