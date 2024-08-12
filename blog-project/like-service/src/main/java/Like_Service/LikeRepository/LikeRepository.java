package Like_Service.LikeRepository;

import Like_Service.LikeEntity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByBlogidAndUserid(long blogid, String userid);


    @Query("SELECT l.userid FROM Like l WHERE l.blogid =:blogid")
    List<Long> findByBlogId(@Param("blogid")long blogid);

    // List<Like> FindByBlogid(long blogid);
}
