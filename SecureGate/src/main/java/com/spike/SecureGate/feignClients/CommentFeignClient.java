package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.commentDto.CommentCreationFeignDTO;
import com.spike.SecureGate.DTO.commentDto.CommentUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "userClient",  url = "${spike.service.comment_service}")
public interface CommentFeignClient {

    // CREATE A COMMENT
    @PostMapping(value = "/spike/blog/comment/create/{blogId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object> createNewComment(
            @PathVariable String blogId,
            @ModelAttribute CommentCreationFeignDTO commentCreationFeignDTO
    );

    // UPDATE A COMMENT BY COMMENT ID
    @PutMapping("/spike/blog/comment/update/{commentId}")
    ResponseEntity<Object> UpdateComment(
            @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO,
            @PathVariable String commentId
    );

    // GET ALL COMMENTS BY BLOG ID
    @GetMapping("/spike/blog/comment/get-all/{blogId}")
    public ResponseEntity<Object> getAllCommentsOfBlog(
            @PathVariable String blogId
    );

    // GET COMMENT BY COMMENT ID
    @GetMapping("/spike/blog/comment/comment/{commentId}")
    public ResponseEntity<Object> getCommentByCommentId(
            @PathVariable String commentId
    );

    // DELETE A COMMENT BY COMMENT ID AND BLOG ID
    @DeleteMapping("/spike/blog/comment/delete/{blogId}/{commentId}")
    public ResponseEntity<Object> deleteCommentByBlogId(
            @PathVariable String blogId,
            @PathVariable String commentId
    );
}
