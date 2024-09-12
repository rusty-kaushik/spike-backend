package com.spike.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDropdownDTO {
    private Long id;
    private String name;

    public DepartmentDropdownDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
