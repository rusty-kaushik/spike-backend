package com.taskboard_service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskboard_service.converter.ModelConverter;
import com.taskboard_service.dto.Status;
import com.taskboard_service.dto.TaskboardDto;
import com.taskboard_service.dto.TaskboardUpdateDto;
import com.taskboard_service.dto.UserInfoDTO;
import com.taskboard_service.entity.Taskboard;
import com.taskboard_service.exception.DetailsNotFoundException;
import com.taskboard_service.exception.TaskboadNotFoundException;
import com.taskboard_service.repository.TaskBoardRepo;
import com.taskboard_service.service.TaskboardService;
import com.taskboard_service.userFeign.FeignClientDepartment;
import com.taskboard_service.userFeign.FeignClientForUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskboardServiceImpl implements TaskboardService{

	@Autowired
	ModelConverter converter;
	
	@Autowired
	TaskBoardRepo repo;
	
	@Autowired
	FeignClientForUser userFeign;
	
	
	@Autowired
	FeignClientDepartment clientDepartment;
	
//	
	@Override
	public TaskboardDto createTaskboard(TaskboardDto taskboardDto) throws TaskboadNotFoundException {

		Taskboard taskboard = converter.dtoToEntity(taskboardDto);
		
		UserInfoDTO userByUsername = userFeign.getUserByUsername(taskboardDto.getUserName());
		
		
		String department=null;
		
		try {
			
			ResponseEntity<Object> departmentById = clientDepartment.getDepartmentById(taskboardDto.getDepartmentId());
			Map<String, Object> depart = (Map<String, Object>) departmentById.getBody();
			department = (String) ((Map<String, Object>) depart.get("data")).get("name");
			
			System.out.println("kkkkkkkkkk"+department);
		
			
		} catch (Exception e) {
			log.error("Please ! Check your services connection . May be down."+e);
		}
		
		System.out.println("===================="+userByUsername);
		
		Taskboard taskEntity =null;
		if(taskboard!=null) {
			taskboard.setDepartmentName(department);
			 taskEntity = repo.save(taskboard);
			 return converter.entityToDto(taskEntity);
		}else {
			throw new TaskboadNotFoundException("Taskboard not found Exception ");
		}
		
	}

	@Override
	public TaskboardDto updateTaskboard(TaskboardUpdateDto taskboardDto, UUID id) throws DetailsNotFoundException {
	
		Optional<Taskboard> taskboard = repo.findById(id);
		
		if(taskboard.isPresent()) {
			if(taskboardDto.getTitle()!=null)taskboard.get().setTitle(taskboardDto.getTitle());
			if(taskboardDto.getTaskDes()!=null)taskboard.get().setTaskDes(taskboardDto.getTaskDes());
		}else {
			
			throw new DetailsNotFoundException("Details not found in this ID. Please , Try again");
		}
		
		taskboard.get().setLastModifiedBy(taskboardDto.getUserName());
		taskboard.get().setLastModifiedDate(LocalDateTime.now());
		
		return converter.entityToDto(repo.save(taskboard.get()));
	}
	
	
	@Override
	public TaskboardDto updateTaskboardStatus(Status status, UUID id) throws DetailsNotFoundException {
		 Optional<Taskboard> taskboard = repo.findById(id);
		
		
		 int ordinal = taskboard.get().getStatus().ordinal();
	
		if(taskboard.isPresent() && ordinal<status.ordinal()) {
			if(status!=null)taskboard.get().setStatus(status);
		}
		else {
			log.error("-------- : Sorry, Make sure your status must be grater than current status . Please! Try again");
			throw new DetailsNotFoundException("Sorry, Make sure your status must be grater than current status . Please! Try again");
		}
		
		return converter.entityToDto(repo.save(taskboard.get()));
	}
	
	
	

	@Override
	public TaskboardDto getTaskById(UUID id) throws DetailsNotFoundException {
		
		Taskboard taskboard = repo.findById(id).orElseThrow(()->new DetailsNotFoundException("Details not found Exception .Please , Try with correct id "));
		
		return converter.entityToDto(taskboard);
	}
	
	
	@Override
	@Deprecated
	public List<TaskboardDto>  getAllTask() throws TaskboadNotFoundException{
		
		
		List<Taskboard> taskboard = repo.findAll();
		
		List<TaskboardDto> taskboardDtos=new ArrayList<>();
		
		for (Taskboard taskboard2 : taskboard) {
			TaskboardDto entityToDto = converter.entityToDto(taskboard2);
			
			taskboardDtos.add(entityToDto);
			
		}
		
		
		return taskboardDtos;
	}

	@Override
	public boolean deleteTaskById(UUID id) {
		boolean flag=false;
		   Optional<Taskboard> findById = repo.findById(id);
		   	
			if(!findById.isEmpty()) {
				repo.deleteById(id);
				flag= true;
			}
			
		return flag;
	}

	@Override
	public List<TaskboardDto> getByDepartmentId(long departmentId) {
		
		
		List<TaskboardDto> taskboardDtos=new ArrayList<>();
		List<Taskboard> department = repo.findByDepartmentId(departmentId);
		
		for (Taskboard taskboard2 : department) {
			
			TaskboardDto entityToDto = converter.entityToDto(taskboard2);
			
			taskboardDtos.add(entityToDto);
			
		}
		
		return taskboardDtos;
	}

	
	

}
