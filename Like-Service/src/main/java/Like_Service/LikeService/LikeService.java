package Like_Service.LikeService;

import Like_Service.LikeEntity.LikeDto;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    String likeandUnlikepost(UUID blogid, String username);

    List<LikeDto> getUserNames(UUID blogid);

    boolean unlikeDeletedBlog(String blogId);
}
