package com.in2it.blogmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.in2it.blogmongo.model.Like;
import java.util.List;


@Repository
public interface LikeRepository extends MongoRepository<Like, String>{
	
	 List<Like> findLikeByAuthorIdAndBlogId(String authorId, String blogId);
	 List<Like> findByAuthorId(String authorId);

}
