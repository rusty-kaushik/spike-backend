package com.spike.SecureGate.service.externalBlogService;

import com.spike.SecureGate.DTO.blogDto.BlogCreationFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import com.spike.SecureGate.Validations.BlogValidators;
import com.spike.SecureGate.exceptions.BlogNotFoundException;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.BlogFeignClient;
import com.spike.SecureGate.helper.BlogHelper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogValidators blogValidators;

    @Autowired
    private BlogHelper blogHelper;

    @Autowired
    private BlogFeignClient blogFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);


    // CREATE A BLOG
    @Override
    public ResponseEntity<Object> createBlog(String userName, BlogCreationRequestDTO blogCreationRequestDTO) {
        try {
            if (!blogValidators.validateBlogCreationDto(blogCreationRequestDTO)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("ValidationError","Invalid BlogCreationRequestDTO");
            }
            // Convert request DTO to Feign DTO
            BlogCreationFeignDTO blogCreationFeignDTO = blogHelper.blogCreationDtoTOFeignDto(userName, blogCreationRequestDTO);
            // Call external blog service
            List<MultipartFile> mediaFiles = blogCreationRequestDTO.getMedia();
            return blogFeignClient.createNewBlog(mediaFiles, blogCreationFeignDTO);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while creating a blog: " + e.getMessage());
        }
    }


    // UPDATE A BLOG
    @Override
    public ResponseEntity<Object> updateBlog(String userName, BlogUpdateRequestDTO blogUpdateRequestDTO) {
        try {
            if (!blogValidators.isValidUUID(blogUpdateRequestDTO.getBlogId())) {
                logger.error("Invalid blogId format");
                throw new BlogNotFoundException("ValidationError","Invalid blogId format");
            }
            if (!blogValidators.validateBlogUpdateDto(blogUpdateRequestDTO)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("ValidationError","Invalid BlogCreationRequestDTO");
            }
            BlogUpdateFeignDTO finalData = blogHelper.blogUpdateDtoTOFeignDto(userName, blogUpdateRequestDTO);
            return blogFeignClient.updateBlog(blogUpdateRequestDTO.getBlogId(), finalData);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while updating a blog: " + e.getMessage());
        }
    }


    // FETCH A BLOG BY ID
    @Override
    public ResponseEntity<Object> fetchBlogById(String blogId) {
        try {
            if (!blogValidators.isValidUUID(blogId)) {
                logger.error("Invalid blogId format");
                throw new BlogNotFoundException("ValidationError","Invalid blogId format");
            }
            return blogFeignClient.fetchBlogById(blogId);
        } catch (ValidationFailedException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while fetching a blog: " + e.getMessage());
        }
    }


    // FETCH ALL BLOGS
    @Override
    public ResponseEntity<Object> fetchAllBlogs(int pageNum, int pageSize) {
        try {
            return blogFeignClient.fetchAllBlogs(pageNum, pageSize);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching all blogs" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while fetching all blogs: " + e.getMessage());
        }
    }


    // DELETE A BLOG
    @Override
    public ResponseEntity<Object> deleteBlogById(String blogId, String userName) {
        try {
            if (!blogValidators.isValidUUID(blogId)) {
                logger.error("Invalid blogId format");
                throw new ValidationFailedException("ValidationError","Invalid blogId format");
            }
            if (!blogValidators.validateBlogExistence(blogId)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("ValidationError","Invalid BlogCreationRequestDTO");
            }
            return blogFeignClient.deleteBlogById(blogId, userName);
        } catch (ValidationFailedException | BlogNotFoundException e) {
            throw e;
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while deleting a blog" + e.getMessage());
            throw new UnexpectedException("UnexpectedError","An unexpected error occurred while deleting a blog: " + e.getMessage());
        }
    }
}
