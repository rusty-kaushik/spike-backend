package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.service.externalBlogService.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    // CREATE A BLOG
    @Operation(
            summary = "Create a blog",
            description = "Any user can create a blog"
    )
    @PostMapping(path = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> createBlog(@ModelAttribute BlogCreationRequestDTO blogDto)
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new Blog");
        ResponseEntity<Object> user = blogService.createBlog(blogDto);
        logger.info("Finished creating new Blog");
        return user;
    }

    // UPDATE A BLOG
    @Operation(
            summary = "Update a blog",
            description = "Any user can update his/her blog"
    )
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> updateBlog(@RequestBody BlogUpdateRequestDTO blogUpdateRequestDTO)
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started updating Blog");
        ResponseEntity<Object> user = blogService.updateBlog(userName,blogUpdateRequestDTO);
        logger.info("Finished creating new Blog");
        return user;
    }

    // FETCH A BLOG BY ID
    @Operation(
            summary = "Fetch a blog by ID",
            description = "Any user can fetch a blog by ID"
    )
    @GetMapping("/get/{blogId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> fetchBlogById(@PathVariable String blogId)
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new Blog");
        ResponseEntity<Object> user = blogService.fetchBlogById(blogId);
        logger.info("Finished creating new Blog");
        return user;
    }

    // FETCH ALL BLOGS
    @Operation(
            summary = "Fetch all blogs",
            description = "Any user can fetch all blogs"
    )
    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> fetchAllBlogs(
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize)
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new Blog");
        ResponseEntity<Object> user = blogService.fetchAllBlogs(pageNum,pageSize);
        logger.info("Finished creating new Blog");
        return user;
    }

    // DELETE A BLOG BY ID
    @Operation(
            summary = "Delete a blog by ID",
            description = "Any user can delete his/her blog"
    )
    @DeleteMapping("/delete/{blogId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> deleteBlogById(@PathVariable String blogId)
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> user = blogService.deleteBlogById(blogId,userName);
        logger.info("Finished creating new Blog");
        return user;
    }
}
