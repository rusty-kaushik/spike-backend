package Like_Service.FeignInterface;

import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.reponse.ResponseHandler;
import jakarta.validation.Valid;
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

    // Fetch the blog details by blog ID
    @GetMapping("/getByBlogId/{blogId}")
    public ResponseEntity<ResponseHandler<BlogDto>> getBlogById(@PathVariable(value = "blogId") @Valid String blogId);


    @PutMapping("/updateLike")
    public ResponseEntity<ResponseHandler<BlogDto>> updateLike(@RequestParam String blogId, @RequestParam Long totalLikeCount);
}
