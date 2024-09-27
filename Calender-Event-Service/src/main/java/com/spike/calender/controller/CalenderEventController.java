package com.spike.calender.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spike.calender.customException.DataNotFoundException;
import com.spike.calender.customException.FeignException;
import com.spike.calender.customException.InvalidException;
import com.spike.calender.customException.WrongDateAndTimeException;
import com.spike.calender.dto.CalenderEventDto;
import com.spike.calender.dto.CalenderEventUpdateDto;
import com.spike.calender.dto.CalenderResponseDto;
import com.spike.calender.response.ResponseHandler;
import com.spike.calender.service.CalenderService;

@RestController
@RequestMapping("spike/calender")
public class CalenderEventController {

	@Autowired
	private Locale locale;
	
	@Autowired
	private CalenderService calService;
	
	@PostMapping("create-event")
	public ResponseEntity<ResponseHandler<?>> createCalenderEvent(@RequestBody CalenderEventDto calenderDto) throws FeignException, SchedulerException, WrongDateAndTimeException
	{
		
		return ResponseEntity.ok(new ResponseHandler<CalenderResponseDto>(calService.saveCalenderEvent(calenderDto), "Data Persisted successfully", HttpStatus.OK, HttpStatus.OK.value()));
		
	}
	
	@GetMapping("get-event-by-date/{date}")
	public ResponseEntity<ResponseHandler<List<CalenderResponseDto>>> getCalenderEventByDate(@PathVariable("date") String date) throws DataNotFoundException
	{
    
		return ResponseEntity.ok(new ResponseHandler<>(calService.getCalenderEventByDate(date), "Data retrieved successfully", HttpStatus.OK, HttpStatus.OK.value()));
				
				
	}
	
	@GetMapping("get-event-by-week")
	public ResponseEntity<ResponseHandler<List<CalenderResponseDto>>> getCalenderEventByWeek() throws DataNotFoundException
	{
		return ResponseEntity.ok(new ResponseHandler<>(calService.getCalenderEventByWeek(), "Data retrieved successfully", HttpStatus.OK, HttpStatus.OK.value()));
	}
	@GetMapping("get-event-by-month/{year}/{month}")
	public ResponseEntity<ResponseHandler<List<CalenderResponseDto>>> getCalenderEventByMonth(@PathVariable("year") int year, @PathVariable("month") int month) throws DataNotFoundException
	{
		
		return ResponseEntity.ok(new ResponseHandler<>(calService.getCalenderEventByMonth(year, month), "Data retrieved successfully", HttpStatus.OK, HttpStatus.OK.value()));
	}
	@PutMapping("update-event-by-enventId")
	public  ResponseEntity<ResponseHandler<CalenderResponseDto>> updateCalenderEventByEventId(@RequestBody CalenderEventUpdateDto updateDto) throws FeignException, DataNotFoundException, WrongDateAndTimeException, InvalidException, SchedulerException
	{
		
		return ResponseEntity.ok(new ResponseHandler<>(calService.updateCalenderEventByEventId(updateDto), "Event Updated Successfully", HttpStatus.OK, HttpStatus.OK.value()));
	}
	@DeleteMapping("delete-event-by-eventId/{eventId}/{updatedBy}/{reason}")
	public ResponseEntity<ResponseHandler<Boolean>> deleteCalenderEventByEventId(@PathVariable("eventId") String eventId, @PathVariable("updatedBy") String updatedBy, @PathVariable("reason") String reason) throws FeignException, DataNotFoundException
	{
		
		return ResponseEntity.ok(new ResponseHandler<>(calService.cancelCalenderEventByEventId(eventId, updatedBy,reason), "Event Deleted Successfully", HttpStatus.OK, HttpStatus.OK.value()));
	}
	
	
}
