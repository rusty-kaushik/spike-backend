package com.in2it.commentandlikeservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.in2it.commentandlikeservice.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

//	@Query("{ 'blogId': ?0, 'status': ?1 }")
	List<Comment> findByBlogIdAndStatus(String blogId, String status);

	List<Comment> findByUserName(String userName);
	
	Optional<Comment> findByIdAndStatus(String id, String status);

//	Comment findByIdAndStatus(String id, String status);

	List<Comment> findByStatus(String status);
	

}
