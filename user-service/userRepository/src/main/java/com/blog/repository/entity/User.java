package com.blog.repository.entity;

import com.blog.repository.auditing.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "user_master")

public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50)
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String userName;

    @Size(min = 3, max = 50)
    @Column(name = "employee_code", nullable = false, unique = true, length = 50)
    private String empCode;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Size(min = 8)
    @Column(name = "password", nullable = false)
    private String password;


    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;


    @Size(min = 10, max = 10)
    @Column(name = "mobile_number", nullable = false, length = 10)
    private String mobile;

    @Column(name = "joining_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date joiningDate;

    @Email
    @Column(name = "backup_email", nullable = false, unique = true)
    private String backupEmail;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    @Column(name = "manager_id", nullable = false)
    private Long managerId;

    @Column(name = "salary",nullable = false)
    private String salary;


    @Column(name="designation" , nullable =false)
    private String designation;

    @Column(name = "post_create", nullable = false)
    private boolean postCreate = true;

    @ManyToMany
    @JoinTable(
            name = "user_department",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @OneToOne
    private UserSocials userSocials;

    @OneToOne
    private UserProfilePicture profilePicture;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<UserAddress> userAddress;


    public User() {
    }

    public User(Long id, String userName, String empCode, String email, String password, String name, String mobile, Date joiningDate, String backupEmail, String status, Long managerId, String designation, boolean postCreate, Set<Department> departments, Role role, UserSocials userSocials, UserProfilePicture profilePicture, Set<UserAddress> userAddress,String salary) {
        this.id = id;
        this.userName = userName;
        this.empCode = empCode;
        this.email = email;
        this.password = password;
        this.name = name;
        this.salary= salary;
        this.mobile = mobile;
        this.joiningDate = joiningDate;
        this.backupEmail = backupEmail;
        this.status = status;
        this.managerId = managerId;
        this.designation = designation;
        this.postCreate = postCreate;
        this.departments = departments;
        this.role = role;
        this.userSocials = userSocials;
        this.profilePicture = profilePicture;
        this.userAddress = userAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 3, max = 50) String getUserName() {
        return userName;
    }

    public void setUserName(@Size(min = 3, max = 50) String userName) {
        this.userName = userName;
    }

    public @Size(min = 3, max = 50) String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(@Size(min = 3, max = 50) String empCode) {
        this.empCode = empCode;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @Size(min = 8) String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 8) String password) {
        this.password = password;
    }

    public @Size(min = 1, max = 100) String getName() {
        return name;
    }

    public void setName(@Size(min = 1, max = 100) String name) {
        this.name = name;
    }


    public @Size(min = 10, max = 10) String getMobile() {
        return mobile;
    }

    public void setMobile(@Size(min = 10, max = 10) String mobile) {
        this.mobile = mobile;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public @Email String getBackupEmail() {
        return backupEmail;
    }

    public void setBackupEmail(@Email String backupEmail) {
        this.backupEmail = backupEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public boolean isPostCreate() {
        return postCreate;
    }

    public void setPostCreate(boolean postCreate) {
        this.postCreate = postCreate;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserSocials getUserSocials() {
        return userSocials;
    }

    public void setUserSocials(UserSocials userSocials) {
        this.userSocials = userSocials;
    }

    public UserProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(UserProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<UserAddress> getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(Set<UserAddress> userAddress) {
        this.userAddress = userAddress;
    }
}
