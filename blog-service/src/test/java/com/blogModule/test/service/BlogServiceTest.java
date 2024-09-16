package com.blogModule.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogservice.customException.CommentServiceDownException;
import com.in2it.blogservice.customException.LikeServiceDownException;
import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;
import com.in2it.blogservice.feignClients.FeignClientForComment;
import com.in2it.blogservice.feignClients.FeignClientForLike;
import com.in2it.blogservice.mapper.Converter;
import com.in2it.blogservice.model.Blog;
import com.in2it.blogservice.repository.BlogRepository;
import com.in2it.blogservice.service.impl.BlogServiceImpl;


@SpringBootTest
@ContextConfiguration(classes = BlogServiceTest.class)
public class BlogServiceTest {

	@Mock
	private FeignClientForComment commentFeign;
	@Mock
	private FeignClientForLike likeFeign;
	@Mock
	private BlogRepository repo;
	@Mock
	private Blog blogEntity;
	@Mock
	private BlogDto blogDTO;
	@Mock
	private Converter objectMapper;
	@Mock
	private ModelMapper mapper;
	@InjectMocks
	private BlogServiceImpl service;
	
	
	@Test
	public void saveBlogWithFileTest() throws IOException {
	    
//		Making Multipartfile's Object
		Path tempFile = Files.createTempFile("fakefile", ".png");
		
		File file = tempFile.toFile();
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("fileItem", file.getName(), "image/png", IOUtils.toByteArray(input));

//	    Making dummy objects of Blog and BlogDto
	    BlogDto blogDto = new BlogDto();
	    blogDto.setDepartmentId(12);
	    blogDto.setUserName("Sumit");
	    blogDto.setTitle("Java");
	    blogDto.setContent("Java is a good language");
	    blogDto.setMediaFile(List.of("Media.png"));

	    Blog blog = new Blog();
	    blog.setDepartmentId(12);
	    blog.setUserName("Sumit");
	    blog.setTitle("Java");
	    blog.setContent("Java is a good language");
	    blog.setMediaFile(List.of("Media.png"));
	    blog.setMediaPath(List.of("src/main/resources/static/image/Media.png"));	    
	    
	    
	    Map<String, List<String>> uploadFilePath = new HashMap<>();
	    uploadFilePath.put("media_path", Arrays.asList("src/main/resources/static/image/Media.png"));
	    uploadFilePath.put("file_name", Arrays.asList("Media.png"));
	    
	    List<String> generatedUri=  List.of("src/main/resources/static/image/Media.png");
	    
	    when(objectMapper.uploadFile(List.of(multipartFile))).thenReturn(uploadFilePath);
	    when(objectMapper.genrateUriLink(List.of(multipartFile))).thenReturn(generatedUri);
	    when(objectMapper.dtoToBlogConverter(blogDto, List.of("Media.png"), generatedUri)).thenReturn(blog);

	    when(repo.save(any(Blog.class))).thenReturn(blog);
	    
	    BlogDto expectedDto = new BlogDto();
	    when(objectMapper.blogToDtoConverter(blog)).thenReturn(expectedDto);
	    
	    
	   
	    
	    // Act
	    BlogDto result = service.saveBlogWithFile(blogDto, List.of(multipartFile));
	    
	    // Assert
	    verify(objectMapper, times(1)).uploadFile(List.of(multipartFile));
	    verify(objectMapper, times(1)).dtoToBlogConverter(blogDto, List.of("Media.png"), List.of("src/main/resources/static/image/Media.png"));
	    verify(repo, times(1)).save(any(Blog.class));
	    assertEquals(expectedDto.getDepartmentId(), result.getDepartmentId());
	    assertEquals(expectedDto.getUserName(), result.getUserName());
	    assertEquals(expectedDto.getContent(), result.getContent());
	    assertEquals(expectedDto.getTitle(), result.getTitle());
	    
	}

	@Test
	public void updateBlog()
	{
	    
		
	    UUID blogId = UUID.randomUUID();
	    BlogUpdateDto updateDto = new BlogUpdateDto();
	    updateDto.setUserName("testUser");
	    updateDto.setContent("Updated content");
	    updateDto.setTitle("Updated title");
	    
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    existingBlog.setContent("Old content");
	    existingBlog.setTitle("Old title");
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setUpdatedBy("testUser");
	    blogDto.setContent("Updated content");
	    blogDto.setTitle("Updated title");
	    
	    when(repo.getByBlogId(blogId)).thenReturn(existingBlog);
	    when(repo.save(any(Blog.class))).thenReturn(existingBlog);
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    
	    // Act
	    BlogDto result = service.updateBlog(updateDto, blogId);
	    System.out.println(result);
	    // Assert
	    assertNotNull(result);
	    assertEquals("Updated content", result.getContent());
	    assertEquals("Updated title", result.getTitle());
	    assertEquals("testUser", result.getUpdatedBy());
	    verify(repo, times(1)).getByBlogId(blogId);
	    verify(repo, times(1)).save(existingBlog);
	}
	@Test
	public void updateLike() throws IOException
	{
		
		UUID blogId = UUID.randomUUID();
		Long totallikeCount = 110l;
		
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setId(blogId);
	    blogDto.setLikeCount(totallikeCount);
	    
	    when(repo.getByBlogId(blogId)).thenReturn(existingBlog);
	    when(repo.save(any(Blog.class))).thenReturn(existingBlog);
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    
	    // Act
	    BlogDto result = service.updateLike(totallikeCount, blogId);
	    System.out.println(result);
	    // Assert
	    assertNotNull(result);
	    assertEquals(totallikeCount, result.getLikeCount());
	    assertEquals(blogId, result.getId());
	    verify(repo, times(1)).getByBlogId(blogId);
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	}
	
	@Test
	public void updateComment() throws IOException
	{
		
		UUID blogId = UUID.randomUUID();
		Long totalCommentCount = 110l;
		
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    existingBlog.setCommentCount(totalCommentCount);
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setId(blogId);
	    blogDto.setCommentCount(totalCommentCount);
	    
	    when(repo.getByBlogId(blogId)).thenReturn(existingBlog);
	    when(repo.save(any(Blog.class))).thenReturn(existingBlog);
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    
	    // Act
	    BlogDto result = service.updateComment(totalCommentCount, blogId);
	    System.out.println(result);
	    // Assert
	    assertNotNull(result);
	    assertEquals(totalCommentCount, result.getCommentCount());
	    assertEquals(blogId, result.getId());
	    verify(repo, times(1)).getByBlogId(blogId);
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	}
	
	@Test
	public void deleteBlog() throws IOException, CommentServiceDownException, LikeServiceDownException
	{
		
		UUID blogId = UUID.randomUUID();
		String updatedBy = "Sumit";
		LocalDateTime dateTime = LocalDateTime.now();
		
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    existingBlog.setUpdatedBy(updatedBy);
	    existingBlog.setUpdatedDateTime(dateTime);
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setId(blogId);
	    blogDto.setUpdatedBy(updatedBy);
	    blogDto.setUpdatedDateTime(dateTime);

	    
	    when(repo.getByBlogId(blogId)).thenReturn(existingBlog);
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);

	    // Act
	    boolean result = service.deleteBlog(blogId, updatedBy);
	    System.out.println(result);
	    // Assert
	    assertNotNull(result);
	    assertEquals(true, result);
	    	
	    verify(repo, times(1)).getByBlogId(blogId);
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	}
	
	@Test
	@Deprecated

	public void deleteBlogByTitle() throws IOException
	{
		
		UUID blogId = UUID.randomUUID();
		String updatedBy = "Sumit";
		LocalDateTime dateTime = LocalDateTime.now();
		String title = "Java";
		
		
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    existingBlog.setUpdatedBy(updatedBy);
	    existingBlog.setUpdatedDateTime(dateTime);
	    existingBlog.setTitle(title);
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setId(blogId);
	    blogDto.setUpdatedBy(updatedBy);
	    blogDto.setUpdatedDateTime(dateTime);
	    blogDto.setTitle(title);
	    
	    List<BlogDto> blogList = Arrays.asList(blogDto);

	    
	    when(service.getBlogTitle(title)).thenReturn(blogList);
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    when(objectMapper.dtoToBlogConverter(any(BlogDto.class),any(List.class),any(List.class))).thenReturn(existingBlog);
	    when(repo.findByTitleContainingAllIgnoringCaseAndStatus(any(String.class),any(Boolean.class))).thenReturn(Arrays.asList(existingBlog));

	    // Act
	    boolean result = service.deleteBlogByTitle(title, blogId, updatedBy);
	    
	    // Assert
	    assertNotNull(result);
	    assertEquals(true, result);
	    	
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	}
	
	@Test
	public void getBlogTitle() throws IOException
	{
		
		UUID blogId = UUID.randomUUID();
		String updatedBy = "Sumit";
		LocalDateTime dateTime = LocalDateTime.now();
		String title = "Java";
		
		
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    existingBlog.setUpdatedBy(updatedBy);
	    existingBlog.setUpdatedDateTime(dateTime);
	    existingBlog.setTitle(title);
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setId(blogId);
	    blogDto.setUpdatedBy(updatedBy);
	    blogDto.setUpdatedDateTime(dateTime);
	    blogDto.setTitle(title);
	    
	    List<BlogDto> blogList = Arrays.asList(blogDto);

	    when(service.getBlogTitle(title)).thenReturn(blogList);
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    when(repo.findByTitleContainingAllIgnoringCaseAndStatus(any(String.class),any(Boolean.class))).thenReturn(Arrays.asList(existingBlog));

	    // Act
	    List<BlogDto> result = service.getBlogTitle(title);
	    
	    System.out.println(result);
	    // Assert
	    assertNotNull(result);
	    assertEquals(blogList, result);
	    	
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	    verify(repo, times(1)).findByTitleContainingAllIgnoringCaseAndStatus(title,true);
	}
	
	@Test
	public void getBlogTitleWithPage() throws IOException
	{
		
		UUID blogId = UUID.randomUUID();
		String updatedBy = "Sumit";
		LocalDateTime dateTime = LocalDateTime.now();
		String title = "Java";
		
		
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    existingBlog.setUpdatedBy(updatedBy);
	    existingBlog.setUpdatedDateTime(dateTime);
	    existingBlog.setTitle(title);
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setId(blogId);
	    blogDto.setUpdatedBy(updatedBy);
	    blogDto.setUpdatedDateTime(dateTime);
	    blogDto.setTitle(title);
	    
	    List<BlogDto> blogList = Arrays.asList(blogDto);

	    when(service.getBlogTitleWithPage(0, 5, title)).thenReturn(blogList);
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    when(repo.findByTitleContainingAllIgnoringCaseAndStatus(any(PageRequest.class), any(String.class), any(Boolean.class))).thenReturn(Arrays.asList(existingBlog));
	    
	    // Act
	    List<BlogDto> result = service.getBlogTitleWithPage(0, 5, title);
	    // Assert
	    assertNotNull(result);
	    assertEquals(blogList, result);
	    	
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	    verify(repo, times(1)).findByTitleContainingAllIgnoringCaseAndStatus(PageRequest.of(0, 5), title, true);
	}
	
	@Test
	public void getBlog() throws IOException
	{
		
		UUID blogId = UUID.randomUUID();
		String updatedBy = "Sumit";
		LocalDateTime dateTime = LocalDateTime.now();
		String title = "Java";
		
		
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    existingBlog.setUpdatedBy(updatedBy);
	    existingBlog.setUpdatedDateTime(dateTime);
	    existingBlog.setTitle(title);
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setId(blogId);
	    blogDto.setUpdatedBy(updatedBy);
	    blogDto.setUpdatedDateTime(dateTime);
	    blogDto.setTitle(title);
	    
	    List<BlogDto> blogList = Arrays.asList(blogDto);

	    when(repo.findAll(any(PageRequest.class), any(Boolean.class))).thenReturn(Arrays.asList(existingBlog));
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    
	    
	    // Act
	    List<BlogDto> result = service.getBlog(0, 5);
	    // Assert
	    assertNotNull(result);
	    assertEquals(blogList, result);
	    	
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	    verify(repo, times(1)).findAll(PageRequest.of(0, 5), true);
	}
	
	@Test
	public void getByAutherID() throws IOException
	{
		
		String userName = "Sumit";		
		
	    Blog existingBlog = new Blog();
	    existingBlog.setUserName(userName);
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setUserName(userName);
	    
	    List<BlogDto> blogList = Arrays.asList(blogDto);

	    when(repo.findByAuthorId(any(String.class))).thenReturn(Arrays.asList(existingBlog));
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    
	    
	    // Act
	    List<BlogDto> result = service.getByAutherID(userName);
	    // Assert
	    assertNotNull(result);
	    assertEquals(blogList, result);
	    	
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	    verify(repo, times(1)).findByAuthorId(userName);
	}

	@Test
	public void getBlogById() throws IOException
	{
		
		UUID blogId = UUID.randomUUID();	
		
	    Blog existingBlog = new Blog();
	    existingBlog.setId(blogId);
	    
	    
	    BlogDto blogDto = new BlogDto();
	    blogDto.setId(blogId);
	    
	    List<BlogDto> blogList = Arrays.asList(blogDto);

	    when(repo.getByBlogId(any(UUID.class))).thenReturn(existingBlog);
	    when(objectMapper.blogToDtoConverter(any(Blog.class))).thenReturn(blogDto);
	    
	    
	    // Act
	    BlogDto result = service.getBlogById(blogId);
	    // Assert
	    assertNotNull(result);
	    assertEquals(blogDto.getId(), result.getId());
	    	
	    verify(objectMapper, times(1)).blogToDtoConverter(existingBlog);
	    verify(repo, times(1)).getByBlogId(blogId);
	}
	
}
