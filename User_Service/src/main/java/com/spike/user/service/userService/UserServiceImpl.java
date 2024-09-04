package com.spike.user.service.userService;

import com.spike.user.dto.UserAddressDTO;
import com.spike.user.dto.UserChangePasswordDTO;
import com.spike.user.dto.UserCreationRequestDTO;
import com.spike.user.entity.User;
import com.spike.user.entity.UserAddress;
import com.spike.user.exceptions.*;
import com.spike.user.helper.UserHelper;
import com.spike.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepository;

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
}