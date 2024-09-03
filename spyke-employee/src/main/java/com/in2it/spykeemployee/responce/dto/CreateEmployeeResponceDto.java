package com.in2it.spykeemployee.responce.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmployeeResponceDto {
	
	private UUID id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String employeeId;
	private String designation;
	private long salary;
	private LocalDate dateOfJoining;
	private LocalDate dateOfBirth;
	private String gender;
	private Set<String> rolesName; 

}
