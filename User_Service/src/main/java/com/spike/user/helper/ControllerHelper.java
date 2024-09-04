package com.spike.user.helper;

import com.spike.user.entity.User;
import com.spike.user.service.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ControllerHelper {

@Autowired
private UserServiceImpl userService;

    public Pageable getPageable(int page, int size, String sortColumn, String sortDirection) {
        // Set default sort direction
        Sort.Direction direction = Sort.Direction.ASC;

        // Determine sort direction based on input
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }

        // Set default sort column if not provided
        String column = (sortColumn == null || sortColumn.isEmpty()) ? "id" : sortColumn;

        // Return Pageable with or without sorting
        return PageRequest.of(page, size, Sort.by(direction, column));
    }

}
