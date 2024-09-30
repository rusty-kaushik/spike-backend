package com.spike.SecureGate.service.externalTodoService;

import com.spike.SecureGate.DTO.TodoDto.TodoDTO;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.feignClients.TodoFeignClient;
import com.spike.SecureGate.service.externalTicketService.TicketService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoFeignClient todoFeignClient;

    @Override
    public ResponseEntity<Object> createTodoTask(Long userId, String userName, TodoDTO todoCreationDTO) {
        try{
            return todoFeignClient.createNewTodoTask(userId,userName,todoCreationDTO);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a todo task: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateTodoTask(Long taskId, String userName, TodoDTO todoCreationDTO) {
        try{
            return todoFeignClient.updateTodoTask(taskId,userName,todoCreationDTO);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a todo task: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteTodoTask(Long taskId, String userName) {
        try{
            return todoFeignClient.deleteTodoTask(taskId,userName);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a todo task: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getTodoTask(int page, int size, String sort, Long userId) {
        try{
            return todoFeignClient.getAllTasks(page,size,sort,userId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a todo task: " + e.getMessage());
        }
    }


}
