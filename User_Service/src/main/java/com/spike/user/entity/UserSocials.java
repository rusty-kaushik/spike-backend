package com.spike.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spike.user.auditing.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="user_social_urls")
public class UserSocials extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userSocials")
    @JsonBackReference
    private User user;

    @Size(max=255)
    @Column(name="linkedin_url")
    private String linkedinUrl;

    @Size(max=255)
    @Column(name="facebook_url")
    private String facebookUrl;

    @Size(max=255)
    @Column(name="instagram_url")
    private String instagramUrl;
}
