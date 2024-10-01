package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.ticketDto.TicketCreationDTO;
import com.spike.SecureGate.DTO.ticketDto.TicketCreationFeignDTO;
import com.spike.SecureGate.enums.TicketStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ticketClient",  url = "${spike.service.ticket_service}")
public interface TicketsFeignClient {

    @PostMapping("/tickets/create-ticket")
    ResponseEntity<Object> raiseTicket(
            @RequestBody TicketCreationFeignDTO dto
    );

    @GetMapping("/tickets/search-ticket")
    ResponseEntity<Object> searchTickets(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam List<String> sortList,
            @RequestParam String sortOrder
    );

    @GetMapping("/tickets/get-all")
    ResponseEntity<Object> getAllTicket(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam List<String> sortList,
            @RequestParam String sortOrder
    );

    @GetMapping("/tickets/get-count-by-status")
    ResponseEntity<Object> getCountByStatus(
            TicketStatus status
    );

    @PutMapping("/tickets/update/{id}/status")
    ResponseEntity<Object> updateTicketStatus(
            @PathVariable long id,
            @RequestParam TicketStatus status,
            @RequestParam String userName
    );

    @DeleteMapping("/tickets/{id}")
    ResponseEntity<Object> deleteTicket(
            @PathVariable long id,
            @RequestParam String userName
    );
}
