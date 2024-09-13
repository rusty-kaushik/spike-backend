package com.spike.SecureGate.Validations;

import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.JdbcHelper.BlogDbService;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.BlogFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class BlogValidators {

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private BlogDbService blogDbService;

    @Autowired
    private BlogFeignClient blogFeignClient;

    public boolean validateBlogCreationDto(BlogCreationRequestDTO blogCreationRequestDTO){

        // Validate title
        if (blogCreationRequestDTO.getTitle() == null ||
                blogCreationRequestDTO.getTitle().isEmpty() ||
                !blogCreationRequestDTO.getTitle().matches("^[a-zA-Z0-9 ]*$") ||
                blogCreationRequestDTO.getTitle().length() < 3 ||
                blogCreationRequestDTO.getTitle().length() > 50
        ) {
            throw new ValidationFailedException("Title cannot be null, empty, or contain special characters or more than 50 characters");
        }

        // Validate content
        if (blogCreationRequestDTO.getContent() == null ||
                blogCreationRequestDTO.getContent().isEmpty() ||
                blogCreationRequestDTO.getContent().length() > 10000
        ) {
            throw new ValidationFailedException("Content cannot be null or empty or more than 1000 characters");
        }

        // Validate media files if present
        if (blogCreationRequestDTO.getMedia() != null) {
            for (MultipartFile file : blogCreationRequestDTO.getMedia()) {
                if (file.isEmpty()) {
                    throw new ValidationFailedException("Media files cannot be empty");
                }
                // Validate file size (must be <= 1 MB)
//                if (file.getSize() > 1_048_576) { // 1 MB in bytes
//                    throw new ValidationFailedException("File size cannot exceed 1 MB");
//                }
            }
        }

        // Validate department names
        if(blogCreationRequestDTO.getDepartmentId() <= 0 || !isDepartmentIdPresent(blogCreationRequestDTO.getDepartmentId()) ) {
            throw new ValidationFailedException("File size cannot exceed 1 MB");
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
            throw new ValidationFailedException("Title cannot be null, empty, or contain special characters");
        }

        // Validate content
        if (blogUpdateRequestDTO.getContent() == null || blogUpdateRequestDTO.getContent().isEmpty()) {
            throw new ValidationFailedException("Content cannot be null or empty");
        }
        return true;
    }

    public boolean validateBlogExistence (String blogId) {
        if(!blogDbService.doesBlogExist(blogId)){
            throw new ValidationFailedException("Blog does not exist");
        }
        return true;
    }
}
