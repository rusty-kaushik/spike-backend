package com.spike.SecureGate.DTO.userDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class City {
    private String name;
    private String stateName;

    public City(String name,String stateName) {
        this.name = name;
        this.stateName = stateName;
    }

}