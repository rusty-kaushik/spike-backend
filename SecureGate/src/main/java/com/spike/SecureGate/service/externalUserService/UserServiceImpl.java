package com.spike.SecureGate.service.externalUserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.SecureGate.DTO.userDto.*;
import com.spike.SecureGate.Validations.UserValidators;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private UserValidators userValidators;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<Object> createUser(String username, MultipartFile profilePicture, String data) {
        try {
            // CONVERTING TO JSON FOR VALIDATION.
            UserCreationRequestDTO userRequest = objectMapper.readValue(data, UserCreationRequestDTO.class);
            // Validate the user request
            if (!userValidators.validateUserCreationDetails(userRequest)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("Invalid data");
            }
            ResponseEntity<Object> newUser = userFeignClient.createNewUser(username, profilePicture, data);
            return newUser;
        } catch (JsonProcessingException e)  {
            logger.error("String to Json conversion failed");
            throw new ValidationFailedException("Invalid JSON data");
        } catch (ValidationFailedException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while creating a user: " + e.getMessage());
            throw new UnexpectedException("An unexpected error occurred while creating a user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateSelfPassword(String userName, UserChangePasswordDTO userChangePasswordDTO) {
        try {
            if (!userValidators.validateUserUpdatePassword(userChangePasswordDTO)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("Invalid data");
            }
            ResponseEntity<Object> result = userFeignClient.updateSelfPassword(userName, userChangePasswordDTO);
            return result;
        } catch (ValidationFailedException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while updating password of the user " + e.getMessage());
            throw new UnexpectedException("An unexpected error occurred while updating password of the user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateSelfDetails(Long userId, UserFullUpdateDTO userUpdateRequestDTO) {
        try{
            if (!userValidators.validateUserSelfDetailsUpdate(userUpdateRequestDTO)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("Invalid data");
            }
            return userFeignClient.updateUser(userId, userUpdateRequestDTO);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while updating details of the user " + e.getMessage());
            throw new UnexpectedException("An unexpected error occurred while updating details of the user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateSelfProfilePictureDetails(Long userId, MultipartFile profilePicture) {
        try{
            return userFeignClient.updateProfilePicture(userId,profilePicture );
        } catch (ValidationFailedException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while updating profile picture of the user " + e.getMessage());
            throw new UnexpectedException("An unexpected error occurred while updating profile picture of the user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long userId) {
        try{
            return userFeignClient.deleteUser(userId);
        } catch (Exception e) {
            logger.error("Error occurred while deleting user " + e.getMessage());
            throw new UnexpectedException("An unexpected error occurred while deleting user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> fetchUsersForEmployeePage(String name, String email, Double salary, int page, int size, String sort) {
        return userFeignClient.getSpecificUserInfoByUsername(name, email, salary, page, size, sort);
    }

    @Override
    public ResponseEntity<Object> fetchUsersForContactPage(String name, int pageSize, int pageNo, String sort) {
        return userFeignClient.getUserContact(name, pageSize, pageNo, sort);
    }

}
