package com.spike.SecureGate.Validations;

import com.spike.SecureGate.feignClients.BlogFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class LikeValidator {

    @Autowired
    private BlogFeignClient blogFeignClient;

    public boolean likeOrUnlikeABlogValidation(String blogId) {
        ResponseEntity<Object> responseEntity = blogFeignClient.fetchBlogById(blogId);
        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
}
