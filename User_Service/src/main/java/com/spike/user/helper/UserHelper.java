package com.spike.user.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.user.customMapper.UserMapper;
import com.spike.user.dto.*;
import com.spike.user.entity.*;
import com.spike.user.exceptions.DepartmentNotFoundException;
import com.spike.user.exceptions.DtoToEntityConversionException;
import com.spike.user.exceptions.RoleNotFoundException;
import com.spike.user.repository.DepartmentRepository;
import com.spike.user.repository.RoleRepository;
import com.spike.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User dtoToEntityForUserMaster(UserCreationRequestDTO userRequest){
        try{
            logger.info("Converting UserCreationRequestDTO to User entity");
            User user = userMapper.dtoToEntity(userRequest);
            user.setPassword(passwordEncoder.encode("in2it")); // Set the password manually
            // Handle complex fields or relations
            Set<Department> departments = userRequest.getDepartment().stream()
                    .map(deptName -> departmentRepository.findByName(deptName)
                            .orElseThrow(() -> new DepartmentNotFoundException("Department not found: " + deptName)))
                    .collect(Collectors.toSet());
            user.setDepartments(departments);
            logger.debug("Departments set: {}", departments);
            Role role = roleRepository.findByName(userRequest.getRole())
                    .orElseThrow(() -> new RoleNotFoundException("Role not found: " + userRequest.getRole()));
            user.setRole(role);
            logger.debug("Role set: {}", role);
            return user;
        } catch (DepartmentNotFoundException |  RoleNotFoundException e){
            logger.error("Department or Role not found", e);
            throw e;
        } catch (Exception e){
            logger.error("Could not convert UserCreationRequestDTO to User entity", e);
            throw new DtoToEntityConversionException("Could not convert user DTO to user entity");
        }

    }

    public UserAddress dtoToEntityForUserAddress(UserAddressDTO userAddressDTO){
        try{
            return userMapper.dtoToEntityAddress(userAddressDTO);
        } catch (Exception e) {
            logger.error("Could not convert UserAddressDTO to UserAddress entity", e);
            throw new DtoToEntityConversionException("Could not convert user address DTO to user address entity");
        }
    }

    public UserSocials dtoToEntityForUserSocials(UserCreationRequestDTO userRequest){
        try{
            return userMapper.dtoToEntitySocials(userRequest);
        } catch (Exception e) {
            logger.error("Could not convert UserCreationRequestDTO to UserSocials entity", e);
            throw new DtoToEntityConversionException("Could not convert user social url DTO to user social url entity");
        }
    }

    public UserProfilePicture dtoToEntityForUserPicture(MultipartFile profilePicture, UserCreationRequestDTO userRequest1) {
        try {
            logger.info("Processing profile picture: {}", profilePicture.getOriginalFilename());
            if (profilePicture != null && !profilePicture.isEmpty()) {

                String originalFileName = profilePicture.getOriginalFilename();
                String fileType = profilePicture.getContentType();
                Long fileSize = profilePicture.getSize();
                String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.')) : "";
                String newFileName = userRequest1.getEmployeeCode() + "_profile_picture" + fileExtension;
                String filePath = uploadDir + File.separator + newFileName;
                Path path = Paths.get(filePath);

                Files.copy(profilePicture.getInputStream(), path);
                logger.debug("Profile picture saved to path: {}", filePath);

                UserProfilePicture userProfilePicture = new UserProfilePicture();
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
            logger.error("Could not convert user profile picture DTO to UserProfilePicture entity", e);
            throw new DtoToEntityConversionException("Could not convert user profile picture DTO to user profile picture entity");
        }
    }

    //this specification will filter record based on user name
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



    //this specification will filter record based on user salary
    public Specification<User> filterBySalary(Double salary) {
        return (root, query, cb) -> salary != null ? cb.equal(root.get("salary"), salary) : null;
    }



    public UserContactsDTO entityToUserContactsDto(User user){
        try{
            return userMapper.entityToDtoContact(user);
        } catch (Exception e) {
            logger.error("Could not convert User entity to UserContactsDTO", e);
            throw new DtoToEntityConversionException("Could not convert user entity to user contacts DTO");
        }
    }

    public UserAddressDTO entityToUserAddressDto(UserAddress address){
        try{
            return userMapper.entityToDtoAddress(address);
        } catch (Exception e) {
            logger.error("Could not convert UserAddress entity to UserAddressDTO", e);
            throw new DtoToEntityConversionException("Could not convert user address entity to user address DTO");
        }
    }

    public UserDashboardDTO entityToUserDashboardDto(User user){
        try{
            return userMapper.entityToDtoDashboard(user);
        } catch (Exception e) {
            logger.error("Could not convert User entity to UserDashboardDTO", e);
            throw new DtoToEntityConversionException("Could not convert user entity to user dashboard DTO");
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
}