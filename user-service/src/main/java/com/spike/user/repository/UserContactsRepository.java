package com.spike.user.repository;

import com.spike.user.entity.Contacts;
import com.spike.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactsRepository extends JpaRepository<Contacts, Long> , JpaSpecificationExecutor<Contacts> {

}