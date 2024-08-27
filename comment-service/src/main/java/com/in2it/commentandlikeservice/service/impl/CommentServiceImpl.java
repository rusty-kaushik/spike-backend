package com.in2it.commentandlikeservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogservice.dto.BlogDto;
import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.dto.CommentUpdateDto;
import com.in2it.commentandlikeservice.feign.BlogFeign;
import com.in2it.commentandlikeservice.mapper.CommentConvertor;
import com.in2it.commentandlikeservice.model.Comment;
import com.in2it.commentandlikeservice.payload.BlogNotFoundException;
import com.in2it.commentandlikeservice.payload.IdInvalidException;
import com.in2it.commentandlikeservice.payload.UserNotFoundException;
import com.in2it.commentandlikeservice.repository.CommentRepository;
import com.in2it.commentandlikeservice.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentConvertor objectMapper;
	@Autowired
	private BlogFeign feign;

	public CommentDto saveComment(CommentDto commentDto, Long blogId, List<MultipartFile> file) {

		ResponseEntity<BlogDto> response = feign.getBlogById(blogId);
		BlogDto blog = response.getBody();
		// it checks if blog exist or not
		if (blog == null) {
			throw new BlogNotFoundException("Blog does not exist");
		}

		Comment comment = objectMapper.dtoToCommentConvertor(commentDto);
		
	
		Comment com = commentRepository.save(comment);
	
		com.setCreatedDate(LocalDateTime.now());
//		com.setMediaMap(com.getMedia(),com.getMediaPath());
		long incrementcount = blog.getCommentCount() + 1;

		
		feign.updateComment(blogId, incrementcount);
		CommentDto dto = objectMapper.commentToDtoConvertor(comment);

		return dto;

	}

	// This method is used to update BLOG with Blog_id with limited permissions
	@Override
	public CommentDto updateComment(CommentUpdateDto updateDto) {

		Comment comment = null;

		comment = commentRepository.findById(updateDto.getId()).get();

		if (comment != null && comment.getId() == updateDto.getId()
				&& comment.getBlogId() == updateDto.getBlogId()) {

			if (updateDto.getContent() != null)
				comment.setContent(updateDto.getContent());

			comment.setUpdatedDateTime(LocalDateTime.now());
			comment.setUpdatedBy(comment.getAuthorId());
			System.out.println(comment+"+++++++comment");
			return objectMapper.commentToDtoConvertor(commentRepository.save(comment));
		} else {
			throw new UserNotFoundException(
					" Insufficient information, Please ! try again with sufficient information.");
		}

	}

	public Comment getByCommentId(UUID commentId) {
		Comment com = commentRepository.findById(commentId).get();
		return com;

	}

	public List<CommentDto> getByBlogId(Long blogId) {
		List<CommentDto> commentListDto = new ArrayList<>();
		try {
		List<Comment> commentList = commentRepository.findByBlogId(blogId);
		
		for (Comment com : commentList) {
//			if (com != null) {
				CommentDto commentDtoConvertor = objectMapper.commentToDtoConvertor(com);
				System.out.println(commentDtoConvertor);
				System.out.println(com+"++++++++++++++");
				commentListDto.add(commentDtoConvertor);
//			}
		}}
		catch(Exception e) {
			e.printStackTrace();
		}

		return commentListDto;
	}

	public List<CommentDto> getByUserName(String usename) {
		List<Comment> commentList = commentRepository.findByAuthorId(usename);
		List<CommentDto> commentListDto = new ArrayList<>();
		for (Comment com : commentList) {
			if (com != null) {
				CommentDto commentDtoConvertor = objectMapper.commentToDtoConvertor(com);
				commentListDto.add(commentDtoConvertor);
			}
		}
		return commentListDto;
	}

	public Boolean deleteCommentId(Long id) {

		if (id > 0) {
			commentRepository.deleteById(id);
			return true;
		} else {

			throw new IdInvalidException(HttpStatus.NO_CONTENT + "id not found, Please ! enter correct id.");
		}
	}

	public List<Comment> deleteByBlogId(Long blogId, UUID commentId) {

		ResponseEntity<BlogDto> response = feign.getBlogById(blogId);
		BlogDto blog = response.getBody();
		// it checks if blog exist or not
		if (blog == null) {
			throw new BlogNotFoundException("Blog does not exist");
		}
		List<Comment> comments = commentRepository.findByBlogId(blogId);
		List<Comment> deleteComments = new ArrayList<>();
		Comment commentByCommentId = commentRepository.findById(commentId).get();

		for (Comment com : comments) {
			if (commentByCommentId.getId() == com.getId()) {

				com.setStatus("InActive");

				Comment c = commentRepository.save(com);
				c.setUpdatedDateTime(LocalDateTime.now());
				c.setDeletedBy(c.getAuthorId());
				long decrementcount = blog.getCommentCount() - 1;

				feign.updateComment(blogId, decrementcount);

				deleteComments.add(c);
			}
		}
		return deleteComments;
	}

}
