package com.in2it.commentandlikeservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.dto.CommentUpdateDto;
import com.in2it.commentandlikeservice.model.Comment;

public interface CommentService {

	public CommentDto saveComment(CommentDto commentDto, Long blogid, List<MultipartFile> file);

//	public List<CommentDto> getAllComment();
	public List<CommentDto> getByBlogId(Long blogId);

	public CommentDto updateComment(CommentUpdateDto updateDto);

	public List<CommentDto> getByUserName(String usename);

	public Boolean deleteCommentId(Long id);

//	public List<Comment> deleteByBlogId(Long id);
	public List<Comment> deleteByBlogId(Long id, UUID commentId);

}
