package com.in2it.spykeemployee.responce.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponceDto {
	
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



	private String primaryContactNumber;
	private String secondryContactNumber;
	

//	private Address address1;

//	private Address address2;
	

	private String departmentName;
	
	
	private List<String> projectsName;
	
	
    private Set<String> rolesName;
	

}
