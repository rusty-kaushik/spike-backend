package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationFeignDTO;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardUpdateFeignDTO;
import com.spike.SecureGate.enums.TaskBoardStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "taskBoardClient",  url = "${spike.service.taskBoard_service}")
public interface TaskBoardFeignClient {

    // CREATE A TASK
    @PostMapping("/taskboard/create")
    ResponseEntity<Object> createTaskboard(
            @RequestBody TaskBoardCreationFeignDTO taskBoardCreationFeignDTO
    );

    // UPDATE A TASK
    @PutMapping("/taskboard/update/{taskId}")
    ResponseEntity<Object> updateTaskboard(
            @RequestBody TaskBoardUpdateFeignDTO taskBoardUpdateFeignDTO,
            @PathVariable String taskId
    );

    // UPDATE THE STATUS OF A TASK
    @PutMapping("/taskboard/updateStatus/{taskId}")
    ResponseEntity<Object> updateTaskboardStatus(
            @PathVariable String taskId,
            @RequestParam TaskBoardStatus status
    );

    // GET A TASK BY ID
    @GetMapping("/taskboard/get/{taskId}")
    ResponseEntity<Object> getTaskById(
            @PathVariable String taskId
    );

    // GET ALL TASKS BY DEPARTMENT ID
    @GetMapping("/taskboard/getByDepartmentId/{departmentId}")
    ResponseEntity<Object> getByDepartmentId(
            @PathVariable long departmentId
    );

    // DELETE A TASK BY ID
    @DeleteMapping("/taskboard/delete/{taskId}")
    public ResponseEntity<Object> deleteTaskById(
            @PathVariable String taskId
    );


}
