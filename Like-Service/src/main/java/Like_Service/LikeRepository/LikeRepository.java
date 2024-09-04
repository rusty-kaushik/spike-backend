package Like_Service.LikeRepository;


import Like_Service.LikeEntity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {
    LikeEntity findByBlogidAndUserid( UUID blogid, long userid);


    @Query("SELECT l.userid FROM LikeEntity l WHERE l.blogid =:blogid")
    List<Long> findByBlogId(@Param("blogid")UUID blogid);

}
