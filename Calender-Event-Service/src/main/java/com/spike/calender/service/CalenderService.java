package com.spike.calender.service;

import java.time.LocalDate;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.spike.calender.customException.DataNotFoundException;
import com.spike.calender.customException.FeignException;
import com.spike.calender.customException.InvalidException;
import com.spike.calender.customException.WrongDateAndTimeException;
import com.spike.calender.dto.CalenderEventDto;
import com.spike.calender.dto.CalenderEventUpdateDto;
import com.spike.calender.dto.CalenderResponseDto;

@Service
public interface CalenderService {
	public CalenderResponseDto saveCalenderEvent(CalenderEventDto calenderDto) throws FeignException, SchedulerException, WrongDateAndTimeException;
	public List<CalenderResponseDto>  getCalenderEventByDate(String date) throws DataNotFoundException;
	public List<CalenderResponseDto>  getCalenderEventByWeek() throws DataNotFoundException;
	public List<CalenderResponseDto> getCalenderEventByMonth(int year, int month) throws DataNotFoundException;
	public boolean cancelCalenderEventByEventId(String eventId, String updatedBy, String reason) throws FeignException, DataNotFoundException;
	public CalenderResponseDto updateCalenderEventByEventId(CalenderEventUpdateDto updateDto) throws FeignException, DataNotFoundException, WrongDateAndTimeException, InvalidException, SchedulerException;
	
}
