package com.spike.SecureGate.DTO.taskBoardDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskBoardCreationFeignDTO {
    private String userName;
    private long departmentId;
    private String title;
    private String taskDes;
}
