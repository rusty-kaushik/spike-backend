package Like_Service.LikeController;

import Like_Service.ExceptionHandling.BlogNotFoundException;
import Like_Service.ExceptionHandling.UserNotFoundException;
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
    //require blogid and username as parameters
    @PutMapping("/blogpost/{blogId}/{userName}")
    public ResponseEntity<Object> blogLikeAndUnlike(@PathVariable("blogId") String blogid, @PathVariable("userName") String username) {

        try {
            UUID blogId = UUID.fromString(blogid);
            String response = likeService.likeandUnlikepost(blogId, username);
            return ResponseHandler.response(HttpStatus.OK, response, "Success");
        } catch(RuntimeException ex){
            throw(ex);
        }
        catch (Exception ex) {
            throw new RuntimeException("An Unexpected error occurred", ex);
        }
    }


    //api to get usernames who liked the blog
    @GetMapping("getusername/wholikedblog/{blogId}")
    public ResponseEntity<Object> getUserNamesWhoLikedBlog(@PathVariable("blogId") String blogid) {
        try {
            UUID blogId = UUID.fromString(blogid);
            //it will provide the list of usernames who liked blog with the provided blogid
            List<String> likes = likeService.getUserNames(blogId);
            return ResponseHandler.response(HttpStatus.OK, String.valueOf(likes), "usernames fetched successfully");
        } catch (UserNotFoundException ex) {
            throw (ex);
        } catch (Exception ex) {
            throw new RuntimeException("An Unexpected error occurred", ex);

        }
    }

    //delete api to unlike all the likes on deleted blog
    @DeleteMapping("/unlike/deleted-blog/{blogId}")
    public ResponseEntity<Object> unlikeDeletedBlog(@RequestParam("blogId") String blogId) {
        try {
            boolean deleted = likeService.unlikeDeletedBlog(blogId);
            return ResponseHandler.response(HttpStatus.OK, "Unlike a deleted blog successfully", deleted);
        }
        catch(BlogNotFoundException ex ){
            throw(ex);
        } catch(Exception ex) {
            throw new RuntimeException("An Unexpected error occurred", ex);
        }
    }

}

