package com.in2it.blogmongo.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component

public class UploadFileHelper {

//	public final String UPLOAD_DIR = "D:\\Springboot-mongodb-workspace\\blog-mongo\\src\\main\\resources\\static\\media";
	public final String MEDIA_DIR = new ClassPathResource("static/media/").getFile().getAbsolutePath();
	
	

//	public final String MEDIA_DIR = new ClassPathResource("static/media/").getFile().getAbsolutePath();

	public UploadFileHelper() throws IOException {

		System.out.println("Media dir "+MEDIA_DIR);
	}

	public String uploadFile(MultipartFile file) {
		boolean f = false;

		try {
//			InputStream stream = file.getInputStream();
//			byte data[]= new byte[stream.available()];
//			stream.read(data);
//			
//			FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+File.separator+file.getOriginalFilename());
//			fos.write(data);
//			fos.flush();
//			fos.close();
			
			
			String randomId = UUID.randomUUID().toString();
			System.out.println("Random id "+ randomId);
//			String newFileName = randomId.concat(name.substring(name.lastIndexOf(".")));
			String newFileName = randomId.concat(file.getOriginalFilename());
			
			System.out.println("new file name "+ newFileName);

//			Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
//			Files.copy(file.getInputStream(), Paths.get(MEDIA_DIR + File.separator + file.getOriginalFilename()),
//					StandardCopyOption.REPLACE_EXISTING);
			long copy = Files.copy(file.getInputStream(), Paths.get(MEDIA_DIR + File.separator + newFileName),
					StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("Copy "+ copy);
//			String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path("/media")
//					.path(file.getOriginalFilename()).toUriString();
			String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path("media/")
					.path(newFileName).toUriString();
			
			
			System.out.println(uriString + " From helper class");
			f = true;
			return uriString;

		} catch (Exception e) {
			System.out.println("from helper");
			e.printStackTrace();
		}

		return null;
	}

}
