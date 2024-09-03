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

    public Pageable getPageable(int page, int size, String sortColumn, String sort) {
        if (sort != null && !sort.isEmpty()) {
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (sort.equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
            if (sortColumn == null || sortColumn.isEmpty()) {
                sortColumn = "id";
            }
            return PageRequest.of(page, size, Sort.by(sortDirection, sortColumn));
        } else {
            return PageRequest.of(page, size);
        }
    }

}
