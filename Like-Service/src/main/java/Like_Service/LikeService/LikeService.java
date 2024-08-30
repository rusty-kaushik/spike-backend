package Like_Service.LikeService;

import Like_Service.LikeEntity.LikeEntity;

import java.util.List;

public interface LikeService {
    String likeandUnlikepost(long blogid, long userid);

    List<Long> getUserIds(long blogid);
}
