package com.spike.SecureGate.DTO.blogDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateRequestDTO {
    private String blogId;
    private String title;
    private String content;
}
