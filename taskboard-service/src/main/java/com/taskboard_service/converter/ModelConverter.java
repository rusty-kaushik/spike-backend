package com.taskboard_service.converter;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taskboard_service.dto.Status;
import com.taskboard_service.dto.TaskboardDto;
import com.taskboard_service.entity.Taskboard;

@Component
public class ModelConverter {

	@Autowired
	ModelMapper mapper;
	
//	@Autowired
//	Taskboard taskboard;

	public TaskboardDto entityToDto(Taskboard taskboard) {

		TaskboardDto taskboardDto = mapper.map(taskboard, TaskboardDto.class);
	
		return taskboardDto;

	}

	public Taskboard dtoToEntity(TaskboardDto taskboardDto) {

		Taskboard taskboard = mapper.map(taskboardDto, Taskboard.class);

		taskboard.setCreatedAt(LocalDateTime.now());
		taskboard.setStatus(Status.TODO);
		taskboard.setCreatedBy(taskboard.getUserName());        // whose assign task 
		return taskboard;

	}
	
}
