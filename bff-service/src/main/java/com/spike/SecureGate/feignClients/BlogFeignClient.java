package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.blogDto.BlogCreationFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateFeignDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "userClient",  url = "${spike.service.blog_service}")
public interface BlogFeignClient {

    // Create a new blog
    @PostMapping(value = "/spike/blog/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object> createNewBlog(
            @RequestPart(value = "media", required = false) List<MultipartFile> mediaFile,
            @RequestPart(value = "blogDto") BlogCreationFeignDTO blogDto
    );

    // Update a blog
    @PutMapping("/spike/blog/update/{blogId}")
    ResponseEntity<Object> updateBlog(
            @PathVariable String blogId,
            @RequestBody BlogUpdateFeignDTO blogUpdateFeignDTO);

    // Get a blog by Blog Id
    @GetMapping("/spike/blog/getByBlogId/{blogId}")
    ResponseEntity<Object> fetchBlogById(
            @PathVariable String blogId
    );

    // Delete a blog by Blog Id
    @DeleteMapping("/spike/blog/deleteByBlogId/{blogId}")
    ResponseEntity<Object> deleteBlogById(
            @PathVariable String blogId,
            @RequestParam String updatedBy
    );

    // Get all blogs
    @GetMapping("/spike/blog/getAll")
    ResponseEntity<Object> fetchAllBlogs(
            @RequestParam int pageNum,
            @RequestParam int pageSize
    );

    @GetMapping("/spike/blog/getByName/{name}")
    ResponseEntity<Object> getBlogsByAutherName(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @PathVariable String name
    );

    @GetMapping("/spike/blog/getByUserId/{userId}")
    ResponseEntity<Object> getBlogsByAutherId(
            @PathVariable long userId
    );

    @GetMapping("/spike/blog/getByTitle/{blogTitle}")
    ResponseEntity<Object> getBlogByTitle(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @PathVariable String title
    );

}
