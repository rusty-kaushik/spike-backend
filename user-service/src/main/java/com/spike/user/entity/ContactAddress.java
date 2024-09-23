package com.spike.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
//	@JsonBackReference
    private Contacts contact;

    @NotNull(message = "Address Line 1 cannot be null")
    @Column(name = "address_line1", nullable = false)
    private String line1;

    @Column(name = "address_line2", nullable = true)
    private String line2;

    @NotNull(message = "State cannot be null")
    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "district", nullable = true)
    private String district;

    @NotBlank(message = "Zip code cannot be blank")
    @Column(name = "zip", nullable = false)
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
