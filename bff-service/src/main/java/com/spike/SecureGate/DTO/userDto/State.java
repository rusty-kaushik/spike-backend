package com.spike.SecureGate.DTO.userDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class State {
    private String name;
    private String countryName;

    public State(String name,String countryName) {
        this.name = name;
        this.countryName = countryName;
    }

}