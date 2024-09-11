package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.commentDto.CommentCreationRequestDTO;
import com.spike.SecureGate.DTO.commentDto.CommentUpdateRequestDTO;
import com.spike.SecureGate.service.externalCommentsService.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@RestController
@RequestMapping("/in2it/spike/SecureGate/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    // CREATE A COMMENT
    @Operation(
            summary = "Creates a new comment",
            description = "Add a comment to the blog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully added a comment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @PostMapping(value = "/add-comment/{blogId}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> createComment(
            @PathVariable String blogId,
            @ModelAttribute CommentCreationRequestDTO commentCreationRequestDTO
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating comment");
        ResponseEntity<Object> data = commentService.createNewComment(blogId, userName,commentCreationRequestDTO);
        logger.info("Finished creating comment");
        return data;
    }

    // EDIT A COMMENT
    @Operation(
            summary = "Edits a comment",
            description = "Edit a comment to the blog you added.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully added a comment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @PutMapping("/update/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> updateComment(
            @PathVariable String commentId,
            @ModelAttribute CommentUpdateRequestDTO commentUpdateRequestDTO
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started updating comment");
        ResponseEntity<Object> data = commentService.updateComment(commentId,commentUpdateRequestDTO);
        logger.info("Finished updating comment");
        return data;
    }

    // GET ALL COMMENTS OF A BLOG
    @Operation(
            summary = "Get all comments of the blog",
            description = "Get all comments of the blog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully added a comment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/get/all/{blogId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getAllCommentsOfABlog(
            @PathVariable String blogId
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching comments of a blog");
        ResponseEntity<Object> data = commentService.getAllCommentsOfABlog(blogId);
        logger.info("Finished fetching comments of a blog");
        return data;
    }

    // GET A COMMENT BY ID
    @Operation(
            summary = "Get a comment",
            description = "Returns a comment by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully added a comment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/get/comment/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getCommentByCommentId(
            @PathVariable String commentId
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching comment data");
        ResponseEntity<Object> data = commentService.getCommentByCommentId(commentId);
        logger.info("Finished fetching comment data");
        return data;
    }

    // GET A COMMENT BY ID
    @Operation(
            summary = "deletes a comment",
            description = "Deletes a comment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted a comment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("/delete/{blogId}/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> deleteComment(
            @PathVariable String blogId,
            @PathVariable String commentId
    )
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Stared deleting comment");
        ResponseEntity<Object> data = commentService.deleteComment(blogId,commentId);
        logger.info("Finished deleting comment");
        return data;
    }

}
