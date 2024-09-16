package com.in2it.blogservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogservice.customException.CommentServiceDownException;
import com.in2it.blogservice.customException.LikeServiceDownException;
import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;
@Component
public interface BlogService {
	
	
//	BlogDto saveBlog(BlogDto blogDto);
	BlogDto	saveBlogWithFile(BlogDto blogDto,List<MultipartFile> multipartFile);
	
	BlogDto updateBlog(BlogUpdateDto updateDto,  UUID blogId);
	BlogDto updateLike(Long totallikeCount ,UUID id);
	BlogDto updateComment(Long totalCommentCount,UUID id);
	
	Boolean deleteBlog(UUID id, String userId) throws CommentServiceDownException, LikeServiceDownException ;
	Boolean deleteBlogByTitle(String title, UUID blogId, String deletedBy);
	
	BlogDto getBlogById(UUID id);
	List<BlogDto> getBlogTitleWithPage(int pageNum, int pageSize,String title);
	List<BlogDto> getBlog(int pageNum, int pageSize);
	List<BlogDto> getByAutherID(String userId);
	
	
	List<BlogDto> getByVisibility(long teamId);

}
