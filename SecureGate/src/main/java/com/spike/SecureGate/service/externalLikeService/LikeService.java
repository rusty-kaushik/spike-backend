package com.spike.SecureGate.service.externalLikeService;

import org.springframework.http.ResponseEntity;

public interface LikeService {


    ResponseEntity<Object> likeOrUnlikeABlog(String userName, String blogId);

    ResponseEntity<Object> listOfUsersWhoLikedBlog(String blogId);
}
