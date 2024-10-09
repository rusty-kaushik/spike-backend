package com.in2it.spykeemployee.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressDto {
	
	private UUID id;
	private String type;
	private String houseNo;
	private String streetNo;
	private String area;
	private String district;
	private String state;
	private String country;
	private int pinCode;
	

}
