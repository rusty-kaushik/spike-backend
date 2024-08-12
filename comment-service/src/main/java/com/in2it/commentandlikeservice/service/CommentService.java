package com.in2it.commentandlikeservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.model.Comment;

public interface CommentService {

	public CommentDto saveComment(CommentDto commentDto, List<MultipartFile> file);
//	public Comment saveComment(CommentDto commentDto, MultipartFile file);
//	public List<Comment> getAllComment();
//	public List<CommentDto> getAllComment();
	public List<CommentDto> getByBlogId(Long id);
	public List<CommentDto> getByUserName(String usename);
	public Boolean deleteCommentId(Long id);
//	public List<Comment> deleteByBlogId(Long id);
	public List<Comment> deleteByBlogId(Long id,Long commentId);
}
