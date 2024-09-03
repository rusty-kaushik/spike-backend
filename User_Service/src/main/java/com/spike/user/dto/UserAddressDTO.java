package com.spike.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressDTO {

    @JsonProperty("Line1")
    private String line1;

    @JsonProperty("Line2")
    private String line2;

    @JsonProperty("State")
    private String state;

    @JsonProperty("District")
    private String district;

    @JsonProperty("Zip")
    private String zip;

    @JsonProperty("City")
    private String city;

    @JsonProperty("NearestLandmark")
    private String nearestLandmark;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Type")
    private String type;
}


