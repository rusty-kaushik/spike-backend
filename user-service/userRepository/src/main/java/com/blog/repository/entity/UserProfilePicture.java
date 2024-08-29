package com.blog.repository.entity;

import com.blog.repository.auditing.Auditable;
import jakarta.persistence.*;


@Entity
@Table(name="UserProfilePicture")
public class UserProfilePicture extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long user_id;
    @Column(name="file_name")
    private String file_name;
    @Column(name="file_path")
    private String file_path;

    public UserProfilePicture() {
    }

    public UserProfilePicture(Long id, Long user_id, String file_name, String file_path) {
        this.id = id;
        this.user_id = user_id;
        this.file_name = file_name;
        this.file_path = file_path;
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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
