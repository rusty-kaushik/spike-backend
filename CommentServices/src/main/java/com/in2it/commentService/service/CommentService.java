package com.in2it.commentService.service;

import java.util.List;

import com.in2it.commentService.dto.CommentDeleteDto;
import com.in2it.commentService.dto.CommentDto;
import com.in2it.commentService.dto.CommentUpdateDto;
import com.in2it.commentService.dto.CreateCommentDto;

public interface CommentService {

	CommentDto saveComment(CreateCommentDto commentDto);
	List<CommentDto> getCommentsByBlogId(String bId);
	List<CommentDto> getCommentsByAutherId(String usename);
	Boolean deleteCommentById(CommentDeleteDto deleteDto);
	CommentDto updateComment(CommentUpdateDto dto);
}
