package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;


import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();
        user.setUserID(resultSet.getInt("user_ID"));
        user.setUsername(resultSet.getString("username"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setContact(resultSet.getString("contact"));
        user.setImage(resultSet.getString("profile_image"));
        user.setGender(resultSet.getString("gender"));
        user.setAddress(resultSet.getString("address"));
        user.setBirthDate(resultSet.getDate("birth_date"));
        user.setHireDate(resultSet.getDate("hire_date"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
        user.setTeamID(resultSet.getString("team_ID"));

        return user;
    }
}
