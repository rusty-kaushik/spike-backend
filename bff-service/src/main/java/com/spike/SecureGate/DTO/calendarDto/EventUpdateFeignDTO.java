package com.spike.SecureGate.DTO.calendarDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventUpdateFeignDTO {
    private String eventId;
    private String color;
    private String newStartingDate;
    private String newEndingDate;
    private String newStartingTime;
    private String newEndingTime;
    private String description;
    private String updatedBy;
}
