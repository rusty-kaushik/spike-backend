package com.in2it.blogmongo.service;

import java.nio.file.NoSuchFileException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.in2it.blogmongo.model.Like;

@Component
public interface LikeService {
	
	Like addLike(Like like);
	List<Like> getAllLikes();
	
	Like removeLike(String likeId);
	
	List<Like> removelikeByAuthorId(String authorId);
	
	
	Like likeBlog(String authorId, String blogId, String type) throws NoSuchFileException;
	

}
