package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.userDto.UserChangePasswordDTO;
import com.spike.SecureGate.service.externalLikeService.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "Like/Unlike APIs")
@RestController
@RequestMapping("/spike/SecureGate/like")
public class LikeController {

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    private LikeService likeService;

    // like or unlike a blog
    @Operation(
            summary = "LIKE/UNLIKE a blog",
            description ="LIKE/UNLIKE a blog")
    @PutMapping("/{blogId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> likeOrUnlikeABlog(@PathVariable String blogId)
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started liking/unliking a blog");
        ResponseEntity<Object> user = likeService.likeOrUnlikeABlog(userName, blogId);
        logger.info("Finished liking/unliking a blog");
        return user;
    }

    // List of users who liked a blog
    @Operation(
            summary = "List of users who liked a blog",
            description ="List of users who liked a blog")
    @GetMapping("/users/{blogId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> listOfUsersWhoLikedBlog(@PathVariable String blogId)
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching the list of users who liked a blog");
        ResponseEntity<Object> user = likeService.listOfUsersWhoLikedBlog(blogId);
        logger.info("Finished fetching the list of users who liked a blog");
        return user;
    }

}
