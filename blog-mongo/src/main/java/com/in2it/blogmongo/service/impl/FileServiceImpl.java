package com.in2it.blogmongo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.in2it.blogmongo.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadMedia(String path, MultipartFile file) throws IOException {
		
		String name = file.getOriginalFilename();
		
		String randomId = UUID.randomUUID().toString();
		
//		String newFileName = randomId.concat(name.substring(name.lastIndexOf(".")));
		String newFileName = randomId.concat(name);
		
		String filePath = path + File.separator + newFileName;
		
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return newFileName;
			
	}

	@Override
	public InputStream getMedea(String path, String fileName) throws FileNotFoundException{
		
		String fullPath = path + File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		
		
		return is;
	}

}
