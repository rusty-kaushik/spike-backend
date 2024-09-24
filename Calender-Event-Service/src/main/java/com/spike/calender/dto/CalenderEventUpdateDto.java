package com.spike.calender.dto;

import java.util.Optional;

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
	private Optional<String> color;
	private Optional<String> newStartingDate;
	private Optional<String> newendingDate;
	private Optional<String> newStartingTime;
	private Optional<String> newEndingTime;
	private Optional<String> description;
	private Optional<String> updatedBy;
}
