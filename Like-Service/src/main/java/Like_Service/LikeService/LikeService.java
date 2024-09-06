package Like_Service.LikeService;

import Like_Service.LikeEntity.LikeEntity;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    String likeandUnlikepost(UUID blogid, long userid);

    List<Long> getUserIds(UUID blogid);
}
