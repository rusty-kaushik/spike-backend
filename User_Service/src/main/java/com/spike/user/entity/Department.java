package com.spike.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spike.user.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "departments")
    @JsonBackReference
    private Set<User> users = new HashSet<>();
}
