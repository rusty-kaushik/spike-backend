package com.spike.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactsAddressDto {

    private String line1;
    private String line2;
    private String state;
    private String district;
    private String zip;
    private String city;
    private String nearestLandmark;
    private String country;
    private String type;
}
