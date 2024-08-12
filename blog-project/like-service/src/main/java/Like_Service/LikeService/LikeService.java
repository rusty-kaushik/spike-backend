package Like_Service.LikeService;

import java.util.List;

public interface LikeService {
	  public String likepost(long blogid, String userid);

    List<Long> getUserIds(long blogid);
}
