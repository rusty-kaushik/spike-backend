package Like_Service.LikeRepository;


import Like_Service.LikeEntity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.blogid = :blogid AND l.status = 'Liked'")
    Long countLikesByBlogId(@Param("blogid") UUID blogid);

    LikeEntity findByBlogidAndUserName(UUID blogid, String username);


    @Query("SELECT l.userName FROM LikeEntity l WHERE l.blogid =:blogid AND l.status = 'Liked'")
    List<String> findByBlogId(@Param("blogid") UUID blogid);

    @Query(" FROM LikeEntity WHERE blogid=:blogid AND status='Liked'")
    List<LikeEntity> findALLByBlogId(UUID blogid);
}
