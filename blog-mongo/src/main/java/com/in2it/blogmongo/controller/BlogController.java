package com.in2it.blogmongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogmongo.dto.BlogDto;
import com.in2it.blogmongo.model.Blog;
import com.in2it.blogmongo.service.BlogService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/blogs")
public class BlogController {

	@Autowired
	BlogService service;

	
	@Hidden
	@PostMapping
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
		System.out.println(blog.getTitle() + "title in controller");
		Blog savedBlog = service.createBlog(blog);
		return ResponseEntity.ok(savedBlog);
	}

	@PostMapping(path = "author/{authorid}/add-blog", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BlogDto> addBlog(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("visiblity") String visiblity, @PathVariable("authorid") String authorid,
			@RequestParam(required = false) List<String> tags, @RequestParam(required = false, defaultValue = "0") Long departmentId,
			@RequestParam(required = false, defaultValue = "0") Long teamId,
			@RequestParam(value = "media", required = false) List<MultipartFile> media) {

		System.out.println("data :  title " + title + " content " + content + " visiblity " + visiblity + " authorid "
				+ authorid + " tags " + tags + " media " + media);

		BlogDto blog = service.addBlog(title, content, visiblity, media, authorid, tags, departmentId, teamId);
		return ResponseEntity.status(HttpStatus.CREATED).body(blog);

	}

	@Hidden
	@GetMapping
	public ResponseEntity<List<BlogDto>> getAllBlogs() {
		List<BlogDto> allBlogs = service.getAllBlogs();
		return ResponseEntity.ok(allBlogs);
	}
	
	@GetMapping("/all-blogs")
	public ResponseEntity<List<BlogDto>> getAllActiveBlogs() {
		List<BlogDto> allBlogs = service.getAllActiveBlogs();
		return ResponseEntity.ok(allBlogs);
	}

	@GetMapping("/blog/{blogId}")
	public BlogDto getBlogByBlogId(@PathVariable String blogId) {
		return service.getBlogByBlogId(blogId);
	}

	@GetMapping("/author/{authorId}")
	public List<BlogDto> getBlogsByAuthorId(@PathVariable String authorId) {
		return service.getBlogsByAuthorId(authorId);
	}

	@GetMapping("/title")
	public List<BlogDto> getBlogsByTitle(@RequestParam(value = "title", required = true) String title) {
		return service.getBlogsByTitle(title);

	}

//	public Blog updateBlog(@PathVariable String blogId, @RequestParam("title") String title,
//			@RequestParam("content") String content, @RequestParam("visiblity") String visiblity,
//			@RequestParam(required = false) List<String> tags, @RequestParam(required = false) Long departmentId,
//			@RequestParam(required = false) Long teamId) {
//		return null;
//
//	}



	@DeleteMapping("/delete/blog/{blogId}")
	public BlogDto deleteBlog(@PathVariable String blogId) {
		return service.deleteBlog(blogId);
	}
	

	@DeleteMapping("blog/author/{authorId}")
	public List<BlogDto> deleteByAuthorId(@PathVariable String authorId) {
		return service.deleteByAuthorId(authorId);
	}
		

}
