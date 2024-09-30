package com.spike.todo.dto;

import com.spike.todo.entity.TODOStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoCreationDTO {
    private String content;
    private TODOStatus status;
}
