package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.TodoDto.TodoDTO;
import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.service.externalBlogService.BlogService;
import com.spike.SecureGate.service.externalTodoService.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    // CREATE A task
    @Operation(
            summary = "Create a todo task"
    )
    @PostMapping("/create/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> createTodoTask(
            @PathVariable Long userId,
            @RequestBody TodoDTO todoCreationDTO
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> task = todoService.createTodoTask(userId,userName,todoCreationDTO);
        logger.info("Finished creating new Blog");
        return task;
    }

    // UPDATE A task
    @Operation(
            summary = "Update a todo task"
    )
    @PutMapping("/update/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> updateTodoTask(
            @PathVariable Long taskId,
            @RequestBody TodoDTO todoCreationDTO
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> task = todoService.updateTodoTask(taskId,userName,todoCreationDTO);
        logger.info("Finished creating new Blog");
        return task;
    }

    // CREATE A task
    @Operation(
            summary = "Create a todo task"
    )
    @DeleteMapping("/delete/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> deleteTodoTask(
            @PathVariable Long taskId
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> task = todoService.deleteTodoTask(taskId,userName);
        logger.info("Finished creating new Blog");
        return task;
    }

    // CREATE A task
    @Operation(
            summary = "Create a todo task"
    )
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getTodoTask(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @PathVariable Long userId
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> task = todoService.getTodoTask(page,size,sort,userId);
        logger.info("Finished creating new Blog");
        return task;
    }


}
