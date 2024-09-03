package com.in2it.spykeemployee.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	


	@Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @OneToOne
    @JoinColumn(name = "manager_id")
    @JsonManagedReference
    private Employee manager;

    @OneToOne
    @JoinColumn(name = "admin_id")
    @JsonIgnore
    private Employee admin;

    @OneToMany(mappedBy = "department")
    @JsonBackReference
    private Set<Employee> employees = new HashSet<>();
    
    
    

}
