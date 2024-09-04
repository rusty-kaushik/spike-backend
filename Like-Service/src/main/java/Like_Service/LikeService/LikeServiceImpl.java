package Like_Service.LikeService;

import Like_Service.ExceptionHandling.BlogNotFoundException;
import Like_Service.ExceptionHandling.UserNotFoundException;
import Like_Service.FeignInterface.BlogClient;
import Like_Service.LikeEntity.LikeEntity;
import Like_Service.LikeEntity.status;
import Like_Service.LikeRepository.LikeRepository;
import com.in2it.blogservice.dto.BlogDto;
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
        ResponseEntity<BlogDto> response = blogClient.getBlogById(blogId);
        BlogDto blog = response.getBody();

        if (blog == null) {
            throw new BlogNotFoundException("Blog does not exist");
        }

        LikeEntity existingLike = likeRepository.findByBlogidAndUserid(blogId, userId);
        long likeCount = blog.getLikeCount();

        if (existingLike != null) {
            if (existingLike.getStatus() == status.Liked) {
                existingLike.setStatus(status.Unliked);
                likeCount -= 1;
            } else {
                existingLike.setStatus(status.Liked);
                likeCount += 1;
            }
            existingLike.setCreatedAt(LocalDateTime.now());
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

        blogClient.updateLike(blogId, likeCount);

        return existingLike != null && existingLike.getStatus() == status.Unliked ? "User unliked this blog" : "User liked this blog";
    }


    @Override
    public List<UUID> getUserIds(UUID blogid) {
        List<UUID> userids = likeRepository.findByBlogId(blogid);
        if (userids.isEmpty()) {
            throw new UserNotFoundException("no user has liked this blog");
        }
        return userids;
    }
}

