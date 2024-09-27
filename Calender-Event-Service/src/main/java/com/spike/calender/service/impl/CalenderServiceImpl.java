package com.spike.calender.service.impl;


import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spike.calender.customException.DataNotFoundException;
import com.spike.calender.customException.FeignException;
import com.spike.calender.customException.InvalidException;
import com.spike.calender.customException.WrongDateAndTimeException;
import com.spike.calender.customMailBody.CustomMailBodyMessages;
import com.spike.calender.dto.CalenderEventDto;
import com.spike.calender.dto.CalenderEventUpdateDto;
import com.spike.calender.dto.CalenderResponseDto;
import com.spike.calender.dto.EmailDto;
import com.spike.calender.entity.CalenderEntity;
import com.spike.calender.feign.EmailFeign;
import com.spike.calender.feign.callClass.FeignCallClass;
import com.spike.calender.jobBuilder.MyJobBuilder;
import com.spike.calender.jobTriggerer.MyJobTriggerer;
import com.spike.calender.repository.CalenderRepository;
import com.spike.calender.service.CalenderService;

@Component
public class CalenderServiceImpl implements CalenderService
{
	@Autowired
	private CalenderRepository calRepository;
	@Autowired
	private ModelMapper modelMpper;
	@Autowired
	private CustomMailBodyMessages customMailBodyMessages;
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private MyJobBuilder myJobBuilder;
	@Autowired
	private MyJobTriggerer myJobTriggerer;
	@Autowired
	private FeignCallClass feignCall;
	
	
	public CalenderResponseDto saveCalenderEvent(CalenderEventDto calenderDto) throws FeignException, SchedulerException, WrongDateAndTimeException
	{		
		if(LocalDate.parse(calenderDto.getStartingDate()).isBefore(LocalDate.now()) ||
			(!LocalDate.parse(calenderDto.getStartingDate()).isBefore(LocalDate.now()) && LocalTime.parse(calenderDto.getStartingTime()).isBefore(LocalTime.now())) 
			)
		{
			 throw new WrongDateAndTimeException("Check starting Date or starting Time which you entered");
        }
		if(LocalDate.parse(calenderDto.getEndingDate()).isBefore(LocalDate.parse(calenderDto.getStartingDate())) ||
			(!LocalDate.parse(calenderDto.getEndingDate()).isBefore(LocalDate.parse(calenderDto.getStartingDate())) && LocalTime.parse(calenderDto.getEndingTime()).isBefore(LocalTime.now()))
			)
		{
			throw new WrongDateAndTimeException("Check ending Date or ending Time which you entered");
		}
		JobDetail jobDetail = myJobBuilder.buildJobDetail(calenderDto.getEventName(),LocalDate.parse(calenderDto.getStartingDate()),LocalTime.parse(calenderDto.getStartingTime()),calenderDto.getLink(),calenderDto.getDescription(), calenderDto.getSharedBy());
        Trigger trigger = myJobTriggerer.buildJobTrigger(jobDetail, LocalDate.parse(calenderDto.getStartingDate()), LocalTime.parse(calenderDto.getStartingTime()));
        scheduler.scheduleJob(jobDetail, trigger);
		
		CalenderEntity calenderEntity = new CalenderEntity();
		calenderEntity.setColor(calenderDto.getColor());
		calenderEntity.setDescription(calenderDto.getDescription());
		calenderEntity.setEndingDate(LocalDate.parse(calenderDto.getEndingDate()));
		calenderEntity.setStartingDate(LocalDate.parse(calenderDto.getStartingDate()));
		calenderEntity.setEndingTime(LocalTime.parse(calenderDto.getEndingTime()));
		calenderEntity.setStartingTime(LocalTime.parse(calenderDto.getStartingTime()));
		calenderEntity.setEventName(calenderDto.getEventName());
		calenderEntity.setSharedBy(calenderDto.getSharedBy());
		calenderEntity.setCreatedAt(LocalDateTime.now());
		calenderEntity.setCreatedBy(calenderDto.getCreatedBy());
		calenderEntity.setLink(calenderDto.getLink());
		calenderEntity.setStatus(true);
		
		CalenderResponseDto response = null;
		String customMessage = customMailBodyMessages.getCustomMessageForEventCreation(
				calenderEntity.getEventName(), calenderEntity.getStartingDate(), 
				calenderEntity.getStartingTime(), calenderEntity.getLink(), 
				calenderEntity.getDescription());
		
			calRepository.save(calenderEntity);
			response = modelMpper.map(calenderEntity, CalenderResponseDto.class);
		try 
		{
			feignCall.callEmailFeign("Invitation to "+calenderDto.getEventName()+" - Join Us!",
					calenderDto.getSharedBy(), customMessage);
		}
		catch(Exception ex)
		{
			throw new FeignException("Email Service may be down; Please Check");
		}
		return response;
	}
	
	public List<CalenderResponseDto>  getCalenderEventByDate(String date)throws DataNotFoundException
	{
		List<CalenderResponseDto> response = new ArrayList<>();
		 try
		 {
			List<CalenderEntity> entityList = calRepository.findByDate(LocalDate.parse(date)).get();
			
			
			
			for(CalenderEntity entity : entityList)
			{
				response.add(modelMpper.map(entity, CalenderResponseDto.class));
			}
		 }
		 catch (Exception e) {
			throw new DataNotFoundException("Data not available");
		}
		return response;
	}
	public List<CalenderResponseDto>  getCalenderEventByWeek()throws DataNotFoundException
	{
		LocalDate today = LocalDate.now();
	    LocalDate monday = today.with(previousOrSame(MONDAY));
	    LocalDate sunday = today.with(nextOrSame(SUNDAY));
	    List<CalenderResponseDto> response = new ArrayList<>();
	    try
	    {
			List<CalenderEntity> entityList = calRepository.findByWeek(monday, sunday).get();
	
			
			
			for(CalenderEntity entity : entityList)
			{
				response.add(modelMpper.map(entity, CalenderResponseDto.class));
			}
	    }
		catch (Exception e) {
			throw new DataNotFoundException("Data not available");
		}
		
		return response;
	}
	public List<CalenderResponseDto> getCalenderEventByMonth(int year, int month) throws DataNotFoundException
	{
		
		//get desired year Month object
		YearMonth yearMonth = YearMonth.of( year, month );		
		
		//getting LocalDate object of 1st day of the desired year Month object
		LocalDate firstDayOfMonth = yearMonth.atDay( 1 );
		
		//getting LocalDate object of last day of the desired year Month object
		LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
		List<CalenderResponseDto> response = new ArrayList<>();
		
		try 
		{
		List<CalenderEntity> entityList = calRepository.findByMonth(firstDayOfMonth, lastDayOfMonth).get();

		
		
		
			for(CalenderEntity entity : entityList)
			{
				response.add(modelMpper.map(entity, CalenderResponseDto.class));
			}
		}
		catch (Exception e) {
			throw new DataNotFoundException("Data not available");
		}
		return response;
	}
	public boolean cancelCalenderEventByEventId(String eventId, String updatedBy, String reason) throws FeignException, DataNotFoundException
	{
		CalenderEntity entity =null;
		try
		{
			entity = calRepository.findByEventIdAndStatus(UUID.fromString(eventId),true).get();
			entity.setStatus(false);
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(updatedBy);
			entity.setReason(reason);
			calRepository.save(entity);
		 }
		 catch (Exception e) {
			throw new DataNotFoundException("Data not available");
		}
		try
		{
			feignCall.callEmailFeign("Cancellation of "+entity.getEventName(), entity.getSharedBy(),
					customMailBodyMessages.getCustomMessageForEventCancellation(entity.getEventName(), 
							entity.getStartingDate(), entity.getStartingTime(), entity.getReason()));
		}
		catch (Exception e) 
		{
				throw new FeignException("Email Service may be down; Please Check");
			
		}
		return true;
	}
	public CalenderResponseDto updateCalenderEventByEventId(CalenderEventUpdateDto updateDto) throws FeignException, DataNotFoundException, WrongDateAndTimeException, InvalidException, SchedulerException
	{
		try
		{
			if(LocalDate.parse(updateDto.getNewStartingDate()).isBefore(LocalDate.now()) ||
					(!LocalDate.parse(updateDto.getNewStartingDate()).isBefore(LocalDate.now()) && LocalTime.parse(updateDto.getNewStartingTime()).isBefore(LocalTime.now())) 
					)
				{
					 throw new WrongDateAndTimeException("Check starting Date or starting Time which you entered");
		        }
			if(LocalDate.parse(updateDto.getNewEndingDate()).isBefore(LocalDate.parse(updateDto.getNewStartingDate())) ||
					(!LocalDate.parse(updateDto.getNewEndingDate()).isBefore(LocalDate.parse(updateDto.getNewStartingDate())) && LocalTime.parse(updateDto.getNewEndingTime()).isBefore(LocalTime.now()))
					)
			{
					throw new WrongDateAndTimeException("Check ending Date or ending Time which you entered");
			}
		}
		catch (NullPointerException | DateTimeParseException  e) {
			if(e instanceof NullPointerException)
				throw new InvalidException("Enter values for new Event's Date and Time");
			if(e instanceof DateTimeParseException)
				throw new WrongDateAndTimeException("Enter valid values for Dates and Times");
		}
		
		CalenderEntity entity = null;
		String customMessage = "";
		try
		{
			entity = calRepository.findByEventIdAndStatus(UUID.fromString(updateDto.getEventId()),true).get();
		}
		 catch (Exception e) {
			throw new DataNotFoundException("Data not available");
		}
			customMessage = customMailBodyMessages.getCustomMessageForEventUpdation(entity.getEventName(), 
					entity.getStartingDate(), entity.getStartingTime(), entity.getLink(), 
					entity.getDescription(),LocalDate.parse(updateDto.getNewStartingDate()),
					LocalTime.parse(updateDto.getNewStartingTime()));
			
			entity.setUpdatedAt(LocalDateTime.now());
			if(updateDto.getUpdatedBy()!=null)
				entity.setUpdatedBy(updateDto.getUpdatedBy());
			if(updateDto.getColor()!=null)
				entity.setColor(updateDto.getColor());
			if(updateDto.getDescription()!=null)
				entity.setDescription(updateDto.getDescription());
			if(updateDto.getNewStartingDate()!=null)
			{
				if(LocalDate.parse(updateDto.getNewStartingDate()).isBefore(LocalDate.now()))
					entity.setStartingDate(LocalDate.parse(updateDto.getNewStartingDate()));
				
			}
			if(updateDto.getNewEndingDate()!=null)
			{
				if(LocalDate.parse(updateDto.getNewEndingDate()).isBefore(LocalDate.parse(updateDto.getNewStartingDate())))
					entity.setEndingDate(LocalDate.parse(updateDto.getNewEndingDate()));
				
			}
			if(updateDto.getNewEndingTime()!=null)
			{
				if(LocalTime.parse(updateDto.getNewEndingTime()).isBefore(LocalTime.parse(updateDto.getNewStartingTime())))
					entity.setEndingTime(LocalTime.parse(updateDto.getNewEndingTime()));
				
			}
			if(updateDto.getNewStartingTime()!=null)
			{
				if(LocalTime.parse(updateDto.getNewStartingTime()).isBefore(LocalTime.now()))
					entity.setStartingTime(LocalTime.parse(updateDto.getNewStartingTime()));
				
			}
			
			calRepository.save(entity);
			
			JobDetail jobDetail = myJobBuilder.buildJobDetail(entity.getEventName(),entity.getStartingDate(),entity.getStartingTime(),entity.getLink(),entity.getDescription(), entity.getSharedBy());
	        Trigger trigger = myJobTriggerer.buildJobTrigger(jobDetail, entity.getStartingDate(), entity.getStartingTime());
	        scheduler.scheduleJob(jobDetail, trigger);
		 
		
		try
		{
			feignCall.callEmailFeign("Update on "+entity.getEventName(), entity.getSharedBy(), customMessage);		
		}
		catch (Exception e) 
		{
			
			throw new FeignException("Email Service may be down; Please Check");

		}
		return modelMpper.map(entity, CalenderResponseDto.class);
	}
	
	
}
