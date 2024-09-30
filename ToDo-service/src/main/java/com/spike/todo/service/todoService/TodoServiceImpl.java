package com.spike.todo.service.todoService;

import com.spike.todo.dto.TodoCreationDTO;
import com.spike.todo.entity.TODO;
import com.spike.todo.exceptions.UserNotFoundException;
import com.spike.todo.helper.TodoHelper;
import com.spike.todo.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoHelper todoHelper;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public TODO createNewTodoTask(Long userId, TodoCreationDTO todoCreationDTO) {
        TODO todo = todoHelper.convertDtoToEntity(todoCreationDTO, userId);
        return todoRepository.save(todo);
    }

    @Override
    public TODO updateTodoTask(Long taskId, TodoCreationDTO todoCreationDTO) {
        TODO task = todoRepository.findById(taskId).orElseThrow(() -> new UserNotFoundException("ValidationError", "Task not found with id: " + taskId));
        task.setContent(todoCreationDTO.getContent());
        task.setStatus(todoCreationDTO.getStatus());
        return todoRepository.save(task);
    }

    @Override
    public void deleteTodoTask(Long taskId) {
        todoRepository.deleteById(taskId);
    }

    @Override
    public Page<TODO> findAllByUserId(Long userId, Pageable paging) {
        return todoRepository.findByUserId(userId, paging);
    }


}