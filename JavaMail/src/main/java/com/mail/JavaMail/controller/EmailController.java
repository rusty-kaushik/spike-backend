package com.mail.JavaMail.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mail.JavaMail.customExceptions.DirectoryException;
import com.mail.JavaMail.customExceptions.FileHandlingException;
import com.mail.JavaMail.customExceptions.MailNotSentException;
import com.mail.JavaMail.dto.EMailDto;
import com.mail.JavaMail.service.EmailService;

import jakarta.mail.MessagingException;



@RestController
@RequestMapping("/spike/mail")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(path = "sendEmail/with/attachment", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String sendMail(@RequestPart(value = "attachment" ,required=false) List<MultipartFile> attachments, EMailDto withoutAttachmentMailDto)  throws MailNotSentException, MessagingException, DirectoryException, FileHandlingException, IOException
	{
		return emailService.sendMail(attachments, withoutAttachmentMailDto);
		
	}
	@PostMapping(path = "sendEmail/without/attachment")
	public String sendMail(@RequestBody EMailDto withoutAttachmentMailDto)  throws MailNotSentException, MessagingException, DirectoryException, FileHandlingException, IOException
	{
		return emailService.sendMail(withoutAttachmentMailDto);
		
	}
	
}
