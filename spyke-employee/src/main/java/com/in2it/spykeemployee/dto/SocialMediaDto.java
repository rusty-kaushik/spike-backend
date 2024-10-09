package com.in2it.spykeemployee.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SocialMediaDto {
	
	private UUID id;
	private String name;
	private String link;

}
