package Like_Service.LikeService;

import Like_Service.LikeEntity.LikeEntity;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    String likeandUnlikepost(UUID blogid, String username);

    List<String> getUserNames(UUID blogid);

    boolean unlikeDeletedBlog(String blogId);
}
