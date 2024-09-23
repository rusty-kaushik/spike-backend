package com.spike.SecureGate.service.externalUserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.SecureGate.DTO.userDto.*;
import com.spike.SecureGate.Validations.UserValidators;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import feign.FeignException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
        } catch (Exception e) {
            logger.error("Error occurred while updating profile picture of the user " + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while updating profile picture of the user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long userId) {
        try{
            return userFeignClient.deleteUser(userId);
        } catch (Exception e) {
            logger.error("Error occurred while deleting user " + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while deleting user: " + e.getMessage());
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
        }
    }

    @Override
    public ResponseEntity<Object> fetchSelfDetails(long userId) {
        return userFeignClient.getUserById(userId);
    }


    @Override
    public ResponseEntity<Object> fetchDepartmentsOfAUser(long userId) {
        return userFeignClient.getDepartmentsByUserId(userId);
    }

    @Override
    public List<String> getCountriesWithStates() {
        // Make the API call
        String xmlResponse = restTemplate.getForObject(COUNTRIES_API_URL, String.class, GeoName);

        // Parse the XML response
        List<String> countryNames = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xmlResponse.getBytes());
            Document document = builder.parse(is);

            NodeList countryNodes = document.getElementsByTagName("country");
            for (int i = 0; i < countryNodes.getLength(); i++) {
                String countryName = document.getElementsByTagName("countryName").item(i).getTextContent();
                countryNames.add(countryName);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception properly in production code
        }

        return countryNames;
    }


}
