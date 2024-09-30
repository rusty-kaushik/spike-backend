package com.spike.SecureGate.service.externalCalendarService;

import com.spike.SecureGate.DTO.blogDto.BlogCreationFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.DTO.calendarDto.EventCreationFeignDTO;
import com.spike.SecureGate.DTO.calendarDto.EventCreationRequestDTO;
import com.spike.SecureGate.DTO.calendarDto.EventUpdateFeignDTO;
import com.spike.SecureGate.DTO.calendarDto.EventUpdateRequestDTO;
import com.spike.SecureGate.Validations.BlogValidators;
import com.spike.SecureGate.exceptions.BlogNotFoundException;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.BlogFeignClient;
import com.spike.SecureGate.feignClients.CalendarFeignClient;
import com.spike.SecureGate.helper.BlogHelper;
import com.spike.SecureGate.helper.CalendarHelper;
import com.spike.SecureGate.service.externalBlogService.BlogService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    private static final Logger logger = LoggerFactory.getLogger(CalendarServiceImpl.class);

    @Autowired
    private CalendarFeignClient calendarFeignClient;

    @Autowired
    private CalendarHelper calendarHelper;

    @Override
    public ResponseEntity<Object> createEvent(EventCreationRequestDTO eventCreationRequestDTO, String userName) {
        try {
            EventCreationFeignDTO eventCreationFeignDTO = calendarHelper.eventCreationDtoTOFeignDto(eventCreationRequestDTO, userName);
            return calendarFeignClient.createCalenderEvent(eventCreationFeignDTO);
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while creating a blog: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getEventByDate(String date) {
        try {
            return calendarFeignClient.getCalenderEventByDate(date);
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while creating a blog: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getEventByWeek() {
        try {
            return calendarFeignClient.getCalenderEventByWeek();
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while creating a blog: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getEventByMonth(int year, int month) {
        try {
            return calendarFeignClient.getCalenderEventByMonth(year,month);
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while creating a blog: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateEvent(EventUpdateRequestDTO eventUpdateRequestDTO, String userName) {
        try {
            EventUpdateFeignDTO eventUpdateFeignDTO = calendarHelper.eventUpdateDtoTOFeignDto(eventUpdateRequestDTO, userName);
            return calendarFeignClient.updateCalenderEventByEventId(eventUpdateFeignDTO);
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while creating a blog: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteEvent(String eventId, String userName, String reason) {
        try {
            return calendarFeignClient.deleteCalenderEventByEventId(eventId, userName, reason);
        }  catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while creating a blog: " + e.getMessage());
        }
    }


}
