package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;


import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();
        user.setUserID(resultSet.getInt("user_ID"));
        user.setUsername(resultSet.getString("username"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setBirthDate(resultSet.getDate("birth_date"));
        user.setEmail(resultSet.getString("email"));
        user.setHireDate(resultSet.getDate("hire_date"));
        user.setTeamID(resultSet.getInt("team_ID"));
        return user;
        }
}