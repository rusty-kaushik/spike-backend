package com.in2it.spykeemployee.service;

import com.in2it.spykeemployee.entity.Contact;

public interface ContactService {
	
	Contact createContact(String primaryMobileNo, String secondryMobileNo, String email, String employeeId, String backupEmail);
	Contact updateContact(String contactId,String primaryMobileNo, String secondryMobileNo, String email, String backupEmail);
	Contact getContactByContactId(String contactId);
	Contact getContactByEmployeeId(String employeeId);
	void deleteContact(String contactId);
	

}
