package com.in2it.commentandlikeservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.in2it.commentandlikeservice.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

//	@Query("{ 'blogId': ?0, 'status': ?1 }")
	List<Comment> findByBlogIdAndStatus(UUID blogId, String status);

	List<Comment> findByUserName(String userName);

	Comment findByIdAndStatus(String id, String status);

	List<Comment> findByStatus(String status);

}
