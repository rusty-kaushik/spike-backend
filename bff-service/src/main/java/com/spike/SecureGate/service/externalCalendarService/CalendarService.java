package com.spike.SecureGate.service.externalCalendarService;

import com.spike.SecureGate.DTO.calendarDto.EventCreationRequestDTO;
import com.spike.SecureGate.DTO.calendarDto.EventUpdateRequestDTO;
import org.springframework.http.ResponseEntity;

public interface CalendarService {

    ResponseEntity<Object> createEvent(EventCreationRequestDTO eventCreationRequestDTO, String userName);

    ResponseEntity<Object> getEventByDate(String date);

    ResponseEntity<Object> getEventByWeek();

    ResponseEntity<Object> getEventByMonth(int year, int month);

    ResponseEntity<Object> updateEvent(EventUpdateRequestDTO eventUpdateRequestDTO, String userName);

    ResponseEntity<Object> deleteEvent(String eventId, String userName, String reason);
}
