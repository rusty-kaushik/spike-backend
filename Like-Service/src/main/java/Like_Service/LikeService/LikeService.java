package Like_Service.LikeService;

import Like_Service.LikeEntity.Like;

import java.util.List;

public interface LikeService {
    String likepost(long blogid, long userid);

    List<Long> getUserIds(long blogid);
}
