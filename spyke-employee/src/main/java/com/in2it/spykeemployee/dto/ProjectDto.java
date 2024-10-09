package com.in2it.spykeemployee.dto;

import java.util.List;
import java.util.UUID;

import com.in2it.spykeemployee.entity.Employee;

import jakarta.persistence.ManyToMany;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectDto {
	
	private UUID id;
	private String name;
	private String description;

}
