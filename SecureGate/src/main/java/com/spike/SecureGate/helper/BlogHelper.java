package com.spike.SecureGate.helper;

import com.spike.SecureGate.DTO.blogDto.BlogCreationFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogCreationRequestDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateFeignDTO;
import com.spike.SecureGate.DTO.blogDto.BlogUpdateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class BlogHelper {

    // CONVERTS A BLOG CREATION REQUEST DATA TO FEIGN CREATION REQUEST DATA
    public BlogCreationFeignDTO blogCreationDtoTOFeignDto(String userName, BlogCreationRequestDTO content) {
        BlogCreationFeignDTO blogDto = new BlogCreationFeignDTO();
        blogDto.setUserName(userName);
        blogDto.setTitle(content.getTitle());
        blogDto.setContent(content.getContent());
        blogDto.setDepartmentId(content.getDepartmentId());
        return blogDto;
    }

    // CONVERTS A BLOG UPDATE REQUEST DATA TO FEIGN UPDATE REQUEST DATA
    public BlogUpdateFeignDTO blogUpdateDtoTOFeignDto(String userName, BlogUpdateRequestDTO blogUpdateRequestDTO) {
        BlogUpdateFeignDTO  blogUpdateRequestDTO1 = new BlogUpdateFeignDTO();
        blogUpdateRequestDTO1.setUserName(userName);
        blogUpdateRequestDTO1.setTitle(blogUpdateRequestDTO.getTitle());
        blogUpdateRequestDTO1.setContent(blogUpdateRequestDTO.getContent());
        return blogUpdateRequestDTO1;
    }
}
