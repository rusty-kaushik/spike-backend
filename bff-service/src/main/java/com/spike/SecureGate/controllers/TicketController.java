package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.ticketDto.TicketCreationDTO;
import com.spike.SecureGate.DTO.userDto.*;

import com.spike.SecureGate.enums.TicketStatus;
import com.spike.SecureGate.service.externalTicketService.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin("*")
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    // CREATE A NEW TICKET
    @Operation(
            summary = "User creates a new ticket",
            description = "Creates a new ticket."
    )
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> createTicket(
            @RequestBody TicketCreationDTO ticketCreationDTO
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new ticket");
        ResponseEntity<Object> user = ticketService.createTicket(ticketCreationDTO);
        logger.info("Finished creating new ticket");
        return user;
    }

    // SEARCH TICKET
    @Operation(
            summary = "User searches ticket",
            description = "Searches ticket."
    )
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> searchTicket(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "sortList", required = false) List<String> sortList,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching ticket");
        ResponseEntity<Object> user = ticketService.searchTicket(keyword,page,size,sortList,sortOrder);
        logger.info("Finished fetching ticket");
        return user;
    }

    // FETCH ALL TICKETS
    @Operation(
            summary = "Get all ticket",
            description = "Get All ticket."
    )
    @GetMapping("/fetch")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> sortList,
            @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching ticket");
        ResponseEntity<Object> user = ticketService.getAllTickets(page,size,sortList,sortOrder);
        logger.info("Finished fetching ticket");
        return user;
    }

    // FETCH TICKETS NUMBER BY STATUS
    @Operation(
            summary = "Get the number of tickets by id.",
            description = "Get the number of tickets by id."
    )
    @GetMapping("/status")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getStatusCount(
            TicketStatus status
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching ticket count");
        ResponseEntity<Object> user = ticketService.getStatusCount(status);
        logger.info("Finished fetching ticket count");
        return user;
    }


    // UPDATE TICKET STATUS
    @Operation(
            summary = "Update ticket status by ID.",
            description = "Update ticket status by ID."
    )
    @PutMapping("/update/status/{ticketId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> updateTicketStatus(
            @PathVariable long ticketId,
            @RequestParam TicketStatus status,
            @RequestParam String userName
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started updating ticket status");
        ResponseEntity<Object> user = ticketService.updateTicketStatus(ticketId, status, userName);
        logger.info("Finished updating ticket status");
        return user;
    }

    // UPDATE TICKET STATUS
    @Operation(
            summary = "Update ticket status by ID.",
            description = "Update ticket status by ID."
    )
    @DeleteMapping("/delete/{ticketId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> deleteTicket(
            @PathVariable long ticketId
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started updating ticket status");
        ResponseEntity<Object> user = ticketService.deleteTicket(ticketId, userName);
        logger.info("Finished updating ticket status");
        return user;
    }

}
