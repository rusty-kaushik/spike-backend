package com.spike.calender.service;


import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spike.calender.customException.FeignException;
import com.spike.calender.customMailBody.CustomMailBodyMessages;
import com.spike.calender.dto.CalenderEventDto;
import com.spike.calender.dto.CalenderEventUpdateDto;
import com.spike.calender.dto.CalenderResponseDto;
import com.spike.calender.dto.EmailDto;
import com.spike.calender.entity.CalenderEntity;
import com.spike.calender.feign.EmailFeign;
import com.spike.calender.repository.CalenderRepository;

@Component
public class CalenderServiceImpl implements CalenderService
{
	@Autowired
	private CalenderRepository calRepository;
	@Autowired
	private ModelMapper modelMpper;
	@Autowired
	private EmailFeign emailFeign;
	@Value("${my.spike.receipient}")
	private String receipient;
	@Autowired
	private CustomMailBodyMessages customMailBodyMessages;
	
	public CalenderResponseDto saveCalenderEvent(CalenderEventDto calenderDto) throws FeignException
	{
		
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
		try 
		{
			calRepository.save(calenderEntity);
			response = modelMpper.map(calenderEntity, CalenderResponseDto.class);
			
			callEmailFeign("Invitation to "+calenderDto.getEventName()+" - Join Us!",
					calenderDto.getSharedBy(), customMessage);
		}
		catch(Exception ex)
		{
			throw new FeignException("Email Service may be down; Please Check");
		}
		return response;
	}
	public List<CalenderResponseDto>  getCalenderEventByDate(String date)
	{
		LocalDate dateObj = LocalDate.parse(date);
		List<CalenderEntity> entityList = calRepository.findByDate(dateObj);
		
		List<CalenderResponseDto> response = new ArrayList<>();
		
		for(CalenderEntity entity : entityList)
		{
			response.add(modelMpper.map(entity, CalenderResponseDto.class));
		}
		
		return response;
	}
	public List<CalenderResponseDto>  getCalenderEventByWeek()
	{
		LocalDate today = LocalDate.now();
	    LocalDate monday = today.with(previousOrSame(MONDAY));
	    LocalDate sunday = today.with(nextOrSame(SUNDAY));
		List<CalenderEntity> entityList = calRepository.findByWeek(monday, sunday);

		List<CalenderResponseDto> response = new ArrayList<>();
		
		for(CalenderEntity entity : entityList)
		{
			response.add(modelMpper.map(entity, CalenderResponseDto.class));
		}
		
		return response;
	}
	public List<CalenderResponseDto> getCalenderEventByMonth(int year, int month)
	{
		
		//get desired year Month object
		YearMonth yearMonth = YearMonth.of( year, month );		
		
		//getting LocalDate object of 1st day of the desired year Month object
		LocalDate firstDayOfMonth = yearMonth.atDay( 1 );
		
		//getting LocalDate object of last day of the desired year Month object
		LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
		
		List<CalenderEntity> entityList = calRepository.findByMonth(firstDayOfMonth, lastDayOfMonth);

		
		List<CalenderResponseDto> response = new ArrayList<>();
		
		for(CalenderEntity entity : entityList)
		{
			response.add(modelMpper.map(entity, CalenderResponseDto.class));
		}
		
		return response;
	}
	public boolean cancelCalenderEventByEventId(String eventId, String updatedBy, String reason) throws FeignException
	{
		try
		{
			CalenderEntity entity = calRepository.findByEventIdAndStatus(UUID.fromString(eventId),true);
			entity.setStatus(false);
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(updatedBy);
			entity.setReason(reason);
			calRepository.save(entity);
			
			callEmailFeign("Cancellation of "+entity.getEventName(), entity.getSharedBy(),
					customMailBodyMessages.getCustomMessageForEventCancellation(entity.getEventName(), 
							entity.getStartingDate(), entity.getStartingTime(), entity.getReason()));
		}
		catch (Exception e) 
		{
			
			throw new FeignException("Email Service may be down; Please Check");
			
		}
		return true;
	}
	public CalenderResponseDto updateCalenderEventByEventId(CalenderEventUpdateDto updateDto) throws FeignException
	{
		CalenderEntity entity = null;
		try
		{
			entity = calRepository.findByEventIdAndStatus(UUID.fromString(updateDto.getEventId()),true);
			
			String customMessage = customMailBodyMessages.getCustomMessageForEventUpdation(entity.getEventName(), 
					entity.getStartingDate(), entity.getStartingTime(), entity.getLink(), 
					entity.getDescription(),LocalDate.parse(updateDto.getNewStartingDate().get()),
					LocalTime.parse(updateDto.getNewStartingTime().get()));
			
			entity.setUpdatedAt(LocalDateTime.now());
			if(!updateDto.getUpdatedBy().get().isEmpty())
				entity.setUpdatedBy(updateDto.getUpdatedBy().get());
			if(!updateDto.getColor().get().isEmpty())
				entity.setColor(updateDto.getColor().get());
			if(!updateDto.getDescription().get().isEmpty())
				entity.setDescription(updateDto.getDescription().get());
			if(!updateDto.getNewStartingDate().get().isEmpty())
				entity.setStartingDate(LocalDate.parse(updateDto.getNewStartingDate().get()));
			if(!updateDto.getNewendingDate().get().isEmpty())
				entity.setEndingDate(LocalDate.parse(updateDto.getNewendingDate().get()));
			if(!updateDto.getNewStartingTime().get().isEmpty())
				entity.setStartingTime(LocalTime.parse(updateDto.getNewStartingTime().get()));
			if(!updateDto.getNewEndingTime().get().isEmpty())
				entity.setEndingTime(LocalTime.parse(updateDto.getNewEndingTime().get()));
			
			calRepository.save(entity);
			
			callEmailFeign("Update on "+entity.getEventName(), entity.getSharedBy(), customMessage);		
		}
		catch (Exception e) 
		{
			
			throw new FeignException("Email Service may be down; Please Check");
			
			// TODO: handle exception
		}
		return modelMpper.map(entity, CalenderResponseDto.class);
	}
	
	/*============================================================================================================
		Used as helper method
		=========================================================================================================
	 */
	
	private void callEmailFeign(String heading, String[] sharedBy, String body)
	{
		EmailDto emailDto = new EmailDto();
		emailDto.setHeading(heading);		
		emailDto.setBody(body);
		emailDto.setRecipientMailId(receipient);
		emailDto.setSharedBy(sharedBy);
		
		emailFeign.sendMail(emailDto);
	}
}
