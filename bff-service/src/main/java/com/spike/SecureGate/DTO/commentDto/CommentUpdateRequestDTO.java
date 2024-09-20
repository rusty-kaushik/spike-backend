package com.spike.SecureGate.DTO.commentDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CommentUpdateRequestDTO {
    private String content;
}
