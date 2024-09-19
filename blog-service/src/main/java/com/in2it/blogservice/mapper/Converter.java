package com.in2it.blogservice.mapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in2it.blogservice.dto.BlogDto;
import com.in2it.blogservice.model.Blog;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Converter {

	@Autowired
	ModelMapper mapper;

	/* static way to create file path */
	public final String fileUploadDir = ".\\blog-media";

	private static long randomId = 0;

	public Converter() throws IOException {

	}

	// Here we changed DtoModel to Entity
	public Blog dtoToBlogConverter(BlogDto dto, List<String> fileName, List<String> uploadedPath) {
		Blog blog = mapper.map(dto, Blog.class);

		blog.setCreatedDateTime(LocalDateTime.now());
		blog.setMediaFile(fileName);
		blog.setMediaPath(uploadedPath);
		blog.setStatus(true);

		return blog;
	}

	// Here we changed Entity to DtoModel
	public BlogDto blogToDtoConverter(Blog blog) {
		mapper.getConfiguration().setAmbiguityIgnored(true);
		BlogDto dto = mapper.map(blog, BlogDto.class);

		if (blog.getMediaFile() != null) {

			dto.setMediaFile(getEncodeFile(blog.getMediaFile()));
		}

		return dto;
	}

	// uploading file in file System
	public Map<String, List<String>> uploadFile(List<MultipartFile> multipartFile) {

		Map<String, List<String>> map = new HashMap<>();

		String fullPath = null;

		List<String> originalFilenames = new ArrayList<>();
		List<String> paths = new ArrayList<>();

		File file = new File(fileUploadDir);

		if (!file.isDirectory()) {
			try {
				Files.createDirectories(Path.of(fileUploadDir));
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		try {
			// replaced code in one line type 2

			for (MultipartFile multipartFile2 : multipartFile) {

				randomId = generateUniqueId();
				String fileName = randomId + multipartFile2.getOriginalFilename();
				fullPath = fileUploadDir + File.separator + fileName;

				paths.add(fullPath);
				map.put("media_path", paths);

				originalFilenames.add(fileName);
				map.put("file_name", originalFilenames);

				log.info("full path ====================" + fullPath);

				// This code is inserted file in file system
				Files.copy(multipartFile2.getInputStream(), Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return map;

	}

	// Generating URIlink in response
	public List<String> genrateUriLink(List<MultipartFile> multipartFile) {

		List<String> fileLink = new ArrayList<>();
		if (multipartFile != null) {

			for (MultipartFile multipart : multipartFile) {

				String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path("/")
						.path(randomId + multipart.getOriginalFilename()).toUriString();
				fileLink.add(uriString);

			}
		}
		return fileLink;

	}

	// Generating Random Id its generate hash code its use for rename file
	public static long generateUniqueId() {
		return System.currentTimeMillis();
	}

	// encode file in base64
	public List<String> getEncodeFile(List<String> fileName) {

		List<String> paths = new ArrayList<>();
		for (String string : fileName) {
			File file = new File(fileUploadDir + File.separator + string);
			log.info("file name " + file);

			byte[] fileContent;
			try {
				fileContent = FileUtils.readFileToByteArray(file);
				String encodedString = Base64.getEncoder().encodeToString(fileContent);
				paths.add(encodedString);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return paths;
	}

}
