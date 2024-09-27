package com.spike.user.repository;

import com.spike.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {
}
