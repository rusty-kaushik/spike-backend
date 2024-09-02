package com.in2it.blogservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogservice.customException.InfoMissingException;
import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.dto.BlogUpdateDto;
import com.in2it.blogservice.reponse.ResponseHandler;
import com.in2it.blogservice.service.impl.BlogServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController

@RequestMapping("/spike/blog")
public class BlogServiceController {
	@Autowired
	private BlogServiceImpl serviceImpl;

	/*
	 * This method is used to insert blog in database.
	 */

	
	@Tag(name = "post-blog method", description = "This method used to create a blog post. After calling this method its return a map. ")
	@PostMapping(path = "/posts", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Object> saveBlogWithFile(@ModelAttribute BlogDto blogDto) throws IOException {

		List<MultipartFile> media = blogDto.getMedia();

	
		
		BlogDto saveBlogWithFile = serviceImpl.saveBlogWithFile(blogDto, media);

		return ResponseHandler.reponseHandler(saveBlogWithFile, HttpStatus.OK, "Blog post successfully.");
	}

	
	
	

	@PutMapping(path = "/update/{blogId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "Update an blog", description = "Update an existing blog. Using blog blog id can update blog title and content only.")
	public  ResponseEntity<Object> updateBlog( @PathVariable UUID blogId, @RequestBody BlogUpdateDto updateDto) {

		System.out.println(updateDto);
		return ResponseHandler.reponseHandler(serviceImpl.updateBlog(updateDto, blogId), HttpStatus.OK, "Blog modefy successfully.");
	}

	
	@PutMapping("/updateLike")
	@Operation(summary = "update like count")
	public  ResponseEntity<Object> updateLike(@RequestParam UUID blogId, @RequestParam Long totalLikeCount) {
	
		return ResponseHandler.reponseHandler(serviceImpl.updateLike(totalLikeCount, blogId), HttpStatus.ACCEPTED, "like");
	}

	@PutMapping("/updateComment")
	@Operation(summary = "update comment count")
	public  ResponseEntity<Object> updateComment(@RequestParam UUID blogId, @RequestParam Long totalCommentCount) {
		return ResponseHandler.reponseHandler(serviceImpl.updateComment(totalCommentCount, blogId), HttpStatus.ACCEPTED, "commented");
	}

	@DeleteMapping("/deleteByBlogId/{blogId}")
	@Operation(summary = "delete blog by blogId ,in updatedBy we can pass whose login then store his userid i.e. Admin123")
	public  ResponseEntity<Object> deleteBlog(@PathVariable UUID blogId, @RequestParam String updatedBy) {

		return ResponseHandler.reponseHandler(serviceImpl.deleteBlog(blogId, updatedBy), HttpStatus.OK, "Your blog has been deleted successfully.");

	}

	@DeleteMapping("/deleteByTitle/{title}")
	@Operation(summary = "delete blog by title, in updatedBy we can pass whose login then store his userid i.e. Admin123")
	public ResponseEntity<Object> deleteBlogBytitle(@PathVariable String title, @RequestParam UUID blogId,@RequestParam String updatedBy) {
		
		return ResponseHandler.reponseHandler(serviceImpl.deleteBlogByTitle(title, blogId,updatedBy), HttpStatus.OK, "Your blog has been deleted successfully.");
	}



	// using search technique
	@GetMapping("/getAll")
	@Operation(summary = "Get a all Blogs", description = "Returns  all blogs  ")
	public  ResponseEntity<Object> getAllBlog(@RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize) {
		
		return ResponseHandler.reponseHandler(serviceImpl.getBlog(pageNum,pageSize), HttpStatus.OK, "Data retrieved successfully.");
	}

	// pagination

	@GetMapping("/getByUserId/{userId}")
	@Operation(summary = "Get a blog by autherId", description = "Returns a Blog as per the autherId.")
	public  ResponseEntity<Object> getBlogsByAutherId(@PathVariable @Valid String userId) {

	
		return ResponseHandler.reponseHandler(serviceImpl.getByAutherID(userId), HttpStatus.OK, "Data retrieved successfully.");
	}

	@GetMapping("/getByBlogId/{blogId}")
	@Operation(summary = "Get a blog by blogId", description = "Returns a blog as per the blogId.")
	public  ResponseEntity<Object> getBlogById(@PathVariable(value = "blogId") @Valid UUID blogId) {
		
		return ResponseHandler.reponseHandler(serviceImpl.getBlogById(blogId), HttpStatus.OK, "Data retrieved successfully.");
	}

	
	@GetMapping("/getByTitle/{blogTitle}")
	@Operation(summary = "Get a blogs by blogTitle", description = "Returns a blogs as list by  blogTitle.")
	public  ResponseEntity<Object> getBlogByTitle(@RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,@PathVariable(value = "blogTitle") String title) {
		
		List<BlogDto> blogTitleWithPage = serviceImpl.getBlogTitleWithPage(pageNum,pageSize,title);
		
		if(!blogTitleWithPage.isEmpty()) {
			
			return ResponseHandler.reponseHandler(blogTitleWithPage, HttpStatus.OK, "Data retrieved successfully.");
		}else {
			
			throw new InfoMissingException("please write valid info");
		}
	}
	
	
	
	@GetMapping("/getByDepartmentId/{departmentId}")
	@Operation(summary = "Get a blog by teamId", description = "Returns a blog as per the teamId.")
	public  ResponseEntity<Object> getByVisibility(@PathVariable long departmentId){
		
		return ResponseHandler.reponseHandler(serviceImpl.getByVisibility(departmentId), HttpStatus.OK, "Data retrieved successfully.");
	}
	


}
