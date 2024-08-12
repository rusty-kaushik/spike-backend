package com.in2it.commentandlikeservice.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	@Autowired
	private FileService fileService;

	public CommentDto saveComment(CommentDto commentDto, List<MultipartFile> file) {

//		Comment comment=objectMapper.dtoToCommentConvertor(commentDto);
		Comment comment = objectMapper.dtoToCommentConvertor(commentDto);
//		String uploadImage = null;
//		try {
//			uploadImage = fileService.uploadImage(file);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		comment.setMedia(uploadImage);
		System.out.println("+++++++++++++");
		Comment com = commentRepository.save(comment);
		System.out.println(com + "**********");
		CommentDto dto = objectMapper.commentToDtoConvertor(comment);
		System.out.println(dto + "++++++++++");
		return dto;
	}

//	public List<CommentDto> getAllComment() {
////		List<Comment> commentList=commentRepository.findAll();
//		List<Comment> commentList = commentRepository.findByStatus("Active");
//		List<CommentDto> commentDtoList = new ArrayList<>();
//		for (Comment com : commentList) {
//			if (com != null) {
//				CommentDto commentDtoConvertor = objectMapper.commentToDtoConvertor(com);
//				commentDtoList.add(commentDtoConvertor);
//			}
//		}
//		return commentDtoList;
//	}

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
		Comment comment = new Comment();
		comment.setDeletedAt(LocalDateTime.now());
		comment.setDeletedBy(comment.getUserName());
		if (id > 0) {
			commentRepository.deleteById(id);
			return true;
		} else {

			throw new IdInvalidException(HttpStatus.NO_CONTENT + "id not found, Please ! enter correct id.");
		}
	}

	public List<Comment> deleteByBlogId(Long id, Long commentId) {
////		Comment comment = new Comment();
//		comment.setDeletedAt(LocalDate.now());
//		comment.setDeletedBy(comment.getUserName());
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

	public Comment updateComment(Comment comment, Long blogId, Long commentId) {
		List<Comment> commentByBlogId = commentRepository.findByBlogId(blogId);
		Comment commentByCommentId = commentRepository.findById(commentId).get();
		return null;

	}

}
