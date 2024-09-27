package com.spike.calender.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalenderEventUpdateDto {

	private String eventId;
	private String color;
	private String newStartingDate;
	private String newEndingDate;
	private String newStartingTime;
	private String newEndingTime;
	private String description;
	private String updatedBy;
}
