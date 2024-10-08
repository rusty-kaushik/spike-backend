package com.spike.user.repository;

import com.spike.user.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactsRepository extends JpaRepository<Contacts, Long>, JpaSpecificationExecutor<Contacts> {
    long countByUserId(long userId);
}