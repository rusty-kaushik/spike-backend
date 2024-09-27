package com.spike.SecureGate.Validations;

import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.commentDto.CommentCreationRequestDTO;
import com.spike.SecureGate.DTO.commentDto.CommentUpdateRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CommentValidator {

    public boolean validateCommentCreationDto(CommentCreationRequestDTO commentCreationRequestDTO){

        // Validate content
        if (commentCreationRequestDTO.getContent() == null ||
                commentCreationRequestDTO.getContent().isEmpty() ||
                commentCreationRequestDTO.getContent().length() > 1000
        ) {
            throw new IllegalArgumentException("Content cannot be null or empty or more than 1000 characters");
        }

        // Validate media files if present
        if (commentCreationRequestDTO.getMedia() != null) {
            for (MultipartFile file : commentCreationRequestDTO.getMedia()) {
                if (file.isEmpty()) {
                    throw new IllegalArgumentException("Media files cannot be empty");
                }
                // Validate file size (must be <= 1 MB)
                if (file.getSize() > 1_048_576) { // 1 MB in bytes
                    throw new IllegalArgumentException("File size cannot exceed 1 MB");
                }
            }
        }
        return true;
    }

    public boolean validateCommentUpdateDto(CommentUpdateRequestDTO commentUpdateRequestDTO) {
        if (commentUpdateRequestDTO.getContent() == null ||
                commentUpdateRequestDTO.getContent().isEmpty() ||
                commentUpdateRequestDTO.getContent().length() > 1000
        ) {
            throw new IllegalArgumentException("Content cannot be null or empty  or more than 1000 characters");
        }
        return true;
    }
}
