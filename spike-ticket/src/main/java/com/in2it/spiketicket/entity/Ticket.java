package com.in2it.spiketicket.entity;

import java.time.LocalDate;

import com.in2it.spiketicket.constants.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String description;
	private String assignedBy;
	private String assignTo;
	
	@Enumerated(EnumType.STRING)
	private Status  status;
	private LocalDate createdAt;
	private String updatedBy;
	private LocalDate updatedAt;
	private boolean deleted;

}
