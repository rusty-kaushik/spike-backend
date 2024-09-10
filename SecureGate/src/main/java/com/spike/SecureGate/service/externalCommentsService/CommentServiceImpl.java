package com.spike.SecureGate.service.externalCommentsService;

import com.spike.SecureGate.DTO.commentDto.CommentCreationFeignDTO;
import com.spike.SecureGate.DTO.commentDto.CommentCreationRequestDTO;
import com.spike.SecureGate.DTO.commentDto.CommentUpdateRequestDTO;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.Validations.BlogValidators;
import com.spike.SecureGate.Validations.CommentValidator;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.exceptions.ValidationFailedException;
import com.spike.SecureGate.feignClients.CommentFeignClient;
import com.spike.SecureGate.helper.CommentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentValidator commentValidator;

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private CommentHelper commentHelper;

    @Autowired
    private BlogValidators blogValidators;

    @Autowired
    private CommentFeignClient commentFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public ResponseEntity<Object> createNewComment(String blogId, String userName, CommentCreationRequestDTO commentCreationRequestDTO) {
        try {
            if (!blogValidators.validateBlogExistence(blogId)) {
                logger.error("Blog do not exists");
                throw new ValidationFailedException("Blog do not exists");
            }
            if (!commentValidator.validateCommentCreationDto(commentCreationRequestDTO)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("Invalid BlogCreationRequestDTO");
            }
            CommentCreationFeignDTO finalData = commentHelper.commentCreationDtoTOFeignDto(userName, commentCreationRequestDTO);
            return commentFeignClient.createNewComment(blogId, finalData);
        } catch ( ValidationFailedException e ) {
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while creating comment", e);
            throw new UnexpectedException("An unexpected error occurred while creating a comment: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateComment(String commentId, CommentUpdateRequestDTO commentUpdateRequestDTO) {
        try{
            if (!commentValidator.validateCommentUpdateDto(commentUpdateRequestDTO)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("Invalid CommentUpdateRequestDTO");
            }
            return commentFeignClient.UpdateComment(commentUpdateRequestDTO,commentId);
        } catch ( ValidationFailedException e ) {
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while updating comment", e);
            throw new UnexpectedException("An unexpected error occurred while updating a comment: " + e.getMessage());
        }

    }

    @Override
    public ResponseEntity<Object> getAllCommentsOfABlog(String blogId) {
        try {
            if (!blogValidators.validateBlogExistence(blogId)) {
                logger.error("Blog do not exists");
                throw new ValidationFailedException("Blog do not exists");
            }
            return commentFeignClient.getAllCommentsOfBlog(blogId);
        } catch ( ValidationFailedException e ) {
            throw e;
        } catch ( Exception e ) {
            logger.error("Error occurred while getting comments", e);
            throw new UnexpectedException("An unexpected error occurred while getting comments: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getCommentByCommentId(String commentId) {
        try{
            return commentFeignClient.getCommentByCommentId(commentId);
        } catch (Exception e) {
            logger.error("Error occurred while getting comment", e);
            throw new UnexpectedException("An unexpected error occurred while getting a comment: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteComment(String blogId, String commentId) {
        try {
            if (!blogValidators.validateBlogExistence(blogId)) {
                logger.error("Validation failed");
                throw new ValidationFailedException("Invalid BlogCreationRequestDTO");
            }
            return commentFeignClient.deleteCommentByBlogId(blogId, commentId);
        } catch(ValidationFailedException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while deleting comment", e);
            throw new UnexpectedException("An unexpected error occurred while deleting a comment: " + e.getMessage());
        }
    }


}
