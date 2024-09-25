package com.spike.SecureGate.DTO.ticketDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketCreationDTO {
    private String title;
    private String description;
    private String assignedBy;
    private String assignTo;
}
