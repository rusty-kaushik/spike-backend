package com.spike.calender.customMailBody;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class CustomMailBodyMessages {
	
	public String getCustomMessageForEventCreation(String eventName, LocalDate startingDate, LocalTime startingTime, String link, String description)
	{
		return "\n\nHi Team,\n\nI hope this message finds you well! We are excited to announce that we are organizing an event "
			+eventName+" on "+startingDate+" and "+startingTime
			+" at Microsoft Teams. \n\nEvent Details:\n\nDate: "+startingDate+"\nTime: "
			+startingTime+"\nPlateform: "+link+"\nAgenda: "+description;
	}
	public String getCustomMessageForEventCancellation(String eventName, LocalDate startingDate, LocalTime startingTime, String reason)
	{
		return "\n\nHi Teams,\n\n" +
	            "I hope this message finds you well. I regret to inform you that due to "+reason+", we have decided to cancel the "+eventName+" originally scheduled for "+startingDate+" and "+startingTime+" at Microsoft Teams.\n\n" +
	            "We value your support and hope to keep you updated about future events. Please feel free to reach out if you have any questions or need further information.\n\n" +
	            "Thank you for your understanding.\n\n" +
	            "Best regards,\n" +
	            "Team Spike\n" +
	            "In2It Technologies Pvt Ltd, Greater Noida\n" +
	            "sumit.mishra@in2ittech.com\n" +
	            "8235691034";
	}
	public String getCustomMessageForEventUpdation(String eventName, LocalDate startingDate, LocalTime startingTime, String link, String description, LocalDate newStartingDate, LocalTime newStartingTime)
	{
		return "\n\nHi Teams,\n\n" +
	            "I hope this message finds you well. We are reaching out to provide you with an important update regarding the "+eventName+" originally scheduled for "+startingDate+" and "+startingTime+" at Microsoft Teams.\n\n" +
	            "New Details:\n\n" +
	            "New Date: "+newStartingDate+"\n" +
	            "New Time: "+newStartingTime+"\n" +
	            "New Platform: Microsoft Teams\n" +
	            "Additional Information: "+description+"\n\n" +
	            "We appreciate your understanding and flexibility as we make these adjustments. If you have any questions or need further information, please don’t hesitate to reach out.\n\n" +
	            "Thank you for your continued support, and we look forward to seeing you at the event!\n\n" +
	            "Best regards,\n" +
	            "Team Spike\n" +
	            "In2It Technologies Pvt Ltd\n" +
	            "Greater Noida\n" +
	            "sumitkumarmishra8235@gmail.com\n" +
	            "8235691034";
	}
	public String getCustomMessageForEventScheduler(String eventName, LocalDate startingDate, LocalTime startingTime, String link, String description)
	{
		return "\n\nHi Team,\n\nI hope this message finds you well! As you know that we organized an event "
			+eventName+" on "+startingDate+" and "+startingTime
			+" at Microsoft Teams.\n\n So, be ready event "+eventName+" will start 10 minutes later. \n\nEvent Details:\n\nDate: "+startingDate+"\nTime: "
			+startingTime+"\nPlateform: "+link+"\nAgenda: "+description;
	}
}
