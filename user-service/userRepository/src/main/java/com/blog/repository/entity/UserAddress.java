package com.blog.repository.entity;


import com.blog.repository.auditing.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class UserAddress extends Auditable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "User ID cannot be null")
    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user_id;
    @NotNull(message = "Address Line 1 cannot be null")
    @Column(name = "address_line1", nullable = false)
    private String addressLine1;
    @Column(name = "address_line2")
    private String addressLine2;
    @NotNull(message = "State cannot be null")
    @Column(name = "state", nullable = false)
    private State state;
    @NotNull(message = "District cannot be null")
    @Column(name = "district", nullable = false)
    private String district;
    @NotBlank(message = "Zip code cannot be blank")
   // @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Invalid zip code format")
    @Column(name = "zip", nullable = false, length = 10)
    private String zip;
    @NotNull(message = "City cannot be null")
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "nearest_landmark")
    private String nearestLandmark;
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private Type type;
    @Column(name = "country", nullable = false)
    private String country;

    public UserAddress() {
    }

    public UserAddress(Long id, User user_id, String addressLine1, String addressLine2, State state, String district, String zip, String city, String nearestLandmark, Type type, String country) {
        this.id = id;
        this.user_id = user_id;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.state = state;
        this.district = district;
        this.zip = zip;
        this.city = city;
        this.nearestLandmark = nearestLandmark;
        this.type = type;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return user_id;
    }

    public void setUserId(User user_id) {
        this.user_id = user_id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNearestLandmark() {
        return nearestLandmark;
    }

    public void setNearestLandmark(String nearestLandmark) {
        this.nearestLandmark = nearestLandmark;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}

