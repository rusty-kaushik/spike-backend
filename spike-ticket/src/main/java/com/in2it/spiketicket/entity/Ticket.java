package com.in2it.spiketicket.entity;

import java.time.LocalDate;

import com.in2it.spiketicket.constants.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
	
	@Column(nullable = false)
	@NotBlank(message = "Title field can not be empty or blank")
	private String title;
	
	private String description;
	
	@Column(name = "assign_by",nullable = false)
	@NotBlank(message = "Assigned By must be required")
	private String assignedBy; //user name
	
	@Column(name = "assign_to",nullable = false)
	@NotBlank(message = "Assigned To must be required")
	private String assignTo; //user name 
	
	@Enumerated(EnumType.STRING)
	private Status  status;
	
	@Column(name = "created_at")
	private LocalDate createdAt;
	private String updatedBy;
	private LocalDate updatedAt;
	private boolean deleted;

}
