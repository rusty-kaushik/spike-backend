package com.in2it.spiketicket.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.in2it.spiketicket.constants.Status;
import com.in2it.spiketicket.dto.CreateTicketDto;
import com.in2it.spiketicket.dto.TicketDto;
import com.in2it.spiketicket.entity.Ticket;

public interface TicketService {
	
	TicketDto raiseTicket(CreateTicketDto dto);
	Page<TicketDto> searchTickets(String keyword, Long id, LocalDate createdAt, int page, int size, List<String> sortList, String sortOrder);
	Page<TicketDto> getAllTicket(int page, int size, List<String> sortList, String sortOrder);
	TicketDto updateStatusOfTicket(long id,String status, String userName);
	TicketDto updateStatusOfTicket(long id,Status status, String userName);
	
	boolean deleteTicket(long ticketId, String userName);
	
	long getCountOfTotalTicket();
	long getCountByStatus(String status);

}
