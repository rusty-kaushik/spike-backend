package Like_Service.FeignInterface;

import com.in2it.blogservice.dto.BlogDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="blog-service" , url="http://localhost:8282/spike/blog")
public interface BlogClient {

    //get api to get  blog by using feign client
    @GetMapping("/getByBlogId/{blogId}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable(value = "blogId") @Valid Long blogId) ;

   //put api to update like count in blog
    @PutMapping("/updateLike")
    public ResponseEntity<BlogDto> updateLike(@RequestParam Long blogId, @RequestParam Long totalLikeCount);


}
