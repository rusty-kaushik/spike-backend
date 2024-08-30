package com.in2it.commentService.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in2it.commentService.dto.CommentDeleteDto;
import com.in2it.commentService.dto.CommentDto;
import com.in2it.commentService.dto.CommentUpdateDto;
import com.in2it.commentService.dto.CreateCommentDto;
import com.in2it.commentService.mapper.CommentConvertor;
import com.in2it.commentService.model.Comment;
import com.in2it.commentService.repository.CommentRepository;
import com.in2it.commentService.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentConvertor objectMapper;

	@Override
	public CommentDto saveComment(CreateCommentDto commentDto) {
		
		return objectMapper.commentToDtoConvertor(commentRepository.save(objectMapper.dtoToCommentConvertor(commentDto)));
		
	}


	@Override
	public List<CommentDto> getCommentsByBlogId(String bId) {
		List<CommentDto> commentsDto=new ArrayList<>();
		try
		{
			List<Comment> comments=commentRepository.findByStatusAndBlogId("Active", bId);
			
			for(Comment comment:comments)
			{
				commentsDto.add(objectMapper.commentToDtoConvertor(comment));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return commentsDto;
	}


	@Override
	public List<CommentDto> getCommentsByAutherId(String authorId) {
		List<CommentDto> commentsDto=new ArrayList<>();
		try
		{
			List<Comment> comments=commentRepository.findByAuthorIdAndStatus(authorId, "Active");
			
			for(Comment comment:comments)
			{
				commentsDto.add(objectMapper.commentToDtoConvertor(comment));
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());		}
		return commentsDto;
	}


	@Override
	public CommentDto updateComment(CommentUpdateDto updateDto) {
		try 
		{
			

			Comment comment = commentRepository.findByIdAndStatus(updateDto.getId(), "Active").get();
			if(!updateDto.getContent().get().isEmpty()) {
				comment.setContent(updateDto.getContent().get());
			}
			
			if(!updateDto.getMedia().get().isEmpty())
			{
				List<String> mediaName=new ArrayList<>();
				List<String> mediaPath=new ArrayList<>();
				HashMap<String , String> imgNameAndUrl= objectMapper.persistFiles(updateDto.getMedia().get());
				Set entrySet = imgNameAndUrl.entrySet();
				Iterator iterator=entrySet.iterator();
				while(iterator.hasNext())
				{
					Map.Entry<String, String> pair=(Map.Entry<String, String>)iterator.next();
					mediaName.add(pair.getKey());
					mediaPath.add(pair.getValue());
				}
			    comment.setMedia(mediaName);
			    comment.setMediaPath(mediaPath);
			   
			}
			 comment.setUpdatedBy(updateDto.getUpdatedBy());
			 comment.setUpdatedAt(LocalDateTime.now());
			return objectMapper.commentToDtoConvertor(commentRepository.save(comment));
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Boolean deleteCommentById(CommentDeleteDto deleteDto) {
		try 
		{
			Comment comment = commentRepository.findByIdAndStatus(deleteDto.getId(), "Active").get();
			comment.setStatus("InActive");
			comment.setUpdatedBy(deleteDto.getDeletedBy());
			comment.setUpdatedAt(LocalDateTime.now());
			commentRepository.save(comment);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			
	}

}
