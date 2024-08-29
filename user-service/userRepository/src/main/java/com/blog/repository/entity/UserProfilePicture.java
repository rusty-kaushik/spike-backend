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
    @Column(name="file_path",nullable = false)
    private String file_path;
    @Column(name="file_type" , nullable = false)
    private String file_type;
    @Column(name="file_size", nullable = false)
    private Long file_size;

    public UserProfilePicture() {
    }

    public UserProfilePicture(Long id, Long user_id, String file_name, String file_path, String file_type, Long file_size) {
        this.id = id;
        this.user_id = user_id;
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_type = file_type;
        this.file_size = file_size;
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

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public Long getFile_size() {
        return file_size;
    }

    public void setFile_size(Long file_size) {
        this.file_size = file_size;
    }
}
