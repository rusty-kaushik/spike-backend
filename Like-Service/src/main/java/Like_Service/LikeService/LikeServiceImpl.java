package Like_Service.LikeService;

import Like_Service.ExceptionHandling.BlogNotFoundException;
import Like_Service.ExceptionHandling.UserNotFoundException;
import Like_Service.FeignInterface.BlogClient;
import Like_Service.LikeEntity.LikeEntity;
import Like_Service.LikeEntity.status;
import Like_Service.LikeRepository.LikeRepository;
import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.reponse.ResponseHandler;
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

    public String likeandUnlikepost(UUID blogId, long userId) {
        // Fetch blog details from the blog service
        ResponseEntity<ResponseHandler<BlogDto>> response = blogClient.getBlogById(blogId);
        BlogDto blog = response.getBody().getData();
        System.out.println(blog);

        if (blog == null) {
            throw new BlogNotFoundException("Blog does not exist");
        }

        long likeCount = blog.getLikeCount();
        System.out.println("Initial Like Count: " + likeCount);

        // Check if the user has already liked/disliked this blog
        LikeEntity existingLike = likeRepository.findByBlogidAndUserid(blogId, userId);

        if (existingLike != null) {

            if (existingLike.getStatus() == status.Liked) {
                // If already liked, switch to disliked and decrement like count
                existingLike.setStatus(status.Unliked);
                likeCount = Math.max(0, likeCount - 1);
            } else {
                // If disliked, switch to liked and increment like count
                existingLike.setStatus(status.Liked);
                likeCount += 1;
            }
            likeRepository.save(existingLike);
        } else {
            LikeEntity newLike = new LikeEntity();
            newLike.setBlogid(blogId);
            newLike.setUserid(userId);
            newLike.setCreatedAt(LocalDateTime.now());
            newLike.setStatus(status.Liked);
            likeRepository.save(newLike);
            likeCount += 1;
        }

        System.out.println("Updating like count to " + likeCount + " for blogId " + blogId);

        updateBlogLikeCount(blogId, likeCount);

        return (existingLike != null && existingLike.getStatus() == status.Liked) ?
                "User unliked this blog" : "User liked this blog";
    }



    public void updateBlogLikeCount(UUID blogId, long likeCount) {
        try {
            blogClient.updateLike(blogId, likeCount);
            System.out.println("Successfully updated like count to " + likeCount + " for blogId " + blogId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update like count for blogId " + blogId, e);
        }
    }

    @Override
    public List<Long> getUserIds(UUID blogid) {
        List<Long> userids = likeRepository.findByBlogId(blogid);
        if (userids.isEmpty()) {
            throw new UserNotFoundException("No user has liked this blog");
        }
        return userids;
    }
}
