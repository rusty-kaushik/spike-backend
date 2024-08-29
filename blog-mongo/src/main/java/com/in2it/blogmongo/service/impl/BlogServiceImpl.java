package com.in2it.blogmongo.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogmongo.dto.BlogDto;
import com.in2it.blogmongo.exception.DataDoseNotExistException;
import com.in2it.blogmongo.exception.InvalidDataException;
import com.in2it.blogmongo.helper.UploadFileHelper;
import com.in2it.blogmongo.model.Blog;
import com.in2it.blogmongo.model.Comment;
import com.in2it.blogmongo.repository.BlogRepository;
import com.in2it.blogmongo.service.BlogService;
import com.in2it.blogmongo.service.CommentService;
import com.in2it.blogmongo.service.FileService;
import com.in2it.blogmongo.service.LikeService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	BlogRepository repository;

	@Autowired
	FileService fileService;

	@Autowired
	UploadFileHelper fileHelper;

	@Autowired
	ModelMapper mapper;

	@Autowired
	LikeService likeService;

	@Autowired
	CommentService commentService;

	@Value("${project.media}")
	String path;

	
	
	/*
	 * This method creats 
	 */

	@Override
	public Blog createBlog(Blog blog) {
		System.out.println(blog.getTitle() + "title in service");
		return repository.save(blog);
	}

	
	
	/*
	 * TODO
	 */

	@Override
	public List<BlogDto> getAllBlogs() {

		List<Blog> allBlog = repository.findAll();

		return allBlog.stream().map((blog) -> mapper.map(blog, BlogDto.class)).collect(Collectors.toList());

	}
	
	
	
	/*
	 * TODO
	 */


	@Override
	public BlogDto addBlog(String title, String content, String visiblity, List<MultipartFile> media, String authorid,
			List<String> tags, Long departmentId, Long teamId) {
		List<String> uploadedMedia = new ArrayList<>();
		if (isValidTitle(title) && isValidContent(content) && isValidAuthorId(authorid)
				&& isValidDepartmentId(departmentId) && isValidTeamId(teamId) && isValidVisiblity(visiblity))  {

			if (media != null && !media.isEmpty()) {
				for (MultipartFile file : media) {
					try {
						String uploadMedia = fileService.uploadMedia(path, file);
						String uploadFile = fileHelper.uploadFile(file);
						System.out.println("upload media " + uploadMedia);
						System.out.println("uploaded file " + uploadFile);
//					uploadedMedia.add(uploadMedia);
						uploadedMedia.add(uploadFile);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}

			Blog blog = Blog.builder()
					.title(title).content(content).visiblity(visiblity).authorId(authorid).tags(tags)
					.createdAt(LocalDateTime.now()).commentsCount(0).likesCount(0).likes(new ArrayList<>())
					.comments(new ArrayList<>()).status("ACTIVE").media(uploadedMedia).departmentId(departmentId)
					.teamId(teamId).build();
			
			Blog savedBlog = repository.save(blog);
			return mapper.map(savedBlog, BlogDto.class);
		}

		throw new RuntimeException("Somethig went wrong");
	}

	
	
	/*
	 * TODO
	 */
	
	@Override
	public BlogDto getBlogByBlogId(String blogId) {

		if(isValidBlogId(blogId)) {
			
			Blog blog = repository.findById(blogId)
					.orElseThrow(() -> new DataDoseNotExistException("Data dosen't exist with given id... " + blogId));
			return mapper.map(blog, BlogDto.class);
		}
		
		throw new RuntimeException("Somethig went wrong");
	}
	
	
	
	
	/*
	 * TODO
	 */

	@Override
	public List<BlogDto> getBlogsByAuthorId(String authorId) {
		if(isValidAuthorId(authorId)) {
			List<Blog> allActiveBlog = repository.findByAuthorIdAndStatus(authorId, "ACTIVE");
			return allActiveBlog.stream().map((blog) -> mapper.map(blog, BlogDto.class)).collect(Collectors.toList());
			
		}
		
		throw new RuntimeException("Somethig went wrong");

	}
	
	
	
	/*
	 * 
	 */

	@Override
	public List<BlogDto> getBlogsByTitle(String title) {

//		return repository.findByTitleAndStatus(title, "ACTIVE");
		if(isValidTitle(title)) {
			
			List<Blog> blogs = repository.findByStatusAndTitleContaining("ACTIVE", title);
			return blogs.stream().map((blog) -> mapper.map(blog, BlogDto.class)).collect(Collectors.toList());
		}
		throw new RuntimeException("Somethig went wrong");
	}
	
	
	
	
	

	/*
	 * TODO
	 */
	
	@Override
	public BlogDto updateBlog(String blogId, String title, String content, String visiblity, List<String> tags) {
		Blog blog = repository.findById(blogId)
				.orElseThrow(() -> new DataDoseNotExistException("Blog dosen't exist with given id"));
		if (blog != null) {
			blog.setTitle(title);
			blog.setContent(content);
			blog.setVisiblity(visiblity);
			blog.setTags(tags);
			blog.setUpdatedAt(LocalDateTime.now());

		}

		Blog updatedBlog = repository.save(blog);
		return mapper.map(updatedBlog, BlogDto.class);
	}
	
	
	
	
	
	/*
	 *TODO 
	 */

	@Override
	public BlogDto deleteBlog(String blogId) {
		Blog blog = repository.findById(blogId)
				.orElseThrow(() -> new DataDoseNotExistException("Blog dosen't exist with given id"));
		if (blog != null) {
			blog.setStatus("INACTIVE");
			blog.setDeletedAt(LocalDateTime.now());
			commentService.deleteCommentsByBlogId(blogId);
		}
		Blog deletedBlog = repository.save(blog);
		return mapper.map(deletedBlog, BlogDto.class);
	}
	
	
	
	
	
	
	/*
	 * TODO
	 */

	@Override
	public List<BlogDto> getAllActiveBlogs() {

		List<Blog> activeBlogs = repository.findByStatus("ACTIVE");
		return activeBlogs.stream().map((blog) -> mapper.map(blog, BlogDto.class)).collect(Collectors.toList());
	}
	
	
	
	
	
	/*
	 * TODO
	 * 
	 */
	

	@Override
	public List<BlogDto> deleteByAuthorId(String authorId) {

		List<Blog> blogs = repository.findByAuthorId(authorId);
		List<Blog> deletedBlogs = new ArrayList<>();
		for (Blog blog : blogs) {
			blog.setStatus("INACTIVE");
			List<Comment> comments = blog.getComments();
			for (Comment comment : comments) {
				commentService.deleteCommentsByBlogId(blog.getId());
			}

			deletedBlogs.add(repository.save(blog));
		}

		return deletedBlogs.stream().map((blog) -> mapper.map(blog, BlogDto.class)).toList();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	========================================validator functions================================================================

	private boolean isValidTitle(String title) {
		if (title == null || title.isBlank() || title.isEmpty()) {
			throw new InvalidDataException("Please provide a Title for the blog...");
		}

		return true;
	}

	private boolean isValidContent(String content) {

		if (content == null || content.isBlank() || content.isEmpty()) {
			throw new InvalidDataException("Please provide a Content for the blog...");
		}

		return true;
	}

	private boolean isValidVisiblity(String visibility) {
		if (visibility == null || visibility.isBlank() || visibility.isEmpty() || visibility.contains(" ")) {
			throw new InvalidDataException("Please provide a valid visiblity");
		}

		return true;
	}

	private boolean isValidDepartmentId(Long departmenId) {
		if (departmenId < 0) {
			throw new InvalidDataException("Please provide a valid department ID");
		}
		return true;
	}

	private boolean isValidTeamId(Long teamId) {
		if (teamId < 0) {
			throw new InvalidDataException("Please provide a valid department ID");
		}
		return true;
	}

	private boolean isValidAuthorId(String authorId) {
		if (authorId == null || authorId.isBlank() || authorId.isEmpty()) {
			throw new InvalidDataException("Author id is Incorrect or invalid");
		}
		return true;
	}
	
	private boolean isValidBlogId(String blogId) {
		if (blogId == null || blogId.isBlank() || blogId.isEmpty()) {
			throw new InvalidDataException("Blog id is Incorrect or invalid");
		}
		return true;
	}


}
