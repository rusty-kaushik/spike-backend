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
    @PostMapping(path = "/spike/blog/create/comment/{blogId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<Object> createNewComment(
            @ModelAttribute CommentCreationFeignDTO commentDto,
            @PathVariable String blogId
    );

    // UPDATE A COMMENT BY COMMENT ID
    @PutMapping("/spike/blog/update/comment/{commentId}")
    ResponseEntity<Object> UpdateComment(
            @RequestBody CommentUpdateRequestDTO updateDto,
            @PathVariable String commentId
    );

    // GET ALL COMMENTS BY BLOG ID
    @GetMapping("/spike/blog/get-all/comment/{blogId}")
    public ResponseEntity<Object> getAllCommentsOfBlog(
            @PathVariable String blogId
    );

    // GET COMMENT BY COMMENT ID
    @GetMapping("/spike/blog/comment/{commentId}")
    public ResponseEntity<Object> getCommentByCommentId(
            @PathVariable String commentId
    );

    // DELETE A COMMENT BY COMMENT ID AND BLOG ID
    @DeleteMapping("/spike/blog/delete/comment/{blogId}/{commentId}")
    public ResponseEntity<Object> deleteCommentByBlogId(
            @PathVariable String blogId,
            @PathVariable String commentId
    );
}
