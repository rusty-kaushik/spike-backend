package com.spike.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardDTO {

    private Long id;
    private String name;
    private String email;
    private String designation;
    private String primaryMobileNumber;
    private Double salary;
    private String profilePicture;


}
