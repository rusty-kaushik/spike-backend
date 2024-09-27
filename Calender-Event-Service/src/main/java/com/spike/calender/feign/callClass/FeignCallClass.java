package com.spike.calender.feign.callClass;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.spike.calender.dto.EmailDto;
import com.spike.calender.feign.EmailFeign;

@Component
public class FeignCallClass extends QuartzJobBean{

	@Autowired
	private EmailFeign emailFeign;
	@Value("${my.spike.receipient}")
	private String receipient;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
        String heading = jobDataMap.getString("heading");
        String body = jobDataMap.getString("body");
        String [] sharedBy = (String[]) jobDataMap.get("sharedBy");
        
        callEmailFeign(heading, sharedBy, body)	;	
	}
	
	//Feign caller method
	public void callEmailFeign(String heading, String[] sharedBy, String body)
	{
		EmailDto emailDto = new EmailDto();
		emailDto.setHeading(heading);		
		emailDto.setBody(body);
		emailDto.setRecipientMailId(receipient);
		emailDto.setSharedBy(sharedBy);
		
		emailFeign.sendMail(emailDto);
	}
	
}
