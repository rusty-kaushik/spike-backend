package com.spike.SecureGate.Validations;

import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.exceptions.BlogNotFoundException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.BlogFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Component
public class BlogValidators {

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private BlogFeignClient blogFeignClient;

    public boolean validateBlogCreationDto(BlogCreationRequestDTO blogCreationRequestDTO){

        // Validate title
        if (blogCreationRequestDTO.getTitle() == null ||
                blogCreationRequestDTO.getTitle().isEmpty() ||
                !blogCreationRequestDTO.getTitle().matches("^[a-zA-Z0-9 ]*$")
        ) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Title cannot be null, empty, or contain special characters or more than 50 characters"
            );
        }

        // Validate content
        if (blogCreationRequestDTO.getContent() == null ||
                blogCreationRequestDTO.getContent().isEmpty()
        ) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Content cannot be null or empty or more than 1000 characters"
            );
        }

        // Validate department names
        if(blogCreationRequestDTO.getDepartmentId() <= 0 || !isDepartmentIdPresent(blogCreationRequestDTO.getDepartmentId()) ) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid department"
            );
        }
        return true;
    }

    public boolean isDepartmentIdPresent(long departmentId) {
        List<Long> departmentIds = userDbService.getAllDepartmentIds();
        return departmentIds.contains(departmentId);
    }


    public boolean validateBlogUpdateDto(BlogUpdateRequestDTO blogUpdateRequestDTO) {
        // Validate title
        if (blogUpdateRequestDTO.getTitle() == null || blogUpdateRequestDTO.getTitle().isEmpty() || !blogUpdateRequestDTO.getTitle().matches("^[a-zA-Z0-9 ]*$")) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Title cannot be null, empty, or contain special characters"
            );
        }

        // Validate content
        if (blogUpdateRequestDTO.getContent() == null || blogUpdateRequestDTO.getContent().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Content cannot be null or empty"
            );
        }

        // validate blog ID
        if (!BlogIdExistenceValidation(blogUpdateRequestDTO.getBlogId())) {
            throw new BlogNotFoundException(
                    "ValidationError",
                    "Invalid blog Id"
            );
        }

        return true;
    }

    public boolean validateBlogExistence (String blogId) {

        if (!isValidUUID(blogId)) {
            throw new BlogNotFoundException(
                    "ValidationError",
                    "blogId is not in valid UUID format"
            );
        }
        // validate blog ID
        if (!BlogIdExistenceValidation(blogId)) {
            throw new BlogNotFoundException(
                    "ValidationError",
                    "Invalid blogId"
            );
        }
        return true;
    }

    public boolean isValidUUID(String blogId) {
        try {
            UUID.fromString(blogId);
            return true;
        } catch (IllegalArgumentException e) {return false;}
    }

    public boolean BlogIdExistenceValidation(String blogId) {
        ResponseEntity<Object> responseEntity = blogFeignClient.fetchBlogById(blogId);
        return responseEntity.getStatusCode().value() == 200;
    }

}
