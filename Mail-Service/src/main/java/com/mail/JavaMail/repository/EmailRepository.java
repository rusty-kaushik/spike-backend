package com.mail.JavaMail.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mail.JavaMail.entity.EmailHistory;

@Repository
public interface EmailRepository extends JpaRepository<EmailHistory, UUID>{

	@Query(nativeQuery = true, value = "SELECT * FROM email_history e WHERE e.sender = ':emailId' ORDER BY e.created_at ASC;")
	public List<EmailHistory> findByEmailIDAsc(String emailId);
}
