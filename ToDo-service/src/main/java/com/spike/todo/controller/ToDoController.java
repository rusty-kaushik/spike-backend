package com.spike.todo.controller;

import com.spike.todo.auditing.AuditorAwareImpl;
import com.spike.todo.dto.TodoCreationDTO;
import com.spike.todo.entity.TODO;
import com.spike.todo.exceptions.UnexpectedException;
import com.spike.todo.response.ResponseHandler;
import com.spike.todo.service.todoService.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spike/todo")
public class ToDoController {

    @Autowired
    private TodoService todoService;

    @Operation(
            summary = "User creates a new todo task"
    )
    @PostMapping("/{userId}/{username}")
    public ResponseEntity<Object> createNewTodoTask(
            @PathVariable Long userId,
            @PathVariable String username,
            @RequestBody TodoCreationDTO todoCreationDTO
            ) {
        try {
            AuditorAwareImpl.setCurrentAuditor(username);
            TODO createdTask = todoService.createNewTodoTask(userId,todoCreationDTO);
            return ResponseHandler.responseBuilder("task successfully created", HttpStatus.OK, createdTask);
        } catch ( UnexpectedException e ) {
            throw e;
        } finally {
            AuditorAwareImpl.clear();
        }
    }

    @Operation(
            summary = "User updates a todo task"
    )
    @PutMapping("/{taskId}/{username}")
    public ResponseEntity<Object> updateTodoTask(
            @PathVariable Long taskId,
            @PathVariable String username,
            @RequestBody TodoCreationDTO todoCreationDTO
    ) {
        try {
            AuditorAwareImpl.setCurrentAuditor(username);
            TODO createdTask = todoService.updateTodoTask(taskId,todoCreationDTO);
            return ResponseHandler.responseBuilder("task successfully updated", HttpStatus.OK, createdTask);
        } catch ( UnexpectedException e ) {
            throw e;
        } finally {
            AuditorAwareImpl.clear();
        }
    }

    @Operation(
            summary = "User deletes a todo task"
    )
    @DeleteMapping("/{taskId}/{username}")
    public ResponseEntity<Object> deleteTodoTask(
            @PathVariable Long taskId,
            @PathVariable String username
    ) {
        try {
            AuditorAwareImpl.setCurrentAuditor(username);
            todoService.deleteTodoTask(taskId);
            return ResponseHandler.responseBuilder("task successfully deleted", HttpStatus.OK, "Success");
        } catch ( UnexpectedException e ) {
            throw e;
        } finally {
            AuditorAwareImpl.clear();
        }
    }

    @Operation(
            summary = "User fetch all todo task"
    )
    @GetMapping()
    public ResponseEntity<Object> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        try {

            Pageable paging;
            if (sort != null && !sort.isEmpty()) {
                Sort.Direction sortDirection = Sort.Direction.ASC;
                if (sort.equalsIgnoreCase("desc")) {
                    sortDirection = Sort.Direction.DESC;
                }
                paging = PageRequest.of(page, size, Sort.by(sortDirection, "id"));
            } else {
                paging = PageRequest.of(page, size);
            }

            Page<TODO> pageTuts;

            return ResponseHandler.responseBuilder("task successfully deleted", HttpStatus.OK,  todoService.findAll(paging));
        } catch ( UnexpectedException e ) {
            throw e;
        } finally {
            AuditorAwareImpl.clear();
        }
    }
}
