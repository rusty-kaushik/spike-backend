package com.in2it.spykeemployee.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.entity.Contact;
import com.in2it.spykeemployee.entity.Contact.ContactBuilder;
import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.exception.ContactNotFoundException;
import com.in2it.spykeemployee.repository.ContactRepository;
import com.in2it.spykeemployee.repository.EmployeeRepository;
import com.in2it.spykeemployee.service.ContactService;
import com.in2it.spykeemployee.service.EmployeeService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ContactRepository repository;

	@Override
	public Contact createContact(String primaryMobileNo, String secondryMobileNo, String email, String employeeId,
			String backupEmail) {
		Employee employee = employeeService.getEmployeeById(employeeId).orElseThrow(()-> new UsernameNotFoundException("Employee Not Found With Given EmployeeId"));
		Contact contact = Contact.builder().email(email).backupEmail(backupEmail)
				.primaryMobileNo(primaryMobileNo)
				.employee(employee)
				.secondryMobileNo(secondryMobileNo).build();
		Contact savedContact = repository.save(contact);
		employee.setContact(savedContact);
		employeeRepository.save(employee);
		return savedContact;
	}

	@Override
	public Contact updateContact(String contactId, String primaryMobileNo, String secondryMobileNo, String email,
			String backupEmail) {
		
			Contact contact = repository.findById(UUID.fromString(contactId)).orElseThrow(()-> new ContactNotFoundException("Contact dosen't exist with given id"));
			contact.setBackupEmail(backupEmail);
			contact.setEmail(email);
			contact.setPrimaryMobileNo(primaryMobileNo);
			contact.setSecondryMobileNo(secondryMobileNo);
			return repository.save(contact);
		
		
	
	}
	

	@Override
	public Contact getContactByContactId(String contactId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contact getContactByEmployeeId(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteContact(String contactId) {
		// TODO Auto-generated method stub

	}




}
