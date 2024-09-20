package com.spike.user.repository;

import com.spike.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> , JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    User findByName(String name);

    List<User> findAllByName(String name, Pageable page);

    @Query("SELECT u.id, u.name FROM User u WHERE u.role.id = 2")
    List<Object[]> findAllManagers();

}