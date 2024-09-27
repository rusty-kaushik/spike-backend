package com.spike.SecureGate.DTO.taskBoardDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskBoardUpdateFeignDTO {
    private String userName;
    private String title;
    private String taskDes;
}
