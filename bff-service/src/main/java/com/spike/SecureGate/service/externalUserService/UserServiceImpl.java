package com.spike.SecureGate.service.externalUserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.SecureGate.DTO.userDto.*;
import com.spike.SecureGate.Validations.UserValidators;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.exceptions.UserNotFoundException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.UserFeignClient;
import com.spike.SecureGate.response.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import feign.FeignException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private UserValidators userValidators;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final String COUNTRIES_API_URL = "http://api.geonames.org/countryInfo?username={username}";

    @Value("${geonames.username}")
    private String GeoName;

    @Override
    public ResponseEntity<Object> createUser(String username, UserCreationRequestDTO data) {
        try {
            // Validate the user request
            if (!userValidators.validateUserCreationDetails(data)) {
                logger.error("Validation failed");
                throw new ValidationFailedException(
                        "ValidationError",
                        "Invalid data"
                );
            }
            return userFeignClient.createNewUser(username, data);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateSelfPassword(String userName, UserChangePasswordDTO userChangePasswordDTO) {
        try {
            userValidators.validateUserUpdatePassword(userChangePasswordDTO);
            return userFeignClient.updateSelfPassword(userName, userChangePasswordDTO);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while updating password of the user " + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while updating password of the user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateSelfDetails(Long userId, UserFullUpdateDTO userUpdateRequestDTO) {
        try{
            if (!userValidators.validateUserSelfDetailsUpdate(userUpdateRequestDTO)) {
                logger.error("Validation failed");
                throw new ValidationFailedException( "ValidationError","Invalid data");
            }
            return userFeignClient.updateUser(userId, userUpdateRequestDTO);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while updating details of the user " + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while updating details of the user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateSelfProfilePictureDetails(Long userId, MultipartFile profilePicture) {
        try{
            return userFeignClient.updateProfilePicture(userId,profilePicture );
        } catch (ValidationFailedException e) {
            throw e;
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while updating profile picture of the user " + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while updating profile picture of the user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long userId) {
        try{
            return userFeignClient.deleteUser(userId);
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while deleting user " + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while deleting user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> fetchUsersForEmployeePage(String name, String email, Double salary, int page, int size, String sort) {
        try{
            return userFeignClient.getSpecificUserInfoByUsername(name, email, salary, page, size, sort);
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while fetching employee page " + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while de: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> fetchUsersForContactPage(Long userId,String name, int pageSize, int pageNo, String sort) {
        try{
            return userFeignClient.getUserContact(userId, name, pageSize, pageNo, sort);
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while fetching employee page " + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while de: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> addProfilePicture(MultipartFile profilePicture, String username, long userId) {
        try {
            if (!userValidators.validateUserExistence(userId)) {
                logger.error("Validation failed");
                throw new ValidationFailedException( "ValidationError","Invalid user");
            }
            return userFeignClient.addProfilePictureOfAUser(userId, username, profilePicture);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }
    }

    @Override
    public ResponseEntity<Object> fetchSelfDetails(long userId) {
        return userFeignClient.getUserById(userId);
    }



    @Override
    public ResponseEntity<Object> fetchDepartmentsOfAUser(long userId) {
        logger.info("Started fetching departments for user ID: {}", userId);
        try {
            // Call to the Feign client to get departments
            ResponseEntity<Object> response = userFeignClient.getDepartmentsByUserId(userId);

            // Check the status of the response
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Successfully retrieved departments for user ID: {}", userId);
                return response; // Return the successful response
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                // If user is not found, throw a UserNotFoundException
                logger.error("User not found for ID: {}", userId);
                throw new UserNotFoundException("ValidationError", "User not found with id: " + userId);
            } else {
                // Handle other status codes if needed
                logger.warn("No departments found for user ID: {}", userId);
                return ResponseHandler.responseBuilder(
                        "No departments found for user ID: " + userId,
                        HttpStatus.OK,
                        "The user does not have any departments assigned."
                );
            }
        } catch (Exception e) {
            logger.error("User not found for user ID {}: {}", userId, e.getMessage());
            return ResponseHandler.responseBuilder(
                    "User not found with this associated id -> " +userId,
                    HttpStatus.NOT_FOUND,
                    "User not exists with this id -> "+userId
            );
        }
    }

    @Override
    public ResponseEntity<Object> createContact(ContactCreationRequestDTO contactCreationRequestDTO, Long userId, String username) {
        try{
            userValidators.validateUserContactCreation(contactCreationRequestDTO);
            return userFeignClient.createContacts(contactCreationRequestDTO, userId, username);
        }catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user contact: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user contact: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateContact(Long contactId, ContactCreationRequestDTO contactCreationRequestDTO) {
        try{
            return userFeignClient.updateContacts(contactId, contactCreationRequestDTO);
        }catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user contact: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user contact: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteContact(Long contactId) {
        try{
            return  userFeignClient.deleteContact(contactId);
        }catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user contact: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user contact: " + e.getMessage());
        }
    }


}
