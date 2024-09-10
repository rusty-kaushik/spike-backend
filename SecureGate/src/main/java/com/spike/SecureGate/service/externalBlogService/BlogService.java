package com.spike.SecureGate.service.externalBlogService;

import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface BlogService {
    ResponseEntity<Object> createBlog(String userName, BlogCreationRequestDTO blogCreationRequestDTO);

    ResponseEntity<Object> updateBlog(String username, BlogUpdateRequestDTO blogUpdateRequestDTO);

    ResponseEntity<Object> fetchBlogById(String blogId);

    ResponseEntity<Object> fetchAllBlogs();

    ResponseEntity<Object> deleteBlogById(String blogId, String userName);
}
