package com.in2it.commentandlikeservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

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

		long incrementcount = blog.getCommentCount() + 1;

		feign.updateComment(blogId, incrementcount);
		CommentDto dto = objectMapper.commentToDtoConvertor(comment);

		return dto;

	}

	// This method is used to update BLOG with Blog_id with limited permissions
	@Override
	public CommentDto updateComment(CommentUpdateDto updateDto, String commentId) {

		Comment comment = null;

		comment = commentRepository.findById(commentId.toString()).get();

		if (comment != null && comment.getId().equals(commentId)) {
			if (updateDto.getContent() != null)
				comment.setContent(updateDto.getContent());

			comment.setUpdatedDateTime(LocalDateTime.now());
			comment.setUpdatedBy(comment.getUserName());

			return objectMapper.commentToDtoConvertor(commentRepository.save(comment));
		} else {
			throw new UserNotFoundException(
					" Insufficient information, Please ! try again with sufficient information.");
		}

	}

	public Comment getByCommentId(String commentId) {
		Comment com = commentRepository.findByIdAndStatus(commentId, "Active");
		return com;

	}

	public List<CommentDto> getByBlogId(Long blogId) {
		List<CommentDto> commentListDto = new ArrayList<>();
		try {

			List<Comment> commentList = commentRepository.findByBlogIdAndStatus(blogId, "Active");
			System.out.println(commentList + "++++++++++");
			if (commentList.isEmpty()) {
				System.out.println("list is empty");
			} else {

				for (Comment com : commentList) {

					CommentDto commentDtoConvertor = objectMapper.commentToDtoConvertor(com);
					commentListDto.add(commentDtoConvertor);
					System.out.println(commentDtoConvertor + "***********");
				}
			}
		} catch (Exception e) {
			logger.error("Error fetching comments by blogId: {}", blogId, e);
		}

		return commentListDto;
	}

	public List<Comment> deleteByBlogId(Long blogId, String commentId) {

		ResponseEntity<BlogDto> response = feign.getBlogById(blogId);
		BlogDto blog = response.getBody();
		// it checks if blog exist or not
		if (blog == null) {
			throw new BlogNotFoundException("Blog does not exist");
		}

		List<Comment> comments = commentRepository.findByBlogIdAndStatus(blogId, "Active");
		System.out.println(comments);
		List<Comment> deleteComments = new ArrayList<>();
		Comment commentByCommentId = commentRepository.findById(commentId).get();

		for (Comment com : comments) {
			System.out.println(commentByCommentId.getId() + "commentByCommentId.getId()");
			System.out.println(com.getId() + "com");
			if (com.getId().equals(commentByCommentId.getId())) {

				com.setStatus("InActive");

				Comment comment = commentRepository.save(com);
				comment.setUpdatedDateTime(LocalDateTime.now());
				comment.setDeletedBy(comment.getUserName());
				long decrementcount = blog.getCommentCount() - 1;

				feign.updateComment(blogId, decrementcount);

				deleteComments.add(comment);
			}
		}
		return deleteComments;
	}

}
