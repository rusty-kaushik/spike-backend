package com.spike.calender.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spike.calender.dto.EmailDto;

@FeignClient(name = "emailClient",  url = "${spike.service.email_service}")
public interface EmailFeign {

	@PostMapping(path = "sendEmail/without/attachment")
	public String sendMail(@RequestBody EmailDto withoutAttachmentMailDto);
}
