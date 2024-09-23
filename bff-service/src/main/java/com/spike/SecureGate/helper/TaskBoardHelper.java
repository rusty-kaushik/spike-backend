package com.spike.SecureGate.helper;

import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationFeignDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationRequestDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardUpdateFeignDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardUpdateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskBoardHelper {

    public TaskBoardCreationFeignDTO convertTaskBoardCreationRequestDtoToFeignDto(String userName,TaskBoardCreationRequestDTO taskBoardCreationRequestDto) {
        TaskBoardCreationFeignDTO data = new TaskBoardCreationFeignDTO();
        data.setTitle(taskBoardCreationRequestDto.getTitle());
        data.setTaskDes(taskBoardCreationRequestDto.getContent());
        data.setDepartmentId(taskBoardCreationRequestDto.getDepartmentId());
        data.setUserName(userName);
        return data;
    }

    public TaskBoardUpdateFeignDTO convertTaskBoardUpdateRequestDtoToFeignDto(String userName, TaskBoardUpdateRequestDTO taskBoardUpdateRequestDTO) {
        TaskBoardUpdateFeignDTO data = new TaskBoardUpdateFeignDTO();
        data.setTitle(taskBoardUpdateRequestDTO.getTitle());
        data.setTaskDes(taskBoardUpdateRequestDTO.getContent());
        data.setUserName(userName);
        return data;
    }
}
