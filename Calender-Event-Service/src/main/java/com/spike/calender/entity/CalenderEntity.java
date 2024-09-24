package com.spike.calender.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalenderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID eventId;
	@NotNull
	private String eventName;
	@NotNull
	private String color;
	@NotNull
	private LocalDate startingDate;
	@NotNull
	private LocalDate endingDate;
	@NotNull
	private LocalTime startingTime;
	@NotNull
	private LocalTime endingTime;
	@NotNull
	private String[] sharedBy;
	@NotNull
	private String description;
	private LocalDateTime updatedAt;
	@NotNull
	private LocalDateTime createdAt;
	@NotNull
	private String createdBy;
	private String updatedBy;
	private String link;
	private String reason;
	@NotNull
	private boolean status;
}
