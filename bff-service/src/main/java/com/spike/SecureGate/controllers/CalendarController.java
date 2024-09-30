package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.DTO.calendarDto.EventCreationFeignDTO;
import com.spike.SecureGate.DTO.calendarDto.EventCreationRequestDTO;
import com.spike.SecureGate.DTO.calendarDto.EventUpdateRequestDTO;
import com.spike.SecureGate.service.externalBlogService.BlogService;
import com.spike.SecureGate.service.externalCalendarService.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

    // CREATE A EVENT
    @Operation(
            summary = "Create a EVENT",
            description = "Any user can create a event"
    )
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> createEvent(
            @RequestBody EventCreationRequestDTO eventCreationRequestDTO
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> event = calendarService.createEvent(eventCreationRequestDTO,userName);
        logger.info("Finished creating new Blog");
        return event;
    }


    // CREATE A EVENT
    @Operation(
            summary = "Get events by date"
    )
    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getEventByDate(
            @PathVariable("date") String date
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new Blog");
        ResponseEntity<Object> event = calendarService.getEventByDate(date);
        logger.info("Finished creating new Blog");
        return event;
    }


    // CREATE A EVENT
    @Operation(
            summary = "Get events by week"
    )
    @GetMapping("/week")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getEventByWeek()
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new Blog");
        ResponseEntity<Object> event = calendarService.getEventByWeek();
        logger.info("Finished creating new Blog");
        return event;
    }


    // CREATE A EVENT
    @Operation(
            summary = "Get events by month"
    )
    @GetMapping("/month/{year}/{month}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getEventByMonth(
            @PathVariable("year") int year,
            @PathVariable("month") int month
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> event = calendarService.getEventByMonth(year,month);
        logger.info("Finished creating new Blog");
        return event;
    }



    // CREATE A EVENT
    @Operation(
            summary = "Create a EVENT",
            description = "Any user can create a event"
    )
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> updateEvent(
            @RequestBody EventUpdateRequestDTO eventUpdateRequestDTO
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> event = calendarService.updateEvent(eventUpdateRequestDTO,userName);
        logger.info("Finished creating new Blog");
        return event;
    }



    // CREATE A EVENT
    @Operation(
            summary = "Create a EVENT",
            description = "Any user can create a event"
    )
    @DeleteMapping("/delete/{eventId}/{reason}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> deleteEvent(
            @PathVariable("eventId") String eventId,
            @PathVariable("reason") String reason
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> event = calendarService.deleteEvent(eventId,userName,reason);
        logger.info("Finished creating new Blog");
        return event;
    }
}
