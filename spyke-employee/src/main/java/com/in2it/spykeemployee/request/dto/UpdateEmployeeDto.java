package com.in2it.spykeemployee.request.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEmployeeDto {
	

	private String firstName;
	private String lastName;
	private String password;
	private String designation;
	private LocalDate dateOfJoining;
	private LocalDate dateOfBirth;
	private String gender;
	private long salary;

//	private MediaFile profilePic;


	
	



	

}
