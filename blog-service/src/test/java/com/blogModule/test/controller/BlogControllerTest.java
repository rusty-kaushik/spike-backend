package com.blogModule.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogservice.controller.BlogServiceController;
import com.in2it.blogservice.customException.CommentServiceDownException;

import com.in2it.blogservice.customException.InfoMissingException;
import com.in2it.blogservice.customException.LikeServiceDownException;
import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;
import com.in2it.blogservice.reponse.ResponseHandler;
import com.in2it.blogservice.reponse.ResponseHandlerWithPageable;
import com.in2it.blogservice.service.impl.BlogServiceImpl;

@SpringBootTest
@ContextConfiguration(classes = BlogControllerTest.class)
public class BlogControllerTest {

	 	@Mock
	    private BlogServiceImpl serviceImpl;

	    @InjectMocks
	    private BlogServiceController blogController;

	    @Test
	    public void testSaveBlogWithFile() throws IOException 
	    {

	        BlogDto blogDto = new BlogDto();
	        blogDto.setTitle("Test Blog");

	        List<MultipartFile> media = new ArrayList<>();

	        when(serviceImpl.saveBlogWithFile(any(BlogDto.class), any(List.class))).thenReturn(blogDto);

	        ResponseEntity<ResponseHandler<BlogDto>> response = blogController.saveBlogWithFile(media,blogDto);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Blog post successfully.", response.getBody().getMessage());
	        assertEquals(blogDto, response.getBody().getData());
	    
	    }
	    
	    @Test
	    public void updateBlog() 
	    {

	    	BlogUpdateDto updateBlogDto = new BlogUpdateDto();
	    	updateBlogDto.setTitle("Test Blog");
	    	UUID id=UUID.randomUUID();
	    	updateBlogDto.setUserName("user-1");
	    	
	    	BlogDto blogDto = new BlogDto();
		    blogDto.setTitle("Test Blog");
		    blogDto.setUpdatedBy("user-1");
		    blogDto.setId(id);

	        when(serviceImpl.updateBlog(any(BlogUpdateDto.class), any(UUID.class)))
	            .thenReturn(blogDto);

	        ResponseEntity<ResponseHandler<BlogDto>> response = blogController.updateBlog(id.toString(), updateBlogDto);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Blog modify successfully.", response.getBody().getMessage());
	        assertEquals(blogDto, response.getBody().getData());
	    
	    }
	    @Test
	    public void updateLike() 
	    {
	    	
	    	UUID id=UUID.randomUUID();
	    	Long likeCount=12L;

	    	BlogUpdateDto updateBlogDto = new BlogUpdateDto();
	    	updateBlogDto.setTitle("Test Blog");
	    	
	    	updateBlogDto.setUserName("user-1");
	    	
	    	BlogDto blogDto = new BlogDto();
		    blogDto.setTitle("Test Blog");
		    blogDto.setUpdatedBy("user-1");
		    blogDto.setLikeCount(likeCount);
		    blogDto.setId(id);

	        when(serviceImpl.updateLike(any(Long.class), any(UUID.class))).thenReturn(blogDto);

	        ResponseEntity<ResponseHandler<BlogDto>> response = blogController.updateLike(id.toString(), likeCount);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("liked", response.getBody().getMessage());
	        assertEquals(blogDto, response.getBody().getData());
	    
	    }
	    @Test
	    public void updateComment() 
	    {

	    	UUID id=UUID.randomUUID();
	    	Long commentCount=12L;

	    	BlogUpdateDto updateBlogDto = new BlogUpdateDto();
	    	updateBlogDto.setTitle("Test Blog");
	    	
	    	updateBlogDto.setUserName("user-1");
	    	
	    	BlogDto blogDto = new BlogDto();
		    blogDto.setTitle("Test Blog");
		    blogDto.setUpdatedBy("user-1");
		    blogDto.setCommentCount(commentCount);
		    blogDto.setId(id);

	        when(serviceImpl.updateComment(any(Long.class), any(UUID.class))).thenReturn(blogDto);

	        ResponseEntity<ResponseHandler<BlogDto>> response = blogController.updateComment(id.toString(), commentCount);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("commented", response.getBody().getMessage());
	        assertEquals(blogDto, response.getBody().getData());
	    
	    }
	    @Test
	    public void deleteBlog() throws CommentServiceDownException, LikeServiceDownException
    {
	    	
	    	UUID id=UUID.randomUUID();
	    	String userId="user-1";

	    	BlogDto blogDto = new BlogDto();
		    blogDto.setUpdatedBy(userId);
		    blogDto.setId(id);
		    blogDto.setStatus(false);

	        when(serviceImpl.deleteBlog(any(UUID.class), any(String.class))).thenReturn(true);

	        ResponseEntity<ResponseHandler<Boolean>> response = blogController.deleteBlog(id.toString(), userId);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Your blog has been deleted successfully.", response.getBody().getMessage());
	        assertEquals(true, response.getBody().getData());
	    
	    }
	    
	    @Test
		@Deprecated
	    public void deleteBlogByTitle()
	    {

	    	UUID id=UUID.randomUUID();
	    	String userId="user-1";

	    	BlogDto blogDto = new BlogDto();
		    blogDto.setUpdatedBy(userId);
		    blogDto.setId(id);
		    blogDto.setStatus(false);
		    blogDto.setTitle("Title");

	        when(serviceImpl.deleteBlogByTitle(any(String.class), any(UUID.class), any(String.class))).thenReturn(true);

		     ResponseEntity<ResponseHandler<Boolean>> response = blogController.deleteBlogBytitle("Title",id.toString(), userId);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Your blog has been deleted successfully.", response.getBody().getMessage());
	        assertEquals(true, response.getBody().getData());
	    
	    }
	    
	    @Test
	    public void getAllBlog() throws CommentServiceDownException 
	    {
	    	List<BlogDto> allBlogDto = new ArrayList<>();
	        when(serviceImpl.getBlog(any(Integer.class), any(Integer.class))).thenReturn(allBlogDto);

	        ResponseEntity<ResponseHandlerWithPageable<List<BlogDto>>>  response = blogController.getAllBlog(0,5);
	        
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Data retrieved successfully.", response.getBody().getMessage());
	        assertEquals(allBlogDto, response.getBody().getData());
	    
	    }
	    
	    @Test
	    public void getBlogsByAutherName() throws CommentServiceDownException 
	    {
	    	String userId="user-1";
	        
	    	List<BlogDto> allBlogDto = new ArrayList<>();
		    
	        when(serviceImpl.getByAutherName(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(allBlogDto);

	        ResponseEntity<ResponseHandlerWithPageable<List<BlogDto>>>  response = blogController.getBlogsByAutherName(0, 10, userId);
	        
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Data retrieved successfully.", response.getBody().getMessage());
	        assertEquals(allBlogDto, response.getBody().getData());
	    
	    }
	    
	    @Test
	    public void getBlogsByAutherId() throws CommentServiceDownException 
	    {
	    	long userId=12;
	    	
	    	List<BlogDto> allBlogDto = new ArrayList<>();
	    	
	    	when(serviceImpl.getByAutherID(any(Long.class))).thenReturn(allBlogDto);
	    	
	    	ResponseEntity<ResponseHandler<List<BlogDto>>>  response = blogController.getBlogsByAutherId(userId);
	    	
	    	assertEquals(HttpStatus.OK, response.getStatusCode());
	    	assertEquals("Data retrieved successfully.", response.getBody().getMessage());
	    	assertEquals(allBlogDto, response.getBody().getData());
	    	
	    }
	    
	    @Test
	    public void getBlogById() throws CommentServiceDownException 
	    {
	    	UUID id=UUID.randomUUID();
	    	BlogDto blogDto = new BlogDto();
	    	blogDto.setId(id);
	        when(serviceImpl.getBlogById(any(UUID.class))).thenReturn(blogDto);

	        ResponseEntity<ResponseHandler<BlogDto>> response = blogController.getBlogById(id.toString());
	        
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Data retrieved successfully.", response.getBody().getMessage());
	        assertEquals(blogDto, response.getBody().getData());
	    
	    }
	    
	    @Test
	    public void getBlogByTitle() throws InfoMissingException, CommentServiceDownException
	    {
//	    	UUID id=UUID.randomUUID();

	    	List<BlogDto> allBlogDto = new ArrayList<>();
	    	
	    	BlogDto blogDto1 = new BlogDto();
	    	BlogDto blogDto2 = new BlogDto();
	    	BlogDto blogDto3 = new BlogDto();
	    	blogDto1.setTitle("blogTitle");
	    	blogDto2.setTitle("blogTitle");
	    	blogDto3.setTitle("blogTitle");
	    	
	    	allBlogDto.add(blogDto1);
	    	allBlogDto.add(blogDto2);
	    	allBlogDto.add(blogDto3);
	    	
	        when(serviceImpl.getBlogTitleWithPage(any(Integer.class),any(Integer.class),any(String.class))).thenReturn(allBlogDto);

	        ResponseEntity<ResponseHandlerWithPageable<List<BlogDto>>> response = blogController.getBlogByTitle(0, 5,"blogTitle");
	        
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Data retrieved successfully.", response.getBody().getMessage());
	        assertEquals(allBlogDto, response.getBody().getData());
	    
	    }
	    
}
