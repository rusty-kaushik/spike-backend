package com.spike.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class UserContactsDTO {

    private String name;
    private String designation;
    private String primaryMobile;
    private String profilePicture;
    private List<UserAddressDTO> primaryAddress;
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;


}
