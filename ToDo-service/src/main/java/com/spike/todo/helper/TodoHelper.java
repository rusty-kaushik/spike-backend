package com.spike.todo.helper;

import com.spike.todo.dto.TodoCreationDTO;
import com.spike.todo.entity.TODO;
import com.spike.todo.exceptions.UnexpectedException;
import org.springframework.stereotype.Component;

@Component
public class TodoHelper {

    public TODO convertDtoToEntity(TodoCreationDTO todoCreationDTO, Long userId){
        try {
            TODO todo = new TODO();
            todo.setContent(todoCreationDTO.getContent());
            todo.setStatus(todoCreationDTO.getStatus());
            todo.setUserId(userId);
            return todo;
        } catch (Exception e) {
            throw new UnexpectedException("UnexpectedException", "Error creating task" + e.getCause());
        }
    }



}
