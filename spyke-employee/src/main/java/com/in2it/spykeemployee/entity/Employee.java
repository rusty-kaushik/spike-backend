package com.in2it.spykeemployee.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotNull
	@Size(min = 2, max = 50)
	private String firstName;

	@NotNull
	@Size(min = 2, max = 50)
	private String lastName;

	@NotNull
	@Size(min = 5, max = 50)
	@Column(unique = true)
	private String username;

	@NotNull
	@Size(min = 8)
	private String password;


	@Size(min = 1, max = 20)
	private String employeeId;

	@Size(max = 50)
	private String designation;

	@Past
	private LocalDate dateOfJoining;

	@Past
	private LocalDate dateOfBirth;


	private String gender;

	@Min(value = 0)
	private long salary;

	@Pattern(regexp = "^(ACTIVE|INACTIVE)$")
	private String status;
//	private MediaFile profilePic;

	@OneToOne
	private Contact contact;

	@OneToOne
	private Address address1;

	@OneToOne
	private Address address2;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "deparment_id")
	@JsonBackReference
	private Department department;

	@ManyToMany
	@JoinTable(name = "employee_projects", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<Project> projects;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	


}
