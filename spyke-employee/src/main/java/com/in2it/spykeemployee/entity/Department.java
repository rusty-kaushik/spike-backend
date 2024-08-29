package com.in2it.spykeemployee.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
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
public class Department {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.UUID)
//	private UUID id;
//	
//    @NotBlank
//    @Size(max = 100)
//    @Column(unique = true, nullable = false)
//	private String name;
//    
//    @Size(max = 500)
//	private String discription;
//    
//    @NotBlank
//	private String managerId;
//    
//    @NotBlank
//	private String adminId;
	

//    @ManyToMany
//    @JoinTable(
//        name = "department_employees",
//        joinColumns = @JoinColumn(name = "department_id"),
//        inverseJoinColumns = @JoinColumn(name = "employee_id")
//    )
//	private List<Employee> employees;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Adjust strategy if UUIDs are used
    private UUID id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @OneToOne
    @JoinColumn(name = "manager_id") // Assuming 'managerId' should be a foreign key
    private Employee manager;

    @OneToOne
    @JoinColumn(name = "admin_id") // Assuming 'adminId' should be a foreign key
    private Employee admin;

    @ManyToMany(mappedBy = "departments")
    @JsonIgnore
    private List<Employee> employees;


}
