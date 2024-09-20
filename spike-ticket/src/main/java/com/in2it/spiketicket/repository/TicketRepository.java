package com.in2it.spiketicket.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in2it.spiketicket.constants.Status;
import com.in2it.spiketicket.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

	Page<Ticket> findByAssignedBy(String assignBy, Pageable pageable);

	Page<Ticket> findByDeleted(boolean deleted, Pageable pageable);

	@Query(value = "SELECT * FROM ticket "
			+ "WHERE to_tsvector('english', title) @@ to_tsquery('english', :keyword || ':*') "
			+ "OR to_tsvector('english', description) @@ to_tsquery('english', :keyword || ':*') "
			+ "OR to_tsvector('english', assign_to) @@ to_tsquery('english', :keyword || ':*') "
			+ "OR to_tsvector('english', status) @@ to_tsquery('english', :keyword || ':*')", nativeQuery = true)
	Page<Ticket> fullTextSearch(@Param("keyword") String keyword, Pageable pageable);

	Optional<Ticket> findByIdAndDeleted(long id, boolean deleted);

	long count();

	long countByDeleted(boolean deleted);

	long countByStatusAndDeleted(Status status, boolean deleted);

	long countByAssignedByAndAndDeleted(String assignedBy, boolean deleted);

	long countByAssignToAndDeleted(String assignTo, boolean deleted);

}
