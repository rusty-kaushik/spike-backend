package com.spike.user.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import com.spike.user.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactsDto {

    @JsonProperty(access = Access.READ_ONLY)
    private long userId;
    private String name;
    private String designation;
    private String primaryMobile;
    private String profilePicture;
    private List<ContactsaddressDto> addresses;
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;


}
