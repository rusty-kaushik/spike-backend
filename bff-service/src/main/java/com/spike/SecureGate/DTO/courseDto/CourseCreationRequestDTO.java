package com.spike.SecureGate.DTO.courseDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class CourseCreationRequestDTO {
    private String courseName;
    private String content;
    private String departmentName;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date startDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date endDate;
}