package com.in2it.commentandlikeservice.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.commentandlikeservice.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	public String uploadImage( MultipartFile file) throws Exception {
		String name = file.getOriginalFilename();
		File file1=new File("media");
		if(file1.isDirectory())System.out.println(file1.getAbsolutePath());
		String path=file1.getAbsolutePath();
		String filePath = path + "/" + name;
		file.transferTo(new File(filePath));
//		File f = new File(path);
//
//		if (!f.exists()) {
//			f.mkdir();
//		}
//
//		Files.copy(file.getInputStream(), Paths.get(filePath).resolve(file.getOriginalFilename()));
		return name;
	}
}
