package com.spike.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spike.user.entity.Contacts;

@Repository
public interface UserContactsRepository extends JpaRepository<Contacts, Long> {

}
