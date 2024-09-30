package com.spike.SecureGate.DTO.TodoDto;

import com.spike.SecureGate.enums.TodoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDTO {
    private String content;
    private TodoStatus status;
}
