package com.in2it.commentandlikeservice.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "UUID")
	private UUID id;
	private String content;
	private List<String> mediaPath;
	@NotNull
	private long blogId;
	@NotNull
	private String userName;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String status;
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate;
	@Column(updatable = false)
	private String deletedBy;
	@Column(updatable = true)
	private String updatedBy;

	private LocalDateTime updatedDateTime;
	private List<String> media;
	
	

	

	
	

}
