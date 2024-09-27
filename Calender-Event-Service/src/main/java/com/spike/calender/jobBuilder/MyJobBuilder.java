package com.spike.calender.jobBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spike.calender.customMailBody.CustomMailBodyMessages;
import com.spike.calender.dto.CalenderEventDto;
import com.spike.calender.feign.callClass.FeignCallClass;

@Component
public class MyJobBuilder {
	@Value("${my.spike.receipient}")
	private String receipient;
	@Autowired
	private CustomMailBodyMessages customMailBodyMessages;
	
    public JobDetail buildJobDetail(String eventName, LocalDate startingDate, LocalTime startingTime, String link, String description, String sharedBy[]) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("recipientMailId", receipient);
        jobDataMap.put("heading", "Be ready for "+eventName);
        jobDataMap.put("body", customMailBodyMessages.getCustomMessageForEventScheduler(eventName, startingDate, startingTime, link, description));
        jobDataMap.put("sharedBy", sharedBy);

        return JobBuilder.newJob(FeignCallClass.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

}
