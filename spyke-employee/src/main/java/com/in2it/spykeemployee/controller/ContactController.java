package com.in2it.spykeemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.spykeemployee.entity.Contact;
import com.in2it.spykeemployee.service.ContactService;

@RestController
@RequestMapping("contacts")
public class ContactController {
	@Autowired
	ContactService service;

	@PostMapping("create-contact")
	Contact createContact(@RequestParam String primaryMobileNo, @RequestParam String secondryMobileNo,
			@RequestParam String email, @RequestParam String employeeId, @RequestParam String backupEmail) {
		return service.createContact(primaryMobileNo, secondryMobileNo, email, employeeId, backupEmail);
	}

	@PutMapping
	public Contact updateContact(@RequestParam String contactId, @RequestParam String primaryMobileNo,
			@RequestParam String secondryMobileNo, @RequestParam String email, @RequestParam String backupEmail) {
		return service.updateContact(contactId, primaryMobileNo, secondryMobileNo, email, backupEmail);

	}

}
