package com.taskboard_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskboard_service.dto.Status;
import com.taskboard_service.dto.TaskboardDto;
import com.taskboard_service.dto.TaskboardUpdateDto;
import com.taskboard_service.exception.DetailsNotFoundException;
import com.taskboard_service.exception.TaskboadNotFoundException;
import com.taskboard_service.responseHandler.ResponseHandler;
import com.taskboard_service.service.TaskboardService;

import io.swagger.v3.oas.annotations.Hidden;

@RequestMapping("/taskboard")
@RestController
@Validated
public class TaskboardController {

	@Autowired
	TaskboardService service;
	
	
	@PostMapping("/create")
public ResponseEntity<ResponseHandler<TaskboardDto>> createTaskboard(@RequestBody TaskboardDto taskboardDto) throws TaskboadNotFoundException{
		
		
		ResponseHandler<TaskboardDto> resHandler=new ResponseHandler<TaskboardDto>(service.createTaskboard(taskboardDto), "Task created successfully", HttpStatus.OK, HttpStatus.OK.value());

	return ResponseEntity.status(HttpStatus.OK).body(resHandler);
		
	}
	
	@PutMapping("/update/{taskId}")
	public ResponseEntity<ResponseHandler<TaskboardDto>> updateTaskboard(@RequestBody TaskboardUpdateDto taskboardDto,@PathVariable String taskId) throws DetailsNotFoundException {
		UUID id = UUID.fromString(taskId);
	
		ResponseHandler<TaskboardDto> resHandler=new ResponseHandler<TaskboardDto>(service.updateTaskboard(taskboardDto, id), "Task update successfully", HttpStatus.OK, HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(resHandler);
			
		
	}
	
	@PutMapping("/updateStatus/{taskId}")
	public ResponseEntity<ResponseHandler<TaskboardDto>> updateTaskboardStatus( @PathVariable String taskId,@RequestParam Status status) throws DetailsNotFoundException {
		
		UUID id = UUID.fromString(taskId);
		
		ResponseHandler<TaskboardDto> resHandler=new ResponseHandler<TaskboardDto>(service.updateTaskboardStatus(status, id), "Task status has been changed successfully", HttpStatus.OK, HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(resHandler);
	
		
	}
	
	@GetMapping("/get/{taskId}")
	public ResponseEntity<ResponseHandler<TaskboardDto>> getTaskById( @PathVariable String taskId) throws DetailsNotFoundException {
		UUID id = UUID.fromString(taskId);
		
		ResponseHandler<TaskboardDto> resHandler=new ResponseHandler<TaskboardDto>(service.getTaskById(id), "Successfully retrieve task by id ", HttpStatus.OK, HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(resHandler);
			
		
	}
	
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseHandler<List<TaskboardDto>>>  getAllTask() throws TaskboadNotFoundException{
		
		ResponseHandler<List<TaskboardDto>> resHandler=new ResponseHandler<List<TaskboardDto>>(service.getAllTask(), "Successfully retrieve all task.", HttpStatus.OK, HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(resHandler);
			
	}
	
	@GetMapping("/getByDepartmentId/{departmentId}")
	public ResponseEntity<ResponseHandler<List<TaskboardDto>>> getByDepartmentId(@PathVariable long departmentId){
		
		ResponseHandler<List<TaskboardDto>> resHandler=new ResponseHandler<List<TaskboardDto>>(service.getByDepartmentId(departmentId), "Successfully retrieve  task by departmentId", HttpStatus.OK, HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(resHandler);
		
		
	}
	
	@DeleteMapping("delete/{taskId}")
	public ResponseEntity<ResponseHandler<Boolean>> deleteTaskById(@PathVariable String taskId) {
		
		UUID id = UUID.fromString(taskId);
		
		ResponseHandler<Boolean> resHandler=new ResponseHandler<Boolean>(service.deleteTaskById(id), "Successfully delete task by id", HttpStatus.OK, HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(resHandler);
	
	}
}
