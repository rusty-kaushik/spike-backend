package com.spike.SecureGate.DTO.taskBoardDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskBoardCreationRequestDTO {
    private long departmentId;
    private String title;
    private String content;
}
