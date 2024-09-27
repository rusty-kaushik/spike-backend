package com.spike.SecureGate.service.externalTicketService;
import com.spike.SecureGate.DTO.ticketDto.TicketCreationDTO;
import com.spike.SecureGate.controllers.TicketController;
import com.spike.SecureGate.enums.TicketStatus;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.TicketsFeignClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketsFeignClient ticketsFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Override
    public ResponseEntity<Object> createTicket(TicketCreationDTO ticketCreationDTO) {
        try{
           return ticketsFeignClient.raiseTicket(ticketCreationDTO);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a ticket: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a ticket: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> searchTicket(String keyword, int page, int size, List<String> sortList, String sortOrder) {
        try{
            return ticketsFeignClient.searchTickets(keyword, page, size, sortList, sortOrder);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while fetching ticket: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while fetching ticket: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getAllTickets(int page, int size, List<String> sortList, String sortOrder) {
        try{
            return ticketsFeignClient.getAllTicket(page, size, sortList, sortOrder);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while fetching ticket: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while fetching ticket: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getStatusCount(TicketStatus status) {
        try{
            return ticketsFeignClient.getCountByStatus(status);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while fetching ticket: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while fetching ticket: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateTicketStatus(long ticketId, TicketStatus status, String userName) {
        try{
            return ticketsFeignClient.updateTicketStatus(ticketId, status, userName);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while fetching ticket: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while fetching ticket: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteTicket(long ticketId, String userName) {
        try{
            return ticketsFeignClient.deleteTicket(ticketId, userName);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while fetching ticket: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while fetching ticket: " + e.getMessage());
        }
    }


}
