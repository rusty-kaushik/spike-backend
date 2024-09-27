package com.taskboard_service.service;

import java.util.List;
import java.util.UUID;

import com.taskboard_service.dto.Status;
import com.taskboard_service.dto.TaskboardDto;
import com.taskboard_service.dto.TaskboardUpdateDto;
import com.taskboard_service.exception.DetailsNotFoundException;
import com.taskboard_service.exception.TaskboadNotFoundException;

public interface TaskboardService {

	public TaskboardDto createTaskboard(TaskboardDto taskboardDto) throws TaskboadNotFoundException;
	public TaskboardDto updateTaskboard(TaskboardUpdateDto taskboardDto, UUID id) throws DetailsNotFoundException;
	
	public TaskboardDto updateTaskboardStatus(Status status, UUID id) throws DetailsNotFoundException;
	
	public TaskboardDto getTaskById(UUID id) throws DetailsNotFoundException;
	public List<TaskboardDto>  getAllTask() throws TaskboadNotFoundException;
	
	public boolean deleteTaskById(UUID id);
	public List<TaskboardDto> getByDepartmentId(long departmentId);
}
