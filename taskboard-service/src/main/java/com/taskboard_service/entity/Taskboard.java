package com.taskboard_service.entity;

import java.util.UUID;

import org.hibernate.validator.constraints.Range;

import com.taskboard_service.dto.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Taskboard extends Auditable{

	@GeneratedValue(strategy = GenerationType.UUID)
	@Id
	private UUID id;
	
	@NotBlank(message = "userName cannot be blank") 
	private String userName;
	
	@Digits(integer = 5 , fraction = 0, message = "min length of digits is 5")
	@Range(min = 1 , message = "min number of digits is should be 1")
	private long departmentId;
	
	@NotBlank(message = "title cannot be blank") 
	@Column(length =50)
	private String title;
	
	@Column(name = "taskDescription" ,length = 200)
	@NotBlank(message = "task Description cannot be blank") 
	
	private String taskDes;
	
	private Status status;
	
}
