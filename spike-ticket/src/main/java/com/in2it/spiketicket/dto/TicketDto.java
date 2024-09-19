package com.in2it.spiketicket.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

	private long id;
	private String title;
	private String description;
	private String assignedBy;
	private String assignTo;
	private String  status;
	private LocalDate createdAt;

}
