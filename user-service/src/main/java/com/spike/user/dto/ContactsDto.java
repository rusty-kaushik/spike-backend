package com.spike.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactsDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long userId;
    private String name;
    private String designation;
    private String primaryMobileNumber;
    private List<UserAddressDTO> addresses;
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;


}
