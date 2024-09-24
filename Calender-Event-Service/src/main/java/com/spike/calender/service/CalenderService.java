package com.spike.calender.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spike.calender.customException.FeignException;
import com.spike.calender.dto.CalenderEventDto;
import com.spike.calender.dto.CalenderEventUpdateDto;
import com.spike.calender.dto.CalenderResponseDto;

@Service
public interface CalenderService {
	public CalenderResponseDto saveCalenderEvent(CalenderEventDto calenderDto) throws FeignException;
	public List<CalenderResponseDto>  getCalenderEventByDate(String date);
	public List<CalenderResponseDto>  getCalenderEventByWeek();
	public List<CalenderResponseDto> getCalenderEventByMonth(int year, int month);
	public boolean cancelCalenderEventByEventId(String eventId, String updatedBy, String reason) throws FeignException;
	public CalenderResponseDto updateCalenderEventByEventId(CalenderEventUpdateDto updateDto) throws FeignException;
	
}
