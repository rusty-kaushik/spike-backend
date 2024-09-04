package com.in2it.spykeemployee.dto;

import java.util.UUID;

import com.in2it.spykeemployee.entity.SocialMedia;

public class ContactDto {
	
	private UUID id;
	private String primaryMobileNo;
	private String secondryMobileNo;
	private String email;
	private String backupEmail;
	private SocialMedia socialLinks;

}
