package com.spike.SecureGate.DTO.ticketDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketCreationFeignDTO {
    private String title;
    private String description;
    private String assignTo;
    private String assignBy;
}
