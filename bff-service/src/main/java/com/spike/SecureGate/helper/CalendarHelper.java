package com.spike.SecureGate.helper;

import com.spike.SecureGate.DTO.blogDto.BlogCreationFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.calendarDto.EventCreationFeignDTO;
import com.spike.SecureGate.DTO.calendarDto.EventCreationRequestDTO;
import com.spike.SecureGate.DTO.calendarDto.EventUpdateFeignDTO;
import com.spike.SecureGate.DTO.calendarDto.EventUpdateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CalendarHelper {

    public EventCreationFeignDTO eventCreationDtoTOFeignDto(EventCreationRequestDTO eventCreationRequestDTO,String userName) {
        EventCreationFeignDTO eventCreationFeignDTO = new EventCreationFeignDTO();
        eventCreationFeignDTO.setCreatedBy(userName);
        eventCreationFeignDTO.setEventName(eventCreationRequestDTO.getEventName());
        eventCreationFeignDTO.setColor(eventCreationRequestDTO.getColor());
        eventCreationFeignDTO.setStartingDate(eventCreationRequestDTO.getStartingDate());
        eventCreationFeignDTO.setEndingDate(eventCreationRequestDTO.getEndingDate());
        eventCreationFeignDTO.setStartingTime(eventCreationRequestDTO.getStartingTime());
        eventCreationFeignDTO.setEndingTime(eventCreationRequestDTO.getEndingTime());
        eventCreationFeignDTO.setSharedBy(eventCreationRequestDTO.getSharedBy());
        eventCreationFeignDTO.setDescription(eventCreationRequestDTO.getDescription());
        eventCreationFeignDTO.setLink(eventCreationRequestDTO.getLink());
        return eventCreationFeignDTO;
    }

    public EventUpdateFeignDTO eventUpdateDtoTOFeignDto(EventUpdateRequestDTO eventUpdateRequestDTO, String userName) {
        EventUpdateFeignDTO eventUpdateFeignDTO = new EventUpdateFeignDTO();
        eventUpdateFeignDTO.setEventId(eventUpdateRequestDTO.getEventId());
        eventUpdateFeignDTO.setColor(eventUpdateRequestDTO.getColor());
        eventUpdateFeignDTO.setNewEndingDate(eventUpdateRequestDTO.getNewEndingDate());
        eventUpdateFeignDTO.setNewStartingDate(eventUpdateRequestDTO.getNewStartingDate());
        eventUpdateFeignDTO.setNewStartingTime(eventUpdateRequestDTO.getNewStartingTime());
        eventUpdateFeignDTO.setNewEndingTime(eventUpdateRequestDTO.getNewEndingTime());
        eventUpdateFeignDTO.setDescription(eventUpdateRequestDTO.getDescription());
        eventUpdateFeignDTO.setUpdatedBy(userName);
        return eventUpdateFeignDTO;
    }

}