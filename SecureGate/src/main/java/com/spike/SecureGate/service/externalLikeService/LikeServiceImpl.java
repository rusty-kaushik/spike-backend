package com.spike.SecureGate.service.externalLikeService;

import com.spike.SecureGate.Validations.LikeValidator;
import com.spike.SecureGate.controllers.LikeController;
import com.spike.SecureGate.exceptions.BlogNotFoundException;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.feignClients.LikeFeignClient;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private static final Logger logger = LoggerFactory.getLogger(LikeServiceImpl.class);

    @Autowired
    private LikeFeignClient likeFeignClient;

    @Autowired
    private LikeValidator likeValidator;

    @Override
    public ResponseEntity<Object> likeOrUnlikeABlog(String userName, String blogId) {
        try {
            if (!likeValidator.likeOrUnlikeABlogValidation(blogId)) {
                logger.error("Invalid blogId");
                throw new BlogNotFoundException("Invalid blogId");
            }
            return likeFeignClient.blogLikeAndUnlike(blogId,userName);
        } catch (BlogNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while liking or unliking a blog: {}", e.getMessage());
            throw new UnexpectedException("An unexpected error occurred while liking or unliking a blog: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> listOfUsersWhoLikedBlog(String blogId) {
        try {
            if (!likeValidator.likeOrUnlikeABlogValidation(blogId)) {
                logger.error("Invalid blogId");
                throw new BlogNotFoundException("Invalid blogId");
            }
            return likeFeignClient.getUserNamesWhoLikedBlog(blogId);
        } catch (BlogNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching the list of users: {}", e.getMessage());
            throw new UnexpectedException("An unexpected error occurred while fetching the list of users: " + e.getMessage());
        }
    }


}
