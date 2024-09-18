package com.taskboard_service.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Auditable {

	    @CreatedDate
	    @Column(nullable = false, updatable = false)
	    private LocalDateTime createdAt;

	    @CreatedBy
	    @Column(updatable = false)
	    private String createdBy;

	    @LastModifiedDate
	    private LocalDateTime lastModifiedDate;
	    
	    @LastModifiedBy
	    private String lastModifiedBy;
	    
	    
	  

}
