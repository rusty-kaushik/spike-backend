package com.in2it.blogservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;
import com.in2it.blogservice.service.impl.BlogServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/in2it-blog")
public class BlogServiceController {
	@Autowired
	private BlogServiceImpl serviceImpl;
	
	@PostMapping(path = "/posts", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<BlogDto> saveBlog( @ModelAttribute BlogDto blogDto) {

		
		System.out.println(" ================="+blogDto+"======");
		if (blogDto != null) {
			
			
			return ResponseEntity.status(HttpStatus.OK).body(serviceImpl.saveBlog(blogDto,blogDto.getMedia()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceImpl.saveBlog(blogDto,blogDto.getMedia()));
		}

	}


	
	@PutMapping(path="/update/{authId}" ,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<BlogDto> updateBlog(@RequestBody BlogUpdateDto updateDto, @Valid @PathVariable("authId") Long id) {

		if (updateDto != null || id > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(serviceImpl.updateBlog(updateDto, id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(serviceImpl.updateBlog(updateDto, id));
		}

	}

	@DeleteMapping("/auther/{authId}/delete")
	public ResponseEntity<?> deleteBlog(@PathVariable @Valid Long id) {
		Boolean deleteBlog = serviceImpl.deleteBlog(id);
		
		if(deleteBlog)
		{
			return ResponseEntity.status(HttpStatus.OK).body(true);
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
		
	}

	// pagination
	

	// pagination
	@GetMapping("/get")
     public	ResponseEntity<List<BlogDto>> getAllBlog() {
		List<BlogDto> blog = serviceImpl.getBlog();
		if(!blog.isEmpty()) {

			return ResponseEntity.status(HttpStatus.OK).body(blog);

		} else{

			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
		}
	}

	// pagination

	@GetMapping("/getByAuthId/{id}")
	 public	ResponseEntity<?> getBlogsByAutherId(@PathVariable @Valid long id) {

		List<BlogDto> byAutherId = serviceImpl.getByAutherID(id);
		
		if(!byAutherId.isEmpty()) {

			return ResponseEntity.status(HttpStatus.OK).body(byAutherId);

		} else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
		}

	}
	@GetMapping("/getByBlogId/{blogId}")
	public ResponseEntity<BlogDto> getBlogById(@PathVariable(value = "blogId") @Valid Long id) {

		BlogDto blog = serviceImpl.getBlogById(id);
		
		if(blog!=null) {

			return ResponseEntity.status(HttpStatus.OK).body(blog);
//			return ResponseEntity.ok()
//	                .contentType(MediaType.IMAGE_JPEG)
//	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + blog.getImgPath() + "\"")
//	                .body(blog);

		} else {

			return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}

	}

	// pagination
	@GetMapping("/getByTitle/{blogTitle}")
	public ResponseEntity<List<BlogDto>> getBlogByTitle(@PathVariable(value = "blogTitle")  String title) {

		List<BlogDto> blog = serviceImpl.getBlogTitle(title);
		
		if(!blog.isEmpty()) {

			return ResponseEntity.status(HttpStatus.OK).body(blog);
		} else {
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
		}

	}

}
