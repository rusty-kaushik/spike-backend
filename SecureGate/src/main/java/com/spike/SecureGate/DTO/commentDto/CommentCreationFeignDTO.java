package com.spike.SecureGate.DTO.commentDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Getter
@Setter
public class CommentCreationFeignDTO {
    private String content;
    private String userName;
    private List<MultipartFile> media;
}
