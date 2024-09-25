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
    @Column(nullable = false)
    private long userId;
   
    @Column(nullable = false)
    private String name;
    private String designation;
    @Column(nullable = false)
    private String primaryMobileNumber;
    @OneToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<ContactAddress> addresses;
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;

}
