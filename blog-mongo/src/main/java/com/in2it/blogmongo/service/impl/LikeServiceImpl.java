package com.in2it.blogmongo.service.impl;

import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in2it.blogmongo.dto.BlogDto;
import com.in2it.blogmongo.model.Blog;
import com.in2it.blogmongo.model.Like;
import com.in2it.blogmongo.repository.BlogRepository;
import com.in2it.blogmongo.repository.LikeRepository;
import com.in2it.blogmongo.service.BlogService;
import com.in2it.blogmongo.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	LikeRepository repository;
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	BlogService blogService;

	@Override
	public Like addLike(Like like) {
		return repository.save(like);
	}

	@Override
	public List<Like> getAllLikes() {
		
		return repository.findAll();
	}

	@Override
	public Like likeBlog(String authorId, String blogId, String type) throws NoSuchFileException {
//		repository.findLikeByAuthorIdAndBlogId(like.getAuthorId(), like.getAuthorId()).isEmpty();
		List<Like> likeByAuthorIdAndBlogId = repository.findLikeByAuthorIdAndBlogId(authorId, blogId);
	
		
		if(likeByAuthorIdAndBlogId.isEmpty()) {
			
			Blog blog = blogRepository.findById(blogId).orElseThrow(()-> new NoSuchFileException("No such element Found"));
			
			List<Like> likes = blog.getLikes();
			
			Like like = Like.builder().authorId(authorId).blogId(blogId).type(type).createdAt(LocalDateTime.now()).build();
			
//			like.setCreatedAt(LocalDateTime.now());
			Like like2 = repository.save(like);
			likes.add(like2);
			blog.setLikes(likes);
			int likeCount = blog.getLikesCount()+1;
			blog.setLikesCount(likeCount);
			blogRepository.save(blog);
			return like2;
		}
		return null;
		
		
	}

	@Override
	public Like removeLike(String likeId) {
		Like like = repository.findById(likeId).orElseThrow(()-> new RuntimeException("Like Dosen't exist with given id.."));
		Blog blog = blogRepository.findById(like.getBlogId()).orElseThrow(()-> new RuntimeException("Blog Dosen't exist with given id.."));
		List<Like> likes = blog.getLikes();
		likes.remove(like);
		blog.setLikes(likes);
		blog.setLikesCount(blog.getLikesCount()-1);
		blogRepository.save(blog);
		
		return like;
	}

	@Override
	public List<Like> removelikeByAuthorId(String authorId) {
		List<Like> likes = repository.findByAuthorId(authorId);
		for (Like like : likes) {
			Blog blog = blogRepository.findById(like.getBlogId()).orElseThrow(()-> new RuntimeException("Blog Dosen't exist with given id.."));
			List<Like> likes2 = blog.getLikes();
			likes.remove(like);
			blog.setLikes(likes2);
			blog.setLikesCount(blog.getLikesCount()-1);
			blogRepository.save(blog);
			
		}
		return likes;
	}

	

	
}
