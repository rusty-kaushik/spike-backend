package com.spike.user.repository;

import com.spike.user.entity.UserSocials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSocialRepository extends JpaRepository<UserSocials, Long> {
}
