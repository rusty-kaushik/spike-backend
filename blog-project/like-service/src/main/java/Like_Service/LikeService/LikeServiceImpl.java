package Like_Service.LikeService;

import Like_Service.ExceptionHandling.BlogNotFoundException;
import Like_Service.ExceptionHandling.UserNotFoundException;
import Like_Service.FeignInterface.BlogClient;
import Like_Service.LikeEntity.Like;
import Like_Service.LikeRepository.LikeRepository;
import com.in2it.blogservice.dto.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BlogClient feign;

    @Override
    public String likepost(long blogid, String userid) {
        ResponseEntity<BlogDto> response = feign.getBlogById(blogid);
        BlogDto blog = response.getBody();
        //it checks if blog exist or not
        if (blog == null) {
            throw new BlogNotFoundException("Blog does not exist");
        }

        Like existingLike = likeRepository.findByBlogidAndUserid(blogid, userid);
        if (existingLike != null) {
            likeRepository.delete(existingLike);
            long likeCount = blog.getLikeCount();
            if(likeCount>0) {
                long decrementLikeCount = likeCount - 1;
                feign.updateLike(blogid, decrementLikeCount);

            }
            return "user unliked this blog";
        } else {
            Like like = new Like();
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
           throw new UserNotFoundException("user not found");
        }
        return userids;
    }


}




