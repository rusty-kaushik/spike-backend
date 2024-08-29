package com.blog.repository.entity;


import com.blog.repository.auditing.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
public class UserSocials  extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long user_id;
    @Size(max=255)
    @Column(name="linkedin_url")
    private String linkedin_url;
    @Size(max=255)
    @Column(name="facebook_url")
    @Size(max=255)
    private String facebook_url;
    @Size(max=255)
    @Column(name="instagram_url")
    private String instagram_url;

    public UserSocials() {
    }

    public UserSocials(Long id, Long user_id, String linkedin_url, String facebook_url, String instagram_url) {
        this.id = id;
        this.user_id = user_id;
        this.linkedin_url = linkedin_url;
        this.facebook_url = facebook_url;
        this.instagram_url = instagram_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getLinkedin_url() {
        return linkedin_url;
    }

    public void setLinkedin_url(String linkedin_url) {
        this.linkedin_url = linkedin_url;
    }

    public String getFacebook_url() {
        return facebook_url;
    }

    public void setFacebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
    }

    public String getInstagram_url() {
        return instagram_url;
    }

    public void setInstagram_url(String instagram_url) {
        this.instagram_url = instagram_url;
    }
}
