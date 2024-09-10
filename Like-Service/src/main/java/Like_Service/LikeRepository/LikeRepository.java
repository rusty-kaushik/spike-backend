package Like_Service.LikeRepository;


import Like_Service.LikeEntity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {
    LikeEntity findByBlogidAndUserName( UUID blogid, String username);


    @Query("SELECT l.userName FROM LikeEntity l WHERE l.blogid =:blogid")
    List<String> findByBlogId(@Param("blogid")UUID blogid);

}
