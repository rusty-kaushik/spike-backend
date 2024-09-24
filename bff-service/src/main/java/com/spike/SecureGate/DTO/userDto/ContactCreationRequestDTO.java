package com.spike.SecureGate.DTO.userDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContactCreationRequestDTO {
    private long userId;
    private String name;
    private String designation;
    private String primaryMobile;
    private List<UserAddressDTO> addresses;
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;

}
