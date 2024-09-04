package com.in2it.spykeemployee.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String primaryMobileNo;
	private String secondryMobileNo;
	
	@Column(unique = true)
	private String email;
	private String backupEmail;

	@JsonIgnore
	@OneToOne(mappedBy = "contact")
	private Employee employee;

	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SocialMedia> socialLinks;

}
