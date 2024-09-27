package com.spike.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ManagerDropdownDTO {
    private Long id;
    private String name;

    // Constructor
    public ManagerDropdownDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
