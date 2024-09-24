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
//		for(MultipartFile attachment : attachments)
//		{
		helper.setCc(withoutAttachmentMailDto.getCcs());
		helper.setTo(withoutAttachmentMailDto.getTo());
		helper.setSubject(withoutAttachmentMailDto.getSubject());
		helper.setText(withoutAttachmentMailDto.getMessage());
		
		//Saving Attachments in File System
		Map<String, List<String>> attachmentMap = serviceHelper.saveAttachments(attachments);
		
		//Taking iterator from the Map to iterate over the map
		List<String> attachmentOldNames = attachmentMap.get("attachmentOldNames");
		List<String> attachmentNewNames = attachmentMap.get("attachmentNewNames");
		List<String> attachmentPaths = attachmentMap.get("attachmentPaths");
		
		
		//Taking iterator from the Map to iterate over the map
//		Iterator<Map.Entry<String, String>> iterator = attachmwentMap.entrySet().iterator();
//		
//		//Iterating over the map
//		while(iterator.hasNext())
//		{
//			Map.Entry<String, String> entry = iterator.next();
//			attachmentNames.add(entry.getKey());
//			attachmentPaths.add(entry.getValue());
//		}
		int size = attachmentOldNames.size();
		for(int index = 0; index<size;index++)
		{
			FileSystemResource file = new FileSystemResource(new File(attachmentPaths.get(index)));
			helper.addAttachment(attachmentOldNames.get(index), file);	
			
		}

		javaMailSender.send(mimeMessage);
//	}
//	    saveMail(attachment, withoutAttachmentMailDto);
		serviceHelper.saveMail(attachmentOldNames, attachmentNewNames, attachmentPaths, withoutAttachmentMailDto);
        System.out.println("All Emails has been Sent Successfully");
	    
        return "Success";
	}
	
	
	public String sendMail( EMailDto withoutAttachmentMailDto) throws MessagingException
	{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//		for(MultipartFile attachment : attachments)
//		{
		helper.setCc(withoutAttachmentMailDto.getCcs());
		helper.setTo(withoutAttachmentMailDto.getTo());
		helper.setSubject(withoutAttachmentMailDto.getSubject());
		helper.setText(withoutAttachmentMailDto.getMessage());

		javaMailSender.send(mimeMessage);
//	}
//	    saveMail(attachment, withoutAttachmentMailDto);
		serviceHelper.saveMail(withoutAttachmentMailDto);
        System.out.println("All Emails has been Sent Successfully");
	    
        return "Success";
	}
	
//	public List<ResponseDto> getMailByMailId(String emailId) throws MailNotSentException
//	{	        
//		List<EmailHistory> mails = serviceHelper.findByEmailIDAsc(emailId);
//		List<ResponseDto> responseMails = new ArrayList<>();
//		for(EmailHistory mail : mails)
//		{
//			responseMails.add(serviceHelper.entityToDto(mail));
//		}
//        return responseMails;
//	}
	
}
