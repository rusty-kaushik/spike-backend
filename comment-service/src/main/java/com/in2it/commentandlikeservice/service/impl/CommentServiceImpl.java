package com.in2it.commentandlikeservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.in2it.commentandlikeservice.dto.BlogDto;
import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.exception.BlogNotFoundException;
import com.in2it.commentandlikeservice.exception.CommentNotFoundException;
import com.in2it.commentandlikeservice.exception.ServiceDownException;
import com.in2it.commentandlikeservice.exception.UserNotFoundException;
import com.in2it.commentandlikeservice.feign.BlogFeign;
import com.in2it.commentandlikeservice.feign.BlogResponce;
import com.in2it.commentandlikeservice.mapper.CommentConvertor;
import com.in2it.commentandlikeservice.model.Comment;
import com.in2it.commentandlikeservice.repository.CommentRepository;
import com.in2it.commentandlikeservice.service.CommentService;

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
		ResponseEntity<BlogResponce<BlogDto>> response = null;
		log.info("------------------------" + commentDto);
		try {
			response = feign.getBlogById(blogId);
		} catch (RetryableException e) {

			throw new ServiceDownException("--------------blog service is down please try after sometime");

		} catch (BlogNotFoundException e) {
			throw new BlogNotFoundException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		BlogDto blog = null;
		// it checks if blog exist or not
		if (response != null) {
			blog = response.getBody().getData();

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
	public CommentDto updateComment(String content, String commentId, String updatedBy) {

		Comment comment = null;

		comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new CommentNotFoundException("Comment dosen't exist with given id"));

		log.info("===================" + content);
		if (comment.getUserName().equals(updatedBy)) {
			if (content != null)
				comment.setContent(content);

			comment.setUpdatedDateTime(LocalDateTime.now());
			comment.setUpdatedBy(updatedBy);

			return objectMapper.commentToDtoConvertor(commentRepository.save(comment));
		} else {
			throw new UserNotFoundException("User not authorized to update this comment");
		}
	}

	public List<CommentDto> getByBlogId(String blogId, Integer pageNumber, Integer pageSize) {
		List<CommentDto> commentListDto = new ArrayList<>();

		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by("updatedAt"));
		Page<Comment> commentData = commentRepository.findByBlogIdAndStatus(blogId, "Active", p);
		List<Comment> commentList = commentData.getContent();
		log.info("commentList---------------------------------" + commentList);
//		if (commentList.isEmpty()) {
//			throw new CommentNotFoundException(HttpStatus.NOT_FOUND + " Data not available, please ! Try again.");
//		} else {

		for (Comment com : commentList) {

			CommentDto commentDtoConvertor = objectMapper.commentToDtoConvertor(com);
			commentListDto.add(commentDtoConvertor);
			System.out.println(commentDtoConvertor + "***********");
		}
//		}

		return commentListDto;
	}

	public CommentDto deleteByCommentId(String blogId, String commentId, String updatedBy) {

		ResponseEntity<BlogResponce<BlogDto>> response = null;

		try {
			response = feign.getBlogById(blogId);
		} catch (RetryableException e) {

			throw new ServiceDownException("--------------blog service is down please try after sometime");

		} catch (BlogNotFoundException e) {
			throw new BlogNotFoundException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		BlogDto blog = null;
		// it checks if blog exist or not
		if (response != null) {
			blog = response.getBody().getData();

		} else {
			throw new BlogNotFoundException("Blog Id is not valid..");
		}

		Comment comment2 = commentRepository.findByIdAndStatus(commentId, "Active")
				.orElseThrow(() -> new CommentNotFoundException("Comment dosen't exist with given Id."));

		if (comment2.getUserName().equals(updatedBy)) {
			comment2.setStatus("InActive");
			long commentCount = commentRepository.findByBlogIdAndStatus(blogId, "Active").size() - 1;
			try {

				feign.updateComment(blogId, commentCount);
			} catch (RetryableException e) {

				throw new ServiceDownException("--------------blog service is down please try after sometime");

			} catch (BlogNotFoundException e) {
				throw new BlogNotFoundException(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}

			Comment deletedComment = commentRepository.save(comment2);

			return objectMapper.commentToDtoConvertor(deletedComment);
		} else {
			throw new UserNotFoundException("User not authorized to update this comment");
		}
	}

	@Override
	public CommentDto getCommentById(String commentId) {
		Comment comment = commentRepository.findByIdAndStatus(commentId, "Active")
				.orElseThrow(() -> new CommentNotFoundException("Comment dosen't exist with given Id"));
		return objectMapper.commentToDtoConvertor(comment);
	}

	@Override
	public boolean deleteCommentsByblogId(String blogId) {

		List<Comment> comments = commentRepository.findByBlogIdAndStatus(blogId, "Active");

		if (comments.isEmpty() || comments == null) {
			throw new BlogNotFoundException("No comments are available for given blog Id");
		} else {
			for (Comment comment : comments) {
				comment.setStatus("InActive");
			}
			commentRepository.saveAll(comments);
			return true;
		}

	}

	public List<CommentDto> getAllComment(Integer pageNumber, Integer pageSize) {

		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by("updatedAt"));

//		List<Comment> comments=commentRepository.findAll();

		Page<Comment> commentData = commentRepository.findByStatus("Active", p);
		List<Comment> comments = commentData.getContent();
		System.out.println(comments);
		List<CommentDto> commentDto = new ArrayList<>();
		for (Comment com : comments) {
			CommentDto dto = objectMapper.commentToDtoConvertor(com);
			System.out.println("comment======" + dto);
			commentDto.add(dto);
		}
		return commentDto;
//		return comments.stream().map(objectMapper.commentToDtoConvertor(comments)).collect(Collectors.toList());
	}

	

}
