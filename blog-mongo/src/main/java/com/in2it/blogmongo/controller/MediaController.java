package com.in2it.blogmongo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogmongo.service.FileService;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/medias")
public class MediaController {
	
	@Value("${project.media}")
	String path;
	
	@Autowired
	FileService service;
	
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
	public String UploadMedia(@RequestParam("media") MultipartFile media) {
		
		String fileName = null;
		try {
			fileName = service.uploadMedia(path, media);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		}
		
		return "file uploadded succefully";
		
	}

}
