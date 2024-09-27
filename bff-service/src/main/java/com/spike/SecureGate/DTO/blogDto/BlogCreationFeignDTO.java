package com.spike.SecureGate.DTO.blogDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogCreationFeignDTO {
    private long departmentId;
    private String userName;
    private String title;
    private String content;
}
