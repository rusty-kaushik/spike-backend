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
import org.springframework.stereotype.Component;

import com.spike.calender.customException.FeignException;
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
		
		try 
		{
			calRepository.save(calenderEntity);
			response = modelMpper.map(calenderEntity, CalenderResponseDto.class);
			String customMessage = "\n\nHi Team,\n\nI hope this message finds you well! I’m excited to announce that we are organizing an event "+calenderDto.getEventName()+" on "+calenderDto.getStartingDate()+" and "+calenderDto.getStartingTime()+" at Microsoft Teams. \n\nEvent Details:\n\nDate: "+calenderDto.getStartingDate()+"\nTime: "+calenderDto.getStartingTime()+"\nPlateform: "+calenderDto.getLink()+"\nAgenda: "+calenderDto.getDescription();
			callEmailFeign("Invitation to "+calenderDto.getEventName()+" - Join Us!", calenderDto.getSharedBy(),customMessage);
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
			String customMessage = "\n\nHi Teams,\n\n" +
	                  "I hope this message finds you well. I regret to inform you that due to "+reason+", we have decided to cancel the "+entity.getEventName()+" originally scheduled for "+entity.getStartingDate()+" and "+entity.getStartingTime()+" at Microsoft Teams.\n\n" +
	                  "We value your support and hope to keep you updated about future events. Please feel free to reach out if you have any questions or need further information.\n\n" +
	                  "Thank you for your understanding.\n\n" +
	                  "Best regards,\n" +
	                  "Team Spike\n" +
	                  "In2It Technologies Pvt Ltd, Greater Noida\n" +
	                  "sumitkumarmishra8235@gmail.com\n" +
	                  "8235691034";
			callEmailFeign("Cancellation of "+entity.getEventName(), entity.getSharedBy(),customMessage);
		}
		catch (Exception e) 
		{
			
			throw new FeignException("Email Service may be down; Please Check");
			
			// TODO: handle exception
		}
		return true;
	}
	public CalenderResponseDto updateCalenderEventByEventId(CalenderEventUpdateDto updateDto) throws FeignException
	{
		CalenderEntity entity = null;
		try
		{
			entity = calRepository.findByEventIdAndStatus(UUID.fromString(updateDto.getEventId()),true);
			
			String customMessage = "\n\nHi Teams,\n\n" +
                    "I hope this message finds you well. We are reaching out to provide you with an important update regarding the "+entity.getEventName()+" originally scheduled for "+entity.getStartingDate()+" and "+entity.getStartingTime()+" at Microsoft Teams.\n\n" +
                    "New Details:\n\n" +
                    "New Date: "+updateDto.getNewStartingDate().get()+"\n" +
                    "New Time: "+updateDto.getNewStartingTime().get()+"\n" +
                    "New Platform: Microsoft Teams\n" +
                    "Additional Information: "+entity.getDescription()+"\n\n" +
                    "We appreciate your understanding and flexibility as we make these adjustments. If you have any questions or need further information, please don’t hesitate to reach out.\n\n" +
                    "Thank you for your continued support, and we look forward to seeing you at the event!\n\n" +
                    "Best regards,\n" +
                    "Team Spike\n" +
                    "In2It Technologies Pvt Ltd\n" +
                    "Greater Noida\n" +
                    "sumitkumarmishra8235@gmail.com\n" +
                    "8235691034";
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
			
			callEmailFeign("Update on "+entity.getEventName(), entity.getSharedBy(),customMessage);		
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
	
	private void callEmailFeign(String subject, String[] sharedBy, String message)
	{
		EmailDto emailDto = new EmailDto();
		emailDto.setSubject(subject);		
		emailDto.setMessage(message);
		emailDto.setTo("sumit.mishra@in2ittech.com");
		emailDto.setCcs(sharedBy);
		
		emailFeign.sendMail(emailDto);
	}
}
