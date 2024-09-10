package com.spike.SecureGate.JdbcHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlogDbService {

    private static final Logger logger = LoggerFactory.getLogger(BlogDbService.class);

    private final JdbcTemplate jdbcTemplate;

    public BlogDbService(@Qualifier("blogJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //CHECKS IF A BLOG EXISTS OR NOT
    public boolean doesBlogExist(String id) {
        String sql = "SELECT COUNT(*) FROM blog WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

}