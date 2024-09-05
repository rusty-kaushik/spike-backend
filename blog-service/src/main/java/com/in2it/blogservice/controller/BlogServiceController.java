package com.in2it.blogservice.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
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
	public ResponseEntity<ResponseHandler<BlogDto>> saveBlogWithFile(@ModelAttribute BlogDto blogDto)
			throws IOException {

		List<MultipartFile> media = blogDto.getMedia();

		BlogDto saveBlogWithFile = serviceImpl.saveBlogWithFile(blogDto, media);

		ResponseHandler<BlogDto> response = new ResponseHandler<BlogDto>(saveBlogWithFile, "Blog post successfully.",
				HttpStatus.OK, HttpStatus.OK.value(), LocalDateTime.now());

		return ResponseEntity.ok(response);

//		return ResponseEntity<ResponseHandlersaveBlogWithFile, HttpStatus.OK,HttpStatus.OK.value(),"Blog post successfully.")>;
	}

	@PutMapping(path = "/update/{blogId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "Update an blog", description = "Update an existing blog. Using blog blog id can update blog title and content only.")
	public ResponseEntity<ResponseHandler<BlogDto>> updateBlog(@PathVariable UUID blogId,
			@RequestBody BlogUpdateDto updateDto) {

		ResponseHandler<BlogDto> response = new ResponseHandler<BlogDto>(serviceImpl.updateBlog(updateDto, blogId),
				"Blog modify successfully.", HttpStatus.OK, HttpStatus.OK.value(), LocalDateTime.now());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/updateLike")
	@Operation(summary = "update like count")
	public ResponseEntity<ResponseHandler<BlogDto>> updateLike(@RequestParam UUID blogId,
			@RequestParam Long totalLikeCount) {

		ResponseHandler<BlogDto> response = new ResponseHandler<BlogDto>(serviceImpl.updateLike(totalLikeCount, blogId),
				"liked", HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(), LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@PutMapping("/updateComment")
	@Operation(summary = "update comment count")
	public ResponseEntity<ResponseHandler<BlogDto>> updateComment(@RequestParam UUID blogId,
			@RequestParam Long totalCommentCount) {
		ResponseHandler<BlogDto> response = new ResponseHandler<BlogDto>(
				serviceImpl.updateComment(totalCommentCount, blogId), "commented", HttpStatus.ACCEPTED,
				HttpStatus.ACCEPTED.value(), LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/deleteByBlogId/{blogId}")
	@Operation(summary = "delete blog by blogId ,in updatedBy we can pass whose login then store his userid i.e. Admin123")
	public ResponseEntity<ResponseHandler<Boolean>> deleteBlog(@PathVariable UUID blogId,
			@RequestParam String updatedBy) {
		ResponseHandler<Boolean> response = new ResponseHandler<>(serviceImpl.deleteBlog(blogId, updatedBy),
				"Your blog has been deleted successfully.", HttpStatus.OK, HttpStatus.OK.value(), LocalDateTime.now());

		return ResponseEntity.ok(response);

	}

	@DeleteMapping("/deleteByTitle/{title}")
	@Operation(summary = "delete blog by title, in updatedBy we can pass whose login then store his userid i.e. Admin123")
	public ResponseEntity<ResponseHandler<Boolean>> deleteBlogBytitle(@PathVariable String title,
			@RequestParam UUID blogId, @RequestParam String updatedBy) {
		ResponseHandler<Boolean> response = new ResponseHandler<>(
				serviceImpl.deleteBlogByTitle(title, blogId, updatedBy), "Your blog has been deleted successfully.",
				HttpStatus.OK, HttpStatus.OK.value(), LocalDateTime.now());
		return ResponseEntity.ok(response);
	}

	// using search technique
	@GetMapping("/getAll")
	@Operation(summary = "Get a all Blogs", description = "Returns  all blogs  ")
	public ResponseEntity<ResponseHandler<List<BlogDto>>> getAllBlog(@RequestParam(defaultValue = "0") int pageNum,
			@RequestParam(defaultValue = "5") int pageSize) {
		ResponseHandler<List<BlogDto>> response = new ResponseHandler<>(serviceImpl.getBlog(pageNum, pageSize),
				"Data retrieved successfully.", HttpStatus.OK, HttpStatus.OK.value(), LocalDateTime.now());
		return ResponseEntity.ok(response);
	}

	// pagination

	@GetMapping("/getByUserId/{userId}")
	@Operation(summary = "Get a blog by autherId", description = "Returns a Blog as per the autherId.")
	public ResponseEntity<ResponseHandler<List<BlogDto>>> getBlogsByAutherId(@PathVariable @Valid String userId) {
		ResponseHandler<List<BlogDto>> response = new ResponseHandler<>(serviceImpl.getByAutherID(userId),
				"Data retrieved successfully.", HttpStatus.OK, HttpStatus.OK.value(), LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getByBlogId/{blogId}")
	@Operation(summary = "Get a blog by blogId", description = "Returns a blog as per the blogId.")
	public ResponseEntity<ResponseHandler<BlogDto>> getBlogById(@PathVariable(value = "blogId") @Valid UUID blogId) {
		ResponseHandler<BlogDto> response = new ResponseHandler<BlogDto>(serviceImpl.getBlogById(blogId),
				"Data retrieved successfully.", HttpStatus.OK, HttpStatus.OK.value(), LocalDateTime.now());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getByTitle/{blogTitle}")
	@Operation(summary = "Get a blogs by blogTitle", description = "Returns a blogs as list by  blogTitle.")
	public ResponseEntity<ResponseHandler<List<BlogDto>>> getBlogByTitle(@RequestParam(defaultValue = "0") int pageNum,
			@RequestParam(defaultValue = "5") int pageSize, @PathVariable(value = "blogTitle") String title) {

		List<BlogDto> blogTitleWithPage = serviceImpl.getBlogTitleWithPage(pageNum, pageSize, title);

		if (!blogTitleWithPage.isEmpty()) {
			ResponseHandler<List<BlogDto>> response = new ResponseHandler<>(blogTitleWithPage,
					"Data retrieved successfully.", HttpStatus.OK, HttpStatus.OK.value(), LocalDateTime.now());
			return ResponseEntity.ok(response);
		} else {

			throw new InfoMissingException("please write valid info");
		}
	}

}
