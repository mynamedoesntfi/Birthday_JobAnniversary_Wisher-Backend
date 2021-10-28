package com.example.Birthday_JobAnniversary_WisherBackend.Repositories;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.UserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserInfo(String username) {
//        logger.info("inside repository");
//        logger.info(username);
        try {
            String query = "select * from users where username=?";
            List<User> userResponse = jdbcTemplate.query(query, new UserRowMapper(), username);
//            logger.info("userResponse= " + userResponse);
            if (username != null) {
               logger.info("userResponse: "+userResponse.get(0));
                return userResponse.get(0);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            String query = "select * from users";
            users = jdbcTemplate.query(query, new UserRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return users;
    }

    public User getUserById(Integer id) {
        User user = null;
        try {
            String query = "select * from users where user_id=?";
            user = jdbcTemplate.query(query, new UserRowMapper(), id).get(0);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return user;
    }

    public User register(User user){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        logger.info("details to enter:{} ",user);
        try{
            String query = "insert into users(username,first_name,last_name,email,password) values(?,?,?,?,?)";
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(query, new String[]{"userID"});
                            ps.setString(1, user.getUsername());
                            ps.setString(2, user.getFirstName());
                            ps.setString(3, user.getLastName());
                            ps.setString(4, user.getEmail());
                            ps.setString(5, user.getPassword());
                            return ps;
                        }
                    }, keyHolder);
            logger.info("Registered userID = {}", Objects.requireNonNull(keyHolder.getKey()).intValue());
            return getUserById(Objects.requireNonNull(keyHolder.getKey()).intValue());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public User removeFromTeam(User user) {
        try {
            String query = "update users set team_id=? where user_id=?";
            jdbcTemplate.update(query, null, user.getUserID());
            logger.info("Removed from team - userID: {}, username: {}, oldTeamID: {}",
                    user.getUserID(),
                    user.getUsername(),
                    user.getTeamID());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return getUserById(user.getUserID());
    }

//    public User updateUserDetails(User user){
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        logger.info("details to update={} ",user);
//        try{
//            String query = "update users set username,first_name,last_name,email,password) values(?,?,?,?,?)";
//            jdbcTemplate.update(
//                    new PreparedStatementCreator() {
//                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                            PreparedStatement ps = connection.prepareStatement(query, new String[]{"userID"});
//                            ps.setString(1, user.getUsername());
//                            ps.setString(2, user.getFirstName());
//                            ps.setString(3, user.getLastName());
//                            ps.setString(4, user.getEmail());
//                            ps.setString(5, user.getPassword());
//                            return ps;
//                        }
//                    }, keyHolder);
//            logger.info("Registered userID = {}", Objects.requireNonNull(keyHolder.getKey()).intValue());
//            return getUserById(Objects.requireNonNull(keyHolder.getKey()).intValue());
//        } catch (Exception e) {
//            logger.error(Arrays.toString(e.getStackTrace()));
//        }
//        return null;
//    }
}
