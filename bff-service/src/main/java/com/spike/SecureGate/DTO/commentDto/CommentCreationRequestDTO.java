package com.spike.SecureGate.DTO.commentDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Component
public class CommentCreationRequestDTO {
    private String content;
    private List<MultipartFile> media;
}
