package com.taskboard_service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoDTO {

    private long id;
    private String username;
    private String role;
    private String email;
    private String designation;
    private String picture;

}