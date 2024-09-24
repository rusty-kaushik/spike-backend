package com.mail.JavaMail.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mail.JavaMail.customExceptions.DirectoryException;
import com.mail.JavaMail.customExceptions.FileHandlingException;
import com.mail.JavaMail.dto.EMailDto;
import com.mail.JavaMail.entity.EmailHistory;
import com.mail.JavaMail.repository.EmailRepository;

@Component
public class EmailServiceImplHelper {
	
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Value("${spring.mail.username:}")
	private String senderMail;
	
	@Value("${my.dir.path}")
	private String dirPath;
	

	public void saveMail(List<String> attachmentOldNames, List<String> attachmentNewNames, List<String> attachmentPaths, EMailDto withoutAttachmentMailDto) throws DirectoryException, FileHandlingException
	{
		
		EmailHistory emailHistoryEntity = new EmailHistory();
	
		emailHistoryEntity.setSender(senderMail);
		emailHistoryEntity.setCcs(withoutAttachmentMailDto.getCcs());
		emailHistoryEntity.setRecipient(withoutAttachmentMailDto.getTo());
		emailHistoryEntity.setMessage(withoutAttachmentMailDto.getMessage());
		emailHistoryEntity.setSubject(withoutAttachmentMailDto.getSubject());
		emailHistoryEntity.setCreatedAt(LocalDateTime.now());
		emailHistoryEntity.setDeletedFromReciever(false);
		emailHistoryEntity.setDeletedFromSender(false);
		
		emailHistoryEntity.setAttachmentOldNames(attachmentOldNames);
		emailHistoryEntity.setAttachmentNewNames(attachmentNewNames);
		emailHistoryEntity.setAttachmentPaths(attachmentPaths);
		
		//Saving Email History into the database
		emailRepository.save(emailHistoryEntity);
	}
	
	public void saveMail( EMailDto withoutAttachmentMailDto)
	{
		
		EmailHistory emailHistoryEntity = new EmailHistory();
		emailHistoryEntity.setSender(senderMail);
		emailHistoryEntity.setCcs(withoutAttachmentMailDto.getCcs());
		emailHistoryEntity.setRecipient(withoutAttachmentMailDto.getTo());
		emailHistoryEntity.setMessage(withoutAttachmentMailDto.getMessage());
		emailHistoryEntity.setSubject(withoutAttachmentMailDto.getSubject());
		emailHistoryEntity.setCreatedAt(LocalDateTime.now());
		emailHistoryEntity.setDeletedFromReciever(false);
		emailHistoryEntity.setDeletedFromSender(false);
		//Saving Email History into the database
		EmailHistory savedEmailHistory = emailRepository.save(emailHistoryEntity);
	}

	public Map<String, List<String>> saveAttachments(List<MultipartFile> attachments) throws DirectoryException, FileHandlingException
	{
		Map<String, List<String>> attachmentMap = new HashMap<>();
		List<String> attachmentOldNames = new ArrayList<>();
		List<String> attachmentNewNames = new ArrayList<>();
		List<String> attachmentPaths = new ArrayList<>();
		
		File pathDirFileObject = new File(dirPath);
		if(!pathDirFileObject.isDirectory())
		{
			try {
				Files.createDirectories(Path.of(dirPath));
			} catch (IOException e) {
				throw new DirectoryException("Directory Not Created due to permission");
				
			}
		}
		if(pathDirFileObject.isDirectory())
		{
			for(MultipartFile attachment : attachments)
			{
				String attachmentFullName = System.currentTimeMillis()+attachment.getOriginalFilename();
				String attachmentFullPath = pathDirFileObject.getAbsolutePath()+"\\"+attachmentFullName;
				
				attachmentNewNames.add(attachmentFullName);
				attachmentPaths.add(attachmentFullPath);
				attachmentOldNames.add(attachment.getOriginalFilename());
				
				try {
//					attachment.transferTo(new File(attachmentFullPath));
					Files.copy(attachment.getInputStream(),Paths.get(attachmentFullPath), StandardCopyOption.REPLACE_EXISTING);
				} 
				catch (IOException exception) 
				{
						throw new DirectoryException("Directory Not found");
				}
			}
			attachmentMap.put("attachmentOldNames", attachmentOldNames);
			attachmentMap.put("attachmentNewNames", attachmentNewNames);
			attachmentMap.put("attachmentPaths", attachmentPaths);
		}
		return attachmentMap;
	}
	
}
