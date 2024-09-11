package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.service.externalBlogService.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/in2it/spike/SecureGate/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    // CREATE A BLOG
    @Operation(
            summary = "Create a blog",
            description = "Any user can create a blog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created a blog",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @PostMapping(value = "/create" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> createBlog(@ModelAttribute BlogCreationRequestDTO blogCreationRequestDTO)
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new Blog");
        ResponseEntity<Object> user = blogService.createBlog(userName,blogCreationRequestDTO);
        logger.info("Finished creating new Blog");
        return user;
    }

    // UPDATE A BLOG
    @Operation(
            summary = "Update a blog",
            description = "Any user can update his/her blog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the blog",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
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
            description = "Any user can fetch a blog by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched the blog",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
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
            description = "Any user can fetch all blogs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched all blog",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> fetchAllBlogs()
    {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new Blog");
        ResponseEntity<Object> user = blogService.fetchAllBlogs();
        logger.info("Finished creating new Blog");
        return user;
    }

    // DELETE A BLOG BY ID
    @Operation(
            summary = "Delete a blog by ID",
            description = "Any user can delete his/her blog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted the blog",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden - Invalid token",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
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
