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

	private String startingDate;

	private String endingDate;

	private String startingTime;

	private String endingTime;

	private String[] sharedBy;

	private String description;
	
	private String link;
	
	private String updatedAt;
	private String createdAt;
	private String createdBy;
	private String updatedBy;
}
