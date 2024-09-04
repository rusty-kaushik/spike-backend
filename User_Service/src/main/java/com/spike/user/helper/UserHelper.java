package com.spike.user.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.user.customMapper.UserMapper;
import com.spike.user.dto.UserAddressDTO;
import com.spike.user.dto.UserCreationRequestDTO;
import com.spike.user.entity.*;
import com.spike.user.repository.DepartmentRepository;
import com.spike.user.repository.RoleRepository;
import com.spike.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserHelper {

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
            User user = userMapper.dtoToEntity(userRequest);

            user.setPassword(passwordEncoder.encode("in2it")); // Set the password manually

            // Handle complex fields or relations
            Set<Department> departments = userRequest.getDepartment().stream()
                    .map(deptName -> departmentRepository.findByName(deptName)
                            .orElseThrow(() -> new IllegalArgumentException("Department not found: " + deptName)))
                    .collect(Collectors.toSet());
            user.setDepartments(departments);

            Role role = roleRepository.findByName(userRequest.getRole())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + userRequest.getRole()));
            user.setRole(role);

            return user;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public UserAddress dtoToEntityForUserAddress(UserAddressDTO userAddressDTO){
        return userMapper.dtoToEntityAddress(userAddressDTO);
    }

    public UserSocials dtoToEntityForUserSocials(UserCreationRequestDTO userRequest){
        return userMapper.dtoToEntitySocials(userRequest);
    }

    public UserProfilePicture dtoToEntityForUserPicture(MultipartFile profilePicture, UserCreationRequestDTO userRequest1) throws IOException {
        if (profilePicture != null && !profilePicture.isEmpty()) {

            String originalFileName = profilePicture.getOriginalFilename();
            String fileType = profilePicture.getContentType();
            Long fileSize = profilePicture.getSize();
            String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.')) : "";
            String newFileName = userRequest1.getEmployeeCode() + "_profile_picture" + fileExtension;
            String filePath = uploadDir + File.separator + newFileName;
            Path path = Paths.get(filePath);

            Files.copy(profilePicture.getInputStream(), path);

            UserProfilePicture userProfilePicture = new UserProfilePicture();
            userProfilePicture.setOriginalFileName(originalFileName);
            userProfilePicture.setFileName(newFileName);
            userProfilePicture.setFilePath(filePath);
            userProfilePicture.setFileType(fileType);
            userProfilePicture.setFileSize(fileSize);
            return userProfilePicture;
        }
        return null;
    }
}