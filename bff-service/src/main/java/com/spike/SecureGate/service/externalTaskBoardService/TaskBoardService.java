package com.spike.SecureGate.service.externalTaskBoardService;

import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationRequestDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardUpdateRequestDTO;
import com.spike.SecureGate.enums.TaskBoardStatus;
import org.springframework.http.ResponseEntity;

public interface TaskBoardService {


    ResponseEntity<Object> createTask(String userName, TaskBoardCreationRequestDTO taskBoardCreationRequestDTO);

    ResponseEntity<Object> editTask(String taskId, String userName, TaskBoardUpdateRequestDTO taskBoardUpdateRequestDTO);

    ResponseEntity<Object> statusEditTask(String taskId, TaskBoardStatus status);

    ResponseEntity<Object> getByID(String taskId);

    ResponseEntity<Object> getByDepartmentID(long departmentID);

    ResponseEntity<Object> deleteByID(String taskId);
}
