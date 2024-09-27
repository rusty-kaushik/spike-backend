package com.spike.SecureGate.DTO.courseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCreationFeignDTO {
    private String courseName;
    private String content;
    private String departmentName;
    private String duration;
    private String createdBy;
}