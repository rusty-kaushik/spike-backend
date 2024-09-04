package com.in2it.spykeemployee.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResisterRequest {
	
	private String firstName;
	private String lastName;
	private String gender;
	private String password;
	private String username;

}
