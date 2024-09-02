package com.in2it.commentandlikeservice.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class Comment {

	@Id
	private String id;
	private String content;
	private List<String> mediaPath;
	@NotNull
	private long blogId;
	@NotNull
	private String userName;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String status;

	private LocalDateTime createdDate;

	private String deletedBy;

	private String updatedBy;

	private LocalDateTime updatedDateTime;
	private List<String> media;
	
	

	

	
	

}
