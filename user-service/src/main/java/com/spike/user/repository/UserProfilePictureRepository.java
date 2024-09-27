package com.spike.user.repository;

import com.spike.user.entity.UserProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfilePictureRepository extends JpaRepository<UserProfilePicture,Long> {
}
