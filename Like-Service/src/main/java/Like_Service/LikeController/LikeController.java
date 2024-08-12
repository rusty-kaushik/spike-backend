package Like_Service.LikeController;

import Like_Service.LikeRepository.LikeRepository;
import Like_Service.LikeService.LikeService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/in2it/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

//    @Autowired
//    private UserClient userClient;

    //Put Api to Like and unlike a Post
    //require blogid and user id as parameters
    @PutMapping("/post/{blogid}/{userid}")
    private ResponseEntity<String> blogLike(@PathVariable("blogid") long blogid, @PathVariable("userid") long userid) {
        String response= likeService.likepost(blogid, userid);
    return ResponseEntity.ok().body(response);

    }




    //api to get userids who liked the blog
    @GetMapping("getusernames/wholikedblog/{blogid}")
    public ResponseEntity<List<Long>> getUsernamesWhoLikedBlog(@PathVariable("blogid") long blogid) {
        //it will provide the list of userids who liked blog with the provided blogid
        List<Long> likes =likeService.getUserIds(blogid);
      return ResponseEntity.status(HttpStatus.OK).body(likes);


}
}

