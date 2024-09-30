package com.spike.SecureGate.DTO.calendarDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventCreationRequestDTO {
    private String eventName;
    private String color;
    private String startingDate;
    private String endingDate;
    private String startingTime;
    private String endingTime;
    private String[] sharedBy;
    private String description;
    private String link;
}
