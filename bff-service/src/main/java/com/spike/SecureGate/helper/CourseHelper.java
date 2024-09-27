package com.spike.SecureGate.helper;

import com.spike.SecureGate.DTO.blogDto.BlogCreationFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.DTO.courseDto.CourseCreationFeignDTO;
import com.spike.SecureGate.DTO.courseDto.CourseCreationRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CourseHelper {

    // CONVERTS A BLOG CREATION REQUEST DATA TO FEIGN CREATION REQUEST DATA
    public CourseCreationFeignDTO courseCreationDtoTOFeignDto(CourseCreationRequestDTO courseCreationRequestDTO, String userName) {
       CourseCreationFeignDTO feignDto = new CourseCreationFeignDTO();
       feignDto.setCourseName(courseCreationRequestDTO.getCourseName());
       feignDto.setDepartmentName(courseCreationRequestDTO.getDepartmentName());
       feignDto.setContent(courseCreationRequestDTO.getContent());
       feignDto.setDuration(courseCreationRequestDTO.getDuration());
       feignDto.setCreatedBy(userName);
       return feignDto;
    }
}
