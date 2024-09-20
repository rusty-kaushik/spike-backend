package com.spike.SecureGate.DTO.blogDto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class BlogUpdateFeignDTO {
    private String userName;
    private String title;
    private String content;
}