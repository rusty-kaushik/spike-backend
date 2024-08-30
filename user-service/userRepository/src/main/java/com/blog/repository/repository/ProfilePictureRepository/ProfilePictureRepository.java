package com.blog.repository.repository.ProfilePictureRepository;

import com.blog.repository.entity.UserProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<UserProfilePicture, Long> {
}
