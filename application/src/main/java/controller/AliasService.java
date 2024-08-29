package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AliasService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getAliasType(String username) {
        String sql = "SELECT alias_type FROM aliases WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, String.class);
    }

    public String getAliasValue(String username) {
        String sql = "SELECT alias_value FROM aliases WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, String.class);
    }
}

