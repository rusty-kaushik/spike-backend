package com.in2it.spiketicket.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in2it.spiketicket.entity.Ticket;
import com.in2it.spiketicket.constants.Status;
import java.util.List;
import java.util.Optional;




@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	Page<Ticket> findByIdEqualsOrTitleContainingIgnoreCaseOrAssignToContainingIgnoreCaseOrAssignedByContainingIgnoreCaseOrCreatedAtOrStatusContainingAllIgnoreCaseAndDeleted(
            Long id, String title, String assignTo, String assignedBy, LocalDate createdAt,String status,boolean deleted, Pageable pageable);
	Page<Ticket>  findByAssignedBy(String assignBy, Pageable pageable);
	Page<Ticket> findByDeleted(boolean deleted, Pageable pageable);
	
	Optional<Ticket> findByIdAndDeleted(long id, boolean deleted);
	long count();
	long countByDeleted(boolean deleted);
	long countByStatusAndDeleted(Status status, boolean deleted);
	long countByAssignedByAndAndDeleted(String assignedBy, boolean deleted);
	long countByAssignToAndDeleted(String assignTo, boolean deleted);
	

}
