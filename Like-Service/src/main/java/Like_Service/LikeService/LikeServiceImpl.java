package Like_Service.LikeService;

import Like_Service.BlogDto.BlogDto;
import Like_Service.ExceptionHandling.BlogNotFoundException;
import Like_Service.ExceptionHandling.UserNotFoundException;
import Like_Service.FeignInterface.BlogClient;
import Like_Service.LikeEntity.LikeEntity;
import Like_Service.LikeEntity.status;
import Like_Service.LikeRepository.LikeRepository;


import Like_Service.ResponseHandler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BlogClient blogClient;



    @Override

    public String likeandUnlikepost(UUID blogId, String userName) {
        // Fetch blog details from the blog service
        try {
            Long LikeCount = likeRepository.countLikesByBlogId(blogId);


            // Check if the user has already liked/disliked this blog
            LikeEntity existingLike = likeRepository.findByBlogidAndUserName(blogId, userName);

            if (existingLike != null) {

                if (existingLike.getStatus() == status.Liked) {
                    // If already liked, switch to disliked and decrement like count
                    existingLike.setStatus(status.Unliked);
                    LikeCount = Math.max(0, LikeCount - 1);
                    likeRepository.save(existingLike);
                    updateBlogLikeCount(blogId, LikeCount);
                    return "User unliked this blog";
                } else {
                    // If disliked, switch to liked and increment like count
                    existingLike.setStatus(status.Liked);
                    LikeCount += 1;

                    likeRepository.save(existingLike);
                    updateBlogLikeCount(blogId, LikeCount);
                    return "User liked this blog";
                }
            } else {
                LikeEntity newLike = new LikeEntity();
                newLike.setBlogid(blogId);
                newLike.setUserName(userName);
                newLike.setCreatedAt(LocalDateTime.now());
                newLike.setStatus(status.Liked);
                likeRepository.save(newLike);
                LikeCount += 1;
                updateBlogLikeCount(blogId, LikeCount);
                return "User liked this blog";
            }
        }catch(Exception ex){
            throw new RuntimeException("Failed to like/unlike post", ex);
        }
    }

    public void updateBlogLikeCount(UUID blogId, long LikeCount) {
        try {
            String blogid= blogId.toString();
            blogClient.updateLike(blogid, LikeCount);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update like count for blogId " + blogId, e);
        }
    }

    @Override
    public List<String> getUserNames(UUID blogid) {
        List<String> userNames = likeRepository.findByBlogId(blogid);
        if (userNames.isEmpty()) {
            throw new UserNotFoundException("No user has liked this blog");
        }
        return userNames;
    }



}
