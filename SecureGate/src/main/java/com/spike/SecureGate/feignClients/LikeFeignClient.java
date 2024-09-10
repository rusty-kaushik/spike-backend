package com.spike.SecureGate.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "userClient",  url = "${spike.service.like_service}")
public interface LikeFeignClient {

}
