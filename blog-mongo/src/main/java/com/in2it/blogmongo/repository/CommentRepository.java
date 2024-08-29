package com.in2it.blogmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.in2it.blogmongo.model.Comment;
import java.util.List;


@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
	
	List<Comment> findByStatus(String status);
	List<Comment> findByStatusAndAuthorid(String status, String authorid);
	List<Comment> findByStatusAndBlogId(String status, String blogId);

}
