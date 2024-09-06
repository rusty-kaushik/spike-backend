package com.spike.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user_profile_picture")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "profilePicture")
    @JsonBackReference
    private User user;

    @Column(name="original_file_name")
    private String originalFileName;

    @Column(name="file_name")
    private String fileName;

    @Column(name="file_path",nullable = false)
    private String filePath;

    @Column(name="file_type" , nullable = false)
    private String fileType;

    @Column(name="file_size", nullable = false)
    private Long fileSize;
}
