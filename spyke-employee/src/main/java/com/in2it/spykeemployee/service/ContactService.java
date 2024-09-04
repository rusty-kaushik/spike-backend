package com.in2it.spykeemployee.service;

import com.in2it.spykeemployee.entity.Contact;

public interface ContactService {
	
	public Contact createContact(String primaryMobileNo, String secondryMobileNo, String email, String employeeId, String backupEmail);
	public Contact updateContact(String contactId,String primaryMobileNo, String secondryMobileNo, String email, String backupEmail);
	public Contact getContactByContactId(String contactId);
	public Contact getContactByEmployeeId(String employeeId);
	public void deleteContact(String contactId);
	

}
