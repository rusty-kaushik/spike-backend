package com.in2it.spykeemployee.dto;

import java.util.HashSet;

import com.in2it.spykeemployee.entity.Employee;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleDto {
	
	private int id;
	
	private String name;
	

	private HashSet<Employee> employees;

}
