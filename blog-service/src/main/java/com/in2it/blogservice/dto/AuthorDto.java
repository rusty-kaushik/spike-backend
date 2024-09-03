package com.in2it.blogservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDto {
	
	
	private long id;
	private String firstName;
	private String lastname;
	private String email;
	private String contactNumber;
	private String department;
	private long managerId;
	@NotNull
	private long departmentId;
	@NotNull
	private long projectId;
	

}
