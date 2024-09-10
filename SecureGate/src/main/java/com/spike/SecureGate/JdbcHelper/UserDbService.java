package com.spike.SecureGate.JdbcHelper;

import com.spike.SecureGate.DTO.publicDto.JwtDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class UserDbService {

    private static final Logger logger = LoggerFactory.getLogger(UserDbService.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDbService(@Qualifier("userJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // GETS ALL DEPARTMENT NAMES
    public List<String> getAllDepartmentNames() {
        String sql = "SELECT name FROM department";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("name"));
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    // Method to get all department IDs
    public List<Long> getAllDepartmentIds() {
        String sql = "SELECT id FROM department";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"));
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("Error fetching department IDs: {}", e.getMessage());
            return Collections.emptyList();
        }
    }


    // Method to get id by username as long
    public long getIdByUsername(String username) {
        String sql = "SELECT id FROM user_master WHERE username = ?";
        try {
            Long result = jdbcTemplate.queryForObject(sql, new Object[]{username}, Long.class);
            if (result == null) {
                return -1;
            }
            return result;
        } catch (EmptyResultDataAccessException e) {
            return -1;
        } catch (Exception e) {
            logger.error("Error fetching department ID: {}", e.getMessage());
            return -1;
        }
    }

    public JwtDTO getUserByUsername(String username) {
        logger.info("Running query in database to fetch user credentials");
        String sql = "SELECT um.username, um.password, r.name " +
                "FROM user_master um " +
                "JOIN role r ON um.role_id = r.id " +
                "WHERE um.username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, new RowMapper<JwtDTO>() {
                public JwtDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    JwtDTO jwtDTO = new JwtDTO();
                    jwtDTO.setUsername(rs.getString("username"));
                    jwtDTO.setPassword(rs.getString("password"));
                    jwtDTO.setRoleName(rs.getString("name"));
                    logger.info("Query returned credentials");
                    return jwtDTO;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.error("User not found with username: {}", username);
            return null;
        }
    }

}
