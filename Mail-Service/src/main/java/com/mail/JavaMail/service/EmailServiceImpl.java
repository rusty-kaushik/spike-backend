package com.mail.JavaMail.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mail.JavaMail.customExceptions.DirectoryException;
import com.mail.JavaMail.customExceptions.FileHandlingException;
import com.mail.JavaMail.customExceptions.MailNotSentException;
import com.mail.JavaMail.dto.EMailDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService{

	@Autowired
	private EmailServiceImplHelper serviceHelper;
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	
	@Override
	public String sendMail( List<MultipartFile> attachments, EMailDto withoutAttachmentMailDto) throws MailNotSentException, MessagingException, DirectoryException, FileHandlingException, IOException
	{	        
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setCc(withoutAttachmentMailDto.getSharedBy());
		helper.setTo(withoutAttachmentMailDto.getRecipientMailId());
		helper.setSubject(withoutAttachmentMailDto.getHeading());
		helper.setText(withoutAttachmentMailDto.getBody());
		
		//Saving Attachments in File System
		Map<String, List<String>> attachmentMap = serviceHelper.saveAttachments(attachments);
		
		//Taking iterator from the Map to iterate over the map
		List<String> attachmentOldNames = attachmentMap.get("attachmentOldNames");
		List<String> attachmentNewNames = attachmentMap.get("attachmentNewNames");
		List<String> attachmentPaths = attachmentMap.get("attachmentPaths");

		int size = attachmentOldNames.size();
		for(int index = 0; index<size;index++)
		{
			FileSystemResource file = new FileSystemResource(new File(attachmentPaths.get(index)));
			helper.addAttachment(attachmentOldNames.get(index), file);	
			
		}

		javaMailSender.send(mimeMessage);

		serviceHelper.saveMail(attachmentOldNames, attachmentNewNames, attachmentPaths, withoutAttachmentMailDto);
	    
        return "Success";
	}
	
	
	public String sendMail( EMailDto withoutAttachmentMailDto) throws MessagingException
	{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setCc(withoutAttachmentMailDto.getSharedBy());
		helper.setTo(withoutAttachmentMailDto.getRecipientMailId());
		helper.setSubject(withoutAttachmentMailDto.getHeading());
		helper.setText(withoutAttachmentMailDto.getBody());

		javaMailSender.send(mimeMessage);

		serviceHelper.saveMail(withoutAttachmentMailDto);
	    
        return "Success";
	}

}
