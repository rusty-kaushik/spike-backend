package com.spike.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spike.user.auditing.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_address")
public class UserAddress extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @NotNull(message = "Address Line 1 cannot be null")
    @Column(name = "address_line1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line2", nullable = true)
    private String addressLine2;

    @NotNull(message = "State cannot be null")
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull(message = "District cannot be null")
    @Column(name = "district", nullable = false)
    private String district;

    @NotBlank(message = "Zip code cannot be blank")
    @Pattern(regexp = "^[0-9]{6}(?:-[0-9]{4})?$", message = "Invalid zip code format")
    @Column(name = "zip", nullable = false, length = 10)
    private String zip;

    @NotNull(message = "City cannot be null")
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "nearest_landmark", nullable = true)
    private String nearestLandmark;

    @Column(name = "type")
    private String type;

    @Column(name = "country", nullable = false)
    private String country;

}