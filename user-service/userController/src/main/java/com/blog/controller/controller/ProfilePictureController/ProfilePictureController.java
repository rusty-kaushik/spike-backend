package com.blog.controller.controller.ProfilePictureController;

import com.blog.repository.entity.UserProfilePicture;
import com.blog.service.service.ProfilePictureService.ProfilePictureService;
import com.blog.service.service.ProfilePictureService.ProfilePictureServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
public class ProfilePictureController {

    private ProfilePictureServiceImpl profilePictureService;

    @PostMapping("/profilePicture")
    private String uploadProfilePicture(MultipartFile profilePicture , Long userid) throws IOException {
   profilePictureService.saveProfilePicture(profilePicture, userid);
   return "picture Uploaded successfully";

    }

}
