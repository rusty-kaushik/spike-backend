package Like_Service.LikeService;

import Like_Service.ExceptionHandling.BlogNotFoundException;
import Like_Service.ExceptionHandling.UserNotFoundException;
import Like_Service.FeignInterface.BlogClient;
import Like_Service.LikeEntity.LikeEntity;
import Like_Service.LikeRepository.LikeRepository;
import com.in2it.blogservice.dto.BlogDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    ;
    @Autowired
    private BlogClient feign;

    @Override
    public String likeandUnlikepost(long blogid, long userid) {
        ResponseEntity<BlogDto> response = feign.getBlogById(blogid);
        BlogDto blog = response.getBody();
        //it checks if blog exist or not
        if (blog == null) {
            throw new BlogNotFoundException("Blog does not exist");
        }

        LikeEntity existingLike = likeRepository.findByBlogidAndUserid(blogid, userid);
        if (existingLike != null) {
            likeRepository.delete(existingLike);
            long likeCount = blog.getLikeCount();
            if(likeCount>0) {
                long decrementLikeCount = likeCount - 1;
                feign.updateLike(blogid, decrementLikeCount);

            }
            return "user unliked this blog";
        } else {
            LikeEntity like = new LikeEntity();
            like.setBlogid(blogid);
            like.setUserid(userid);
            likeRepository.save(like);
            long likeCount = blog.getLikeCount();
            long incrementLikeCount = likeCount + 1;
            feign.updateLike(blogid, incrementLikeCount);
            return "user liked this blog";
        }

    }

    @Override
    public List<Long> getUserIds(long blogid) {
        List<Long> userids = likeRepository.findByBlogId(blogid);
        if(userids.isEmpty()){
           throw new UserNotFoundException("no user has liked this blog");
        }
        return userids;
    }


}




