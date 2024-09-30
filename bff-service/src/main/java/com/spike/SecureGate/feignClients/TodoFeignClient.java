package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.TodoDto.TodoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "userClient",  url = "${spike.service.todo_service}")
public interface TodoFeignClient {

    // CREATE A TASK
    @PostMapping("/spike/todo/{userId}/{username}")
    ResponseEntity<Object> createNewTodoTask(
            @PathVariable Long userId,
            @PathVariable String username,
            @RequestBody TodoDTO todoCreationDTO
    );

    // UPDATE A TASK
    @PutMapping("/spike/todo/{taskId}/{username}")
    ResponseEntity<Object> updateTodoTask(
            @PathVariable Long taskId,
            @PathVariable String username,
            @RequestBody TodoDTO todoCreationDTO
    );

    // DELETE A TASK
    @DeleteMapping("/spike/todo/{taskId}/{username}")
    ResponseEntity<Object> deleteTodoTask(
            @PathVariable Long taskId,
            @PathVariable String username
    );

    // GET ALL TASKS
    @GetMapping("/{userId}")
    ResponseEntity<Object> getAllTasks(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort,
            @PathVariable Long userId
    );
}
