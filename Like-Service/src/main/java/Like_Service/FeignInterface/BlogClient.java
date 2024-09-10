package Like_Service.FeignInterface;


import Like_Service.BlogDto.BlogDto;


import Like_Service.ResponseHandler.ResponseHandler;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

//localhost:8282/spike/blog
@FeignClient(name = "blog-service", url = "${feign.client.url}")
public interface BlogClient {

    @PutMapping("/updateLike")
    public ResponseEntity<ResponseHandler<BlogDto>> updateLike(@RequestParam String blogId,
                                                               @RequestParam Long totalLikeCount);
}
