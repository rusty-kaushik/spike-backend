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
    @PutMapping("/post/{blogid}/{userid}")
    private ResponseEntity<Object> blogLikeAndUnlike(@PathVariable("blogid") UUID blogid, @PathVariable("userid") long userid) {
        String response= likeService.likeandUnlikepost(blogid, userid);
    return ResponseHandler.response(HttpStatus.OK , response,"Success");

    }




    //api to get userids who liked the blog
    @GetMapping("getuserids/wholikedblog/{blogid}")
    public ResponseEntity<Object> getUserIdsWhoLikedBlog(@PathVariable("blogid") UUID blogid) {
        //it will provide the list of userids who liked blog with the provided blogid
        List<Long> likes =likeService.getUserIds(blogid);
     return ResponseHandler.response(HttpStatus.OK, String.valueOf(likes), "userid fetched successfully");



}
}

