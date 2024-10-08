package com.in2it.commentandlikeservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.in2it.commentandlikeservice.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

	List<Comment> findByBlogIdAndStatus(String blogId, String status);
	Page<Comment> findByBlogIdAndStatus(String blogId, String status,Pageable p);
	List<Comment> findByUserName(String userName);

	Optional<Comment> findByIdAndStatus(String id, String status);

	Page<Comment> findByStatus(String status, Pageable pageable);

}
