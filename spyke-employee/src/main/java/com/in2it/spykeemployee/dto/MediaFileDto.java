package com.in2it.spykeemployee.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MediaFileDto {
	
	private UUID id;
	private String name;
	private String path;

}
