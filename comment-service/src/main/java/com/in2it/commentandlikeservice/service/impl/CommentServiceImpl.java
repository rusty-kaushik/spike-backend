package com.in2it.commentandlikeservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.commentandlikeservice.dto.BlogDto;
import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.dto.CommentUpdateDto;
import com.in2it.commentandlikeservice.exception.BlogNotFoundException;
import com.in2it.commentandlikeservice.exception.CommentNotFoundException;
import com.in2it.commentandlikeservice.exception.ServiceDownException;
import com.in2it.commentandlikeservice.feign.BlogFeign;
import com.in2it.commentandlikeservice.mapper.CommentConvertor;
import com.in2it.commentandlikeservice.model.Comment;
import com.in2it.commentandlikeservice.repository.CommentRepository;
import com.in2it.commentandlikeservice.service.CommentService;

import feign.FeignException.InternalServerError;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentConvertor objectMapper;
	@Autowired
	private BlogFeign feign;

	private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

	public CommentDto saveComment(CommentDto commentDto, String blogId) {
		ResponseEntity<BlogDto> response = null;
		log.info("------------------------" + commentDto);
		try {
			response = feign.getBlogById(blogId);
		} catch (RetryableException e) {

			throw new ServiceDownException("--------------blog service is down please try after sometime");

		} catch (Exception e) {
			e.printStackTrace();
		}

		BlogDto blog = null;
		// it checks if blog exist or not
		if (response != null) {
			blog = response.getBody();

		} else {
			throw new BlogNotFoundException("Blog Id is not valid..");
		}

		Comment comment = objectMapper.dtoToCommentConvertor(commentDto);
		comment.setCreatedDate(LocalDateTime.now());

		long commentCount = commentRepository.findByBlogIdAndStatus(blogId, "Active").size() + 1;
		try {
			feign.updateComment(blogId, commentCount);
		} catch (RetryableException e) {

			throw new ServiceDownException("--------------blog service is down please try after sometime");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Comment com = commentRepository.save(comment);
		CommentDto dto = objectMapper.commentToDtoConvertor(com);

		return dto;

	}

	// This method is used to update BLOG with Blog_id with limited permissions
	@Override
	public CommentDto updateComment(CommentUpdateDto updateDto, String commentId) {

		Comment comment = null;

		comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new CommentNotFoundException("Comment dosen't exist with given id"));

		log.info("===================" + updateDto);
		if (updateDto.getContent() != null)
			comment.setContent(updateDto.getContent());

		comment.setUpdatedDateTime(LocalDateTime.now());
		comment.setUpdatedBy(comment.getUserName());

		return objectMapper.commentToDtoConvertor(commentRepository.save(comment));

	}

	public List<CommentDto> getByBlogId(String blogId) {
		List<CommentDto> commentListDto = new ArrayList<>();

		List<Comment> commentList = commentRepository.findByBlogIdAndStatus(blogId, "Active");
		log.info("commentList---------------------------------" + commentList);
		if (commentList.isEmpty()) {
			throw new CommentNotFoundException(HttpStatus.NO_CONTENT + " Data not available, please ! Try again.");
		} else {

			for (Comment com : commentList) {

				CommentDto commentDtoConvertor = objectMapper.commentToDtoConvertor(com);
				commentListDto.add(commentDtoConvertor);
				System.out.println(commentDtoConvertor + "***********");
			}
		}

		return commentListDto;
	}

	public CommentDto deleteByCommentId(String blogId, String commentId) {

		ResponseEntity<BlogDto> response = null;

		try {
			response = feign.getBlogById(blogId);
		} catch (RetryableException e) {

			throw new ServiceDownException("--------------blog service is down please try after sometime");

		} catch (Exception e) {
			e.printStackTrace();
		}

		BlogDto blog = null;
		// it checks if blog exist or not
		if (response != null) {
			blog = response.getBody();

		} else {
			throw new BlogNotFoundException("Blog Id is not valid..");
		}

		Comment comment2 = commentRepository.findByIdAndStatus(commentId, "Active")
				.orElseThrow(() -> new CommentNotFoundException("Comment dosen't exist with given Id."));

		comment2.setStatus("InActive");
		long commentCount = commentRepository.findByBlogIdAndStatus(blogId, "Active").size() - 1;
		try {

			feign.updateComment(blogId, commentCount);
		} catch (RetryableException e) {

			throw new ServiceDownException("--------------blog service is down please try after sometime");

		} catch (Exception e) {
			e.printStackTrace();
		}

		Comment deletedComment = commentRepository.save(comment2);

		return objectMapper.commentToDtoConvertor(deletedComment);
	}

	@Override
	public CommentDto getCommentById(String commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new CommentNotFoundException("Comment dosen't exist with given Id"));
		return objectMapper.commentToDtoConvertor(comment);
	}

}
