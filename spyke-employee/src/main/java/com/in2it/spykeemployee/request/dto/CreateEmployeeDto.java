package com.in2it.spykeemployee.request.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {

	@NotBlank(message = "First name can not be null or blank.")
	private String firstName;
	private String lastName;
	@NotBlank(message = "Password can not be null or blank.")
	private String password;
	private String designation;
	private LocalDate dateOfJoining;
	private LocalDate dateOfBirth;
	private String gender;
	private long salary;
	

}
