package com.in2it.commentService.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.in2it.commentService.model.Comment;

//@Repository
//public interface CommentRepository extends JpaRepository<Comment, UUID> {
//
//	@Query(value= "select * from comment where status='Active' and  blog_id=%:blogId%", nativeQuery = true)
//	List<Comment> findByBlogId(UUID blogId);
//	@Query(value= "select * from comment where status='Active' and  author_id=%:authorId%", nativeQuery = true)
//	List<Comment> findByAuthorId(String authorId);
////	@Modifying
////	@Query(value= "update  comment set  status='InActive' where  c_id=%:id%", nativeQuery = true)
////	void deleteById(Long id);
////	@Modifying
//	@Query(value= "select * from comment where status='Active' and c_id=?1", nativeQuery = true)
//	Optional<Comment>  findByCId(UUID cId);
////	Optional<Comment> findByStatusAndCId(String status, UUID cId);
//}

@Repository
public interface CommentRepository extends MongoRepository<Comment, String>
{
	List<Comment> findByStatusAndBlogId(String status, String blogId);
	List<Comment> findByAuthorIdAndStatus(String authorId, String status);
	Optional<Comment> findByIdAndStatus(String id, String status);
}
