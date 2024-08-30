package com.in2it.blogservice.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogUpdateDto {
	
	@NotNull
	private long id;
	@NotBlank(message = "content cannot be blank")
	private String content;
	private String visiblity;
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<MultipartFile> media;

}
