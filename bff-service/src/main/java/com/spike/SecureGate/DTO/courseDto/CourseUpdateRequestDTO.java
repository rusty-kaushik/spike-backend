package com.spike.SecureGate.DTO.courseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseUpdateRequestDTO {

    private String courseName;

    private String title;

    private String content;

    private String departmentName;

    private String duration;
}