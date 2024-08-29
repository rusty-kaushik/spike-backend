package com.in2it.blogmongo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogmongo.dto.CommentDto;
import com.in2it.blogmongo.helper.UploadFileHelper;
import com.in2it.blogmongo.model.Blog;
import com.in2it.blogmongo.model.Comment;
import com.in2it.blogmongo.repository.BlogRepository;
import com.in2it.blogmongo.repository.CommentRepository;
import com.in2it.blogmongo.service.CommentService;
import com.in2it.blogmongo.service.FileService;


@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository repository;
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	UploadFileHelper fileHelper;
	
	@Autowired
	ModelMapper mapper;
	
	
	@Value("${project.media}")
	String path;

	@Override
	public Comment addComment(Comment comment) {
		
		return repository.save(comment);
	}

	@Override
	public List<CommentDto> getAllComments() {
		
		List<Comment> allComments = repository.findAll();
		return allComments.stream().map((comment)->mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	public CommentDto commentOnBlog(String content, MultipartFile media, String blogId, String authorid) {
		
		Blog blog = blogRepository.findById(blogId).orElseThrow(()-> new RuntimeException("Blog dosen't Exist"));
		
		String file = null;
		if(media != null && !media.isEmpty()) {
			
			//				file = fileService.uploadMedia(path, media);
			file = fileHelper.uploadFile(media);
		}
		
		Comment comment = Comment.builder()
				.authorid(authorid)
				.content(content)
				.media(file)
				.blogId(blogId)
				.createdAt(LocalDateTime.now())
				.status("ACTIVE")
				.likes(new ArrayList<>())
				.replies(new ArrayList<>())
				.likesCount(0)
				.deletedAt(null)
				.deletedById(null)
				.build();
		
		
		Comment savedComment = repository.save(comment);
		
		
		List<Comment> comments = blog.getComments();
		comments.add(comment);
		blog.setComments(comments);
		int commentsCount = blog.getCommentsCount()+1;
		blog.setCommentsCount(commentsCount);
		blogRepository.save(blog);
		
		
		
		return mapper.map(savedComment, CommentDto.class);
	}

	@Override
	public List<CommentDto> getAllActiveComments() {
		List<Comment> comments = repository.findByStatus("ACTIVE");
		return comments.stream().map((comment)-> mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<CommentDto> getCommentsByBlogId(String blogId) {
		List<Comment> comments = repository.findByStatusAndBlogId("ACTIVE", blogId);
		return comments.stream().map((comment)-> mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<CommentDto> getCommentsByAuthorId(String authorId) {
		List<Comment> comments = repository.findByStatusAndAuthorid("ACTIVE", authorId);
		
		return comments.stream().map((comment)-> mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	public CommentDto deleteComment(String id) {
		Comment comment = repository.findById(id).orElseThrow(()-> new RuntimeException("Comment Dosen't exist with given ID"));
		Blog blog = blogRepository.findById(comment.getBlogId()).orElseThrow(()-> new RuntimeException("Blog Dosen't exist with given ID"));
		List<Comment> comments = blog.getComments();
		comments.remove(comment);
		blog.setComments(comments);
		blog.setCommentsCount(blog.getCommentsCount()-1);
		blogRepository.save(blog);
		comment.setStatus("INACTIVE");
		repository.save(comment);
		
		return mapper.map(comment, CommentDto.class);
	}

	@Override
	public List<CommentDto> deleteCommentsByBlogId(String blogId) {
		System.out.println("Deliting comments of geven blog "+ blogId);
		List<Comment> comments = repository.findByStatusAndBlogId("ACTIVE", blogId);
		List<CommentDto> deletedComments = new ArrayList<>();
		for (Comment comment : comments) {
			
//			Blog blog = blogRepository.findById(comment.getBlogId()).orElseThrow(()-> new RuntimeException("Blog Dosen't exist with given ID"));
//			List<Comment> comments2 = blog.getComments();
//			comments2.remove(comment);
//			blog.setComments(comments2);
//			blog.setCommentsCount(blog.getCommentsCount()-1);
//			blogRepository.save(blog);
			
			 
			comment.setStatus("INACTIVE");
			Comment comment2 = repository.save(comment);
			deletedComments.add(mapper.map(comment2, CommentDto.class));
			
		}
		System.out.println("Deletion DONE");
		return deletedComments;
	}

	@Override
	public List<CommentDto> deleteCommentsByAuthorId(String authorId) {
		List<Comment> comments = repository.findByStatusAndAuthorid("ACTIVE", authorId);
		List<CommentDto> deletedComments = new ArrayList<>();
		for (Comment comment : comments) {
			
			Blog blog = blogRepository.findById(comment.getBlogId()).orElseThrow(()-> new RuntimeException("Blog Dosen't exist with given ID"));
			List<Comment> comments2 = blog.getComments();
			comments.remove(comment);
			blog.setComments(comments2);
			blog.setCommentsCount(blog.getCommentsCount()-1);
			blogRepository.save(blog);
			
			comment.setStatus("INACTIVE");
			Comment comment2 = repository.save(comment);
			deletedComments.add(mapper.map(comment2, CommentDto.class));
			
		}
		
		return deletedComments;
	}


}
