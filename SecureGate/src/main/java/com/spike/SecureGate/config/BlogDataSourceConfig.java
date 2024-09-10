package com.spike.SecureGate.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class BlogDataSourceConfig {

    @Value("${spring.datasource.blog.url}")
    private String url;

    @Value("${spring.datasource.blog.username}")
    private String username;

    @Value("${spring.datasource.blog.password}")
    private String password;

    @Bean(name = "blogDataSource")
    public DataSource blogDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "blogJdbcTemplate")
    public JdbcTemplate blogJdbcTemplate(@Qualifier("blogDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}