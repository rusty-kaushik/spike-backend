package com.spike.calender.jobTriggerer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyJobTriggerer {
	 public Trigger buildJobTrigger(JobDetail jobDetail, LocalDate startingDate,LocalTime startingTime) {
	        return TriggerBuilder.newTrigger()
	                .forJob(jobDetail)
	                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
	                .withDescription("Send Email Trigger")
	                .startAt(Date.from((LocalDateTime.of(startingDate, startingTime).minusMinutes(10)).atZone(ZoneId.systemDefault()).toInstant()))
	                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
	                .build();
	    }
}