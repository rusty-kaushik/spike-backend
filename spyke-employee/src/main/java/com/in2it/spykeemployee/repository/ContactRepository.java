package com.in2it.spykeemployee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in2it.spykeemployee.entity.Contact;
import java.util.List;
import java.util.Optional;


@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
	
	Optional<Contact> findByEmail(String email);
	List<Contact> findByPrimaryMobileNo(String primaryMobileNo);

}
