package com.spike.calender.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalenderResponseDto {

	private String eventId;

	private String eventName;

	private String color;

	private LocalDate startingDate;

	private LocalDate endingDate;

	private LocalTime startingTime;

	private LocalTime endingTime;

	private String[] sharedBy;

	private String description;
	
	private String link;
	
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
	private String createdBy;
	private String updatedBy;
}
