package com.spike.SecureGate.service.externalCommentsService;

import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.commentDto.CommentCreationRequestDTO;
import com.spike.SecureGate.DTO.commentDto.CommentUpdateRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CommentService {
    ResponseEntity<Object> createNewComment(String blogId, String userName, CommentCreationRequestDTO commentCreationRequestDTO);

    ResponseEntity<Object> updateComment(String commentId, CommentUpdateRequestDTO commentUpdateRequestDTO);

    ResponseEntity<Object> getAllCommentsOfABlog(String blogId);

    ResponseEntity<Object> getCommentByCommentId(String commentId);

    ResponseEntity<Object> deleteComment(String blogId, String commentId);
}
