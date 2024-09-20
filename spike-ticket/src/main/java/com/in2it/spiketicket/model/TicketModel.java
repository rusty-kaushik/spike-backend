package com.in2it.spiketicket.model;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;


@Data
public class TicketModel extends RepresentationModel<TicketModel>{
	
	private long id;
	private String title;
	private String description;
	private String assignedBy;
	private String assignTo;
	private String  status;
	private LocalDate createdAt;

}
