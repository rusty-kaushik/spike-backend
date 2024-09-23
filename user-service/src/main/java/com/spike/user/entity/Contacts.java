package com.spike.user.entity;

import com.spike.user.auditing.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contacts extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private long userId;
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    private String designation;
    @Column(nullable = false, unique = true, length = 10)
    @Size(max = 11, min = 10)
    private String primaryMobile;
    @OneToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<ContactAddress> addresses;
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;

}
