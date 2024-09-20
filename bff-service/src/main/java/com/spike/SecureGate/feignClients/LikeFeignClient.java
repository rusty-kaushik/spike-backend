package com.spike.SecureGate.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "userClient",  url = "${spike.service.like_service}")
public interface LikeFeignClient {

    // like and unlike a blog for a user
    @PutMapping("/spike/blog/like/blogpost/{blogId}/{userName}")
    ResponseEntity<Object> blogLikeAndUnlike(
            @PathVariable String blogId,
            @PathVariable String userName
    );

    // get usernames who liked a blog
    @GetMapping("/spike/blog/like/getusername/wholikedblog/{blogId}")
    ResponseEntity<Object> getUserNamesWhoLikedBlog(
            @PathVariable String blogId
    );



}
