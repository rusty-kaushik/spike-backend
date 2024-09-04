package Like_Service.FeignInterface;

import com.in2it.blogservice.dto.BlogDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name="blog-service" , url = "${feign.client.url}")
public interface BlogClient {

    //get api to get  blog by using feign client
    @GetMapping("/getByBlogId/{blogId}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable(value = "blogId") @Valid UUID blogId) ;

   //put api to update like count in blog
    @PutMapping("/updateLike")
    public ResponseEntity<BlogDto> updateLike(@RequestParam  UUID blogId, @RequestParam Long totalLikeCount);


}
