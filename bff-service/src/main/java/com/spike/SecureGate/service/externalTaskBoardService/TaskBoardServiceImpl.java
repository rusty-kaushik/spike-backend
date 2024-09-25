package com.spike.SecureGate.service.externalTaskBoardService;

import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationFeignDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationRequestDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardUpdateFeignDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardUpdateRequestDTO;
import com.spike.SecureGate.Validations.BlogValidators;
import com.spike.SecureGate.enums.TaskBoardStatus;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.TaskBoardFeignClient;
import com.spike.SecureGate.helper.TaskBoardHelper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskBoardServiceImpl implements TaskBoardService {

    private static final Logger logger = LoggerFactory.getLogger(TaskBoardServiceImpl.class);

    @Autowired
    private TaskBoardHelper taskBoardHelper;

    @Autowired
    private TaskBoardFeignClient taskBoardFeignClient;

    @Autowired
    private BlogValidators blogValidators;

    @Override
    public ResponseEntity<Object> createTask(String userName, TaskBoardCreationRequestDTO taskBoardCreationRequestDTO) {
        try {
            if(!blogValidators.isDepartmentIdPresent(taskBoardCreationRequestDTO.getDepartmentId())) {
                throw new ValidationFailedException("ValidationError","Invalid Department Id");
            }
            TaskBoardCreationFeignDTO taskBoardCreationFeignDTO = taskBoardHelper.convertTaskBoardCreationRequestDtoToFeignDto(userName, taskBoardCreationRequestDTO);
            return taskBoardFeignClient.createTaskboard(taskBoardCreationFeignDTO);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> editTask(String taskId, String userName, TaskBoardUpdateRequestDTO taskBoardUpdateRequestDTO) {
        try {
            TaskBoardUpdateFeignDTO taskBoardUpdateFeignDTO = taskBoardHelper.convertTaskBoardUpdateRequestDtoToFeignDto(userName, taskBoardUpdateRequestDTO);
            return taskBoardFeignClient.updateTaskboard(taskBoardUpdateFeignDTO,taskId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> statusEditTask(String taskId, TaskBoardStatus status) {
        try {
            return taskBoardFeignClient.updateTaskboardStatus(taskId,status);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getByID(String taskId) {
        try {
            return taskBoardFeignClient.getTaskById(taskId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getByDepartmentID(long departmentID) {
        try {
            return taskBoardFeignClient.getByDepartmentId(departmentID);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteByID(String taskId) {
        try {
            return taskBoardFeignClient.deleteTaskById(taskId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a user: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a user: " + e.getMessage());
        }
    }


}
