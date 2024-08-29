package com.in2it.spykeemployee.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.in2it.spykeemployee.entity.Address;
import com.in2it.spykeemployee.entity.Contact;
import com.in2it.spykeemployee.entity.Department;
import com.in2it.spykeemployee.entity.Project;
import com.in2it.spykeemployee.entity.Role;

public class EmployeeDto {

	private UUID id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String employeeId;
	private String designation;
	private LocalDate dateOfJoining;
	private LocalDate dateOfBirth;
	private String gender;
	private long salary;
	private String status;
//	private MediaFile profilePic;


	private Contact contact;
	

	private Address address1;

	private Address address2;
	

	private Department department;
	
	
	private List<Project> projects;
	
	
    private Set<Role> roles;
	

}
