package com.spike.todo.service.todoService;

import com.spike.todo.dto.TodoCreationDTO;
import com.spike.todo.entity.TODO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {


    TODO createNewTodoTask(Long userId, TodoCreationDTO todoCreationDTO);

    TODO updateTodoTask(Long taskId, TodoCreationDTO todoCreationDTO);

    void deleteTodoTask(Long taskId);


    Page<TODO> findAllByUserId(Long userId, Pageable paging);
}
