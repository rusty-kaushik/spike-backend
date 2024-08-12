package com.in2it.commentandlikeservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String uploadImage( MultipartFile file) throws Exception;
}
