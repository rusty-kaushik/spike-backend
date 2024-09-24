package com.mail.JavaMail.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mail.JavaMail.customExceptions.DirectoryException;
import com.mail.JavaMail.customExceptions.FileHandlingException;
import com.mail.JavaMail.customExceptions.MailNotSentException;
import com.mail.JavaMail.dto.EMailDto;

import jakarta.mail.MessagingException;


@Service
public interface EmailService {


	public String sendMail( List<MultipartFile> attachment, EMailDto withoutAttachmentMailDto) throws MailNotSentException, MessagingException, DirectoryException, FileHandlingException, IOException;
	public String sendMail( EMailDto withoutAttachmentMailDto)  throws MessagingException;
}
