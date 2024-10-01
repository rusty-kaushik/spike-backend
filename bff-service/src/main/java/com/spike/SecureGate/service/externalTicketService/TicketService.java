package com.spike.SecureGate.service.externalTicketService;

import com.spike.SecureGate.DTO.ticketDto.TicketCreationDTO;
import com.spike.SecureGate.enums.TicketStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {


    ResponseEntity<Object> createTicket(TicketCreationDTO ticketCreationDTO, String username);

    ResponseEntity<Object> searchTicket(String keyword, int page, int size, List<String> sortList, String sortOrder);

    ResponseEntity<Object> getAllTickets(int page, int size, List<String> sortList, String sortOrder);

    ResponseEntity<Object> getStatusCount(TicketStatus status);

    ResponseEntity<Object> updateTicketStatus(long ticketId, TicketStatus status, String userName);

    ResponseEntity<Object> deleteTicket(long ticketId, String userName);
}
