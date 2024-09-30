package com.spike.SecureGate.service.externalTodoService;

import com.spike.SecureGate.DTO.TodoDto.TodoDTO;
import org.springframework.http.ResponseEntity;

public interface TodoService {


    ResponseEntity<Object> createTodoTask(Long userId, String userName, TodoDTO todoCreationDTO);

    ResponseEntity<Object> updateTodoTask(Long taskId, String userName, TodoDTO todoCreationDTO);

    ResponseEntity<Object> deleteTodoTask(Long taskId, String userName);

    ResponseEntity<Object> getTodoTask(int page, int size, String sort, Long userId);
}
