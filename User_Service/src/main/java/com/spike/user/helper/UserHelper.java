package com.spike.user.helper;

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
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User dtoToEntityForUserMaster(UserCreationRequestDTO userRequest1){
        User user = new User();
        user.setUsername(userRequest1.getEmployeeCode());
        user.setEmpCode(userRequest1.getEmployeeCode());
        user.setEmail(userRequest1.getEmail());
        user.setPassword(passwordEncoder.encode("in2it"));
        user.setName(userRequest1.getName());
        user.setPrimaryMobile(userRequest1.getPrimaryMobileNumber());
        user.setSecondaryMobile(userRequest1.getSecondaryMobileNumber());
        user.setJoiningDate(userRequest1.getJoiningDate());
        user.setBackupEmail(userRequest1.getBackupEmail());
        user.setManagerId(userRequest1.getManagerId());
        user.setSalary(userRequest1.getSalary());
        user.setDesignation(userRequest1.getDesignation());

        Set<Department> departments = userRequest1.getDepartment().stream()
                .map(deptName -> departmentRepository.findByName(deptName)
                        .orElseThrow(() -> new IllegalArgumentException("Department not found: " + deptName)))
                .collect(Collectors.toSet());
        user.setDepartments(departments);

        Role role = roleRepository.findByName(userRequest1.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + userRequest1.getRole()));
        user.setRole(role);

        return user;
    }

    public UserAddress dtoToEntityForUserAddress(UserAddressDTO userAddressDTO){
        UserAddress userAddress  = new UserAddress();
        userAddress.setAddressLine1(userAddressDTO.getLine1());
        userAddress.setAddressLine2(userAddressDTO.getLine2());
        userAddress.setState(userAddressDTO.getState());
        userAddress.setDistrict(userAddressDTO.getDistrict());
        userAddress.setZip(userAddressDTO.getZip());
        userAddress.setCity(userAddressDTO.getCity());
        userAddress.setNearestLandmark(userAddressDTO.getNearestLandmark());
        userAddress.setCountry(userAddressDTO.getCountry());
        userAddress.setType(userAddressDTO.getType());
        return userAddress;
    }

    public UserSocials dtoToEntityForUserSocials(UserCreationRequestDTO userRequest1){
        UserSocials userSocials  = new UserSocials();
        userSocials.setInstagram_url(userRequest1.getInstagramUrl());
        userSocials.setLinkedin_url(userRequest1.getLinkedinUrl());
        userSocials.setFacebookUrl(userRequest1.getFacebookUrl());
        return userSocials;
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