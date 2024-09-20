package com.spike.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spike.user.auditing.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "employee_code", nullable = false, unique = true)
    private String employeeCode;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "primary_mobile_number", nullable = false, unique = true)
    private String primaryMobileNumber;

    @Column(name = "secondary_mobile_number", nullable = true)
    private String secondaryMobileNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "joining_date")
    private Date joiningDate;

    @Email
    @Column(name = "backup_email", nullable = true)
    private String backupEmail;

    @Column(name = "manager_id", nullable = true)
    private Long managerId;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "designation", nullable = true)
    private String designation;

    @Column(name = "post_create", nullable = false)
    private boolean postCreate = true;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_department",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @JsonManagedReference
    private Set<Department> departments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @JsonManagedReference
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_socials_id", referencedColumnName = "id") // Unique column name for UserSocials
    @JsonManagedReference
    private UserSocials userSocials;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture_id", referencedColumnName = "id") // Unique column name for UserProfilePicture
    private UserProfilePicture profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserAddress> addresses = new ArrayList<>();


    public void addAddress(UserAddress userAddress) {
        addresses.add(userAddress);
        userAddress.setUser(this);
    }

    public void addPicture(UserProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
        profilePicture.setUser(this); // Ensures the bidirectional relationship is maintained
    }

    public void addSocial(UserSocials userSocials) {
        this.userSocials = userSocials;
        userSocials.setUser(this); // Ensures the bidirectional relationship is maintained
    }

    public void addDepartment(Department department) {
        departments.add(department);
        department.getUsers().add(this);
    }

}
