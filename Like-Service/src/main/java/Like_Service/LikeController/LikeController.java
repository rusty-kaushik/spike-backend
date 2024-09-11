package Like_Service.LikeController;

import Like_Service.LikeRepository.LikeRepository;
import Like_Service.LikeService.LikeService;


import Like_Service.ResponseHandler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/spike/blog/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;



    //Put Api to Like and unlike a Post
    //require blogid and user id as parameters
    @PutMapping("/blogpost/{blogid}/{username}")
    private ResponseEntity<Object> blogLikeAndUnlike(@PathVariable("blogid") String blogid, @PathVariable("username") String username) {
       UUID blogId = UUID.fromString(blogid);
        String response= likeService.likeandUnlikepost(blogId, username);
        return ResponseHandler.response(HttpStatus.OK , response,"Success");

    }



    //api to get usernames who liked the blog
    @GetMapping("getusername/wholikedblog/{blogid}")
    public ResponseEntity<Object> getUserNamesWhoLikedBlog(@PathVariable("blogid") String blogid) {
        UUID blogId = UUID.fromString(blogid);
        //it will provide the list of usernames who liked blog with the provided blogid
        List<String> likes =likeService.getUserNames(blogId);
        return ResponseHandler.response(HttpStatus.OK, String.valueOf(likes), "usernames fetched successfully");



    }
}

