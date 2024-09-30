package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.calendarDto.EventCreationFeignDTO;
import com.spike.SecureGate.DTO.calendarDto.EventUpdateFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "calendarClient",  url = "${spike.service.calendar_service}")
public interface CalendarFeignClient {

    @PostMapping("create-event")
    ResponseEntity<Object> createCalenderEvent(
            @RequestBody EventCreationFeignDTO calenderDto
    );

    @GetMapping("get-event-by-date/{date}")
    ResponseEntity<Object> getCalenderEventByDate(
            @PathVariable("date") String date
    );

    @GetMapping("get-event-by-week")
    ResponseEntity<Object> getCalenderEventByWeek();

    @GetMapping("get-event-by-month/{year}/{month}")
    ResponseEntity<Object> getCalenderEventByMonth(
            @PathVariable("year") int year,
            @PathVariable("month") int month
    );

    @PutMapping("update-event-by-enventId")
    ResponseEntity<Object> updateCalenderEventByEventId(
            @RequestBody EventUpdateFeignDTO updateDto
    );

    @DeleteMapping("delete-event-by-eventId/{eventId}/{updatedBy}/{reason}")
    ResponseEntity<Object> deleteCalenderEventByEventId(
            @PathVariable("eventId") String eventId,
            @PathVariable("updatedBy") String updatedBy,
            @PathVariable("reason") String reason
    );
}
