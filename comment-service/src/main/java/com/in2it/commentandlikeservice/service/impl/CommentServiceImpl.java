package com.in2it.commentandlikeservice.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.commentandlikeservice.dto.CommentDto;
import com.in2it.commentandlikeservice.mapper.CommentConvertor;
import com.in2it.commentandlikeservice.model.Comment;
import com.in2it.commentandlikeservice.payload.IdInvalidException;
import com.in2it.commentandlikeservice.repository.CommentRepository;
import com.in2it.commentandlikeservice.service.CommentService;
import com.in2it.commentandlikeservice.service.FileService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentConvertor objectMapper;

	public CommentDto saveComment(CommentDto commentDto, List<MultipartFile> file) {

		Comment comment = objectMapper.dtoToCommentConvertor(commentDto);
		Comment com = commentRepository.save(comment);
		CommentDto dto = objectMapper.commentToDtoConvertor(comment);

		return dto;
	}

	public Comment getByCommentId(Long commentId) {
		Comment com = commentRepository.findById(commentId).get();
		return com;

	}

	public List<CommentDto> getByBlogId(Long id) {
		List<Comment> commentList = commentRepository.findByBlogId(id);
		List<CommentDto> commentListDto = new ArrayList<>();
		for (Comment com : commentList) {
			if (com != null) {
				CommentDto commentDtoConvertor = objectMapper.commentToDtoConvertor(com);
				commentListDto.add(commentDtoConvertor);
			}
		}

		return commentListDto;
	}

	public List<CommentDto> getByUserName(String usename) {
		List<Comment> commentList = commentRepository.findByUserName(usename);
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

	public List<Comment> deleteByBlogId(Long id, Long commentId) {

		List<Comment> comments = commentRepository.findByBlogId(id);
		List<Comment> deleteComments = new ArrayList<>();
		Comment commentByCommentId = commentRepository.findById(commentId).get();
		for (Comment com : comments) {
			if (commentByCommentId.getId() == com.getId()) {

				com.setStatus("InActive");

				Comment c = commentRepository.save(com);
				c.setDeletedAt(LocalDateTime.now());
				deleteComments.add(c);
			}
		}
		return deleteComments;
	}

	

}
