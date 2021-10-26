package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;


import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        /**
         * TODO: FIX
         */

        /*
        user.setUserID(resultSet.getInt("user_ID"));
        user.setUserName(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
        */

        return user;
    }
}
