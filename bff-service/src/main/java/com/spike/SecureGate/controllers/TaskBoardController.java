package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationFeignDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationRequestDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardUpdateRequestDTO;
import com.spike.SecureGate.enums.TaskBoardStatus;
import com.spike.SecureGate.service.externalTaskBoardService.TaskBoardService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskBoard")
public class TaskBoardController {

    @Autowired
    private TaskBoardService taskBoardService;

    private static final Logger logger = LoggerFactory.getLogger(TaskBoardController.class);

    // CREATE A NEW TASK
    @Operation(
            summary = "Creates a new task",
            description = "Creates a new task with a title and content which is department specific."
    )
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> createTask(
            @RequestBody TaskBoardCreationRequestDTO taskBoardCreationRequestDTO
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new User");
        ResponseEntity<Object> user = taskBoardService.createTask(userName, taskBoardCreationRequestDTO);
        logger.info("Finished creating new User");
        return user;
    }

    // EDIT A TASK
    @Operation(
            summary = "Edit a task",
            description = "Edit a task with a title and content which is department specific."
    )
    @PutMapping("/edit/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> editTask(
            @RequestBody TaskBoardUpdateRequestDTO taskBoardUpdateRequestDTO,
            @PathVariable String taskId
            ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new User");
        ResponseEntity<Object> user = taskBoardService.editTask(taskId, userName, taskBoardUpdateRequestDTO);
        logger.info("Finished creating new User");
        return user;
    }

    // EDIT STATUS OF A TASK
    @Operation(
            summary = "Edit status of a task",
            description = "Edit status of a task."
    )
    @PutMapping("/status/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> statusEditTask(
            @PathVariable String taskId,
            @RequestParam TaskBoardStatus status
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new User");
        ResponseEntity<Object> user = taskBoardService.statusEditTask(taskId,status);
        logger.info("Finished creating new User");
        return user;
    }

    // GET TASK BY ID
    @Operation(
            summary = "GET task by ID"
    )
    @GetMapping("/fetch/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getByID(
            @PathVariable String taskId
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new User");
        ResponseEntity<Object> user = taskBoardService.getByID(taskId);
        logger.info("Finished creating new User");
        return user;
    }

    // GET TASK BY department ID
    @Operation(
            summary = "GET task by department ID"
    )
    @GetMapping("/department/{departmentID}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getByDepartmentID(
            @PathVariable long departmentID
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new User");
        ResponseEntity<Object> user = taskBoardService.getByDepartmentID(departmentID);
        logger.info("Finished creating new User");
        return user;
    }

    // DELETE A TASK BY ID
    @Operation(
            summary = "delete task by ID"
    )
    @DeleteMapping("/delete/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> deleteByID(
            @PathVariable String taskId
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new User");
        ResponseEntity<Object> user = taskBoardService.deleteByID(taskId);
        logger.info("Finished creating new User");
        return user;
    }

}
