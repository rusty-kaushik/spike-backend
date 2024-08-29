package com.in2it.blogmongo.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogmongo.dto.CommentDto;
import com.in2it.blogmongo.model.Comment;

@Component
public interface CommentService {

	Comment addComment(Comment comment);
	List<CommentDto> getAllComments();
	List<CommentDto> getAllActiveComments();
	List<CommentDto> getCommentsByBlogId(String blogId);
	List<CommentDto> getCommentsByAuthorId(String authorId);
	
	CommentDto deleteComment(String id);
	List<CommentDto> deleteCommentsByBlogId(String blogId);
	List<CommentDto> deleteCommentsByAuthorId(String authorId);
	
	CommentDto commentOnBlog(String content, MultipartFile media, String blogId, String authorid);
	
}
