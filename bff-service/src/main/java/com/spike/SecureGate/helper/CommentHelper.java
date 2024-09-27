package com.spike.SecureGate.helper;

import com.spike.SecureGate.DTO.blogDto.BlogCreationFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.commentDto.CommentCreationFeignDTO;
import com.spike.SecureGate.DTO.commentDto.CommentCreationRequestDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommentHelper {

    // CONVERTS A COMMENT CREATION REQUEST DATA TO FEIGN CREATION REQUEST DATA
    public CommentCreationFeignDTO commentCreationDtoTOFeignDto(String userName, CommentCreationRequestDTO content) {
        CommentCreationFeignDTO comment = new CommentCreationFeignDTO();
        comment.setUserName(userName);
        comment.setContent(content.getContent());
        comment.setMedia(content.getMedia());
        return comment;
    }

}
