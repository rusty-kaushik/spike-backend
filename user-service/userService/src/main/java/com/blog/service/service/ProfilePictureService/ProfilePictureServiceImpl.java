package com.blog.service.service.ProfilePictureService;

import com.blog.repository.entity.User;
import com.blog.repository.entity.UserProfilePicture;
import com.blog.repository.repository.ProfilePictureRepository.ProfilePictureRepository;
import com.blog.repository.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ProfilePictureServiceImpl {

    @Autowired
    private ProfilePictureRepository profilePictureRepository;


    @Value("${profile.pictures.directory}")
    private String uploadDirectory;

    private  UserRepository userRepository;


    public UserProfilePicture saveProfilePicture(MultipartFile file, Long userId) throws IOException {
        // Validate the file
        if (file.isEmpty() || !file.getContentType().equals("image/jpeg")) {
            throw new IllegalArgumentException("Invalid file. Only JPG files are supported.");
        }

        // Generate a unique file name
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        // Save the file to the specified directory
        Path filePath = Paths.get(uploadDirectory, newFileName);
        Files.copy(file.getInputStream(), filePath);

        // Save metadata to the database
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserProfilePicture userProfilePicture = new UserProfilePicture();
        userProfilePicture.setUser_id(user);
        userProfilePicture.setFile_name(newFileName);
        userProfilePicture.setFile_path(filePath.toString());
        userProfilePicture.setFile_type(file.getContentType());
        userProfilePicture.setFile_size(file.getSize());

        return profilePictureRepository.save(userProfilePicture);
    }
}
