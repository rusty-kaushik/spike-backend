package com.in2it.spykeemployee.dto;

import java.util.List;
import java.util.UUID;

import com.in2it.spykeemployee.entity.Employee;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentDto {
	
	private UUID id;
	private String name;
	private String discription;
	private String managerId;
	private String adminId;
	

	private List<Employee> employees;
}
