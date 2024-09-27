package com.spike.SecureGate.DTO.userDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Country {
    private String id;
    private String name;

    public Country(String id, String name) {
        this.id = id;
        this.name = name;
    }

}