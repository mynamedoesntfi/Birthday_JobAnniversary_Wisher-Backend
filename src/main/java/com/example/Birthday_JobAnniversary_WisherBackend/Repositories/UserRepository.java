package com.example.Birthday_JobAnniversary_WisherBackend.Repositories;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.Wish;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.EventRowMapper;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.UserRowMapper;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.WishRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Repository
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username) {
        try {
            String query = "select * from users where username=?";
            List<User> userResponse = jdbcTemplate.query(query, new UserRowMapper(), username);
            if (username != null) {
                logger.info("userResponse: " + userResponse.get(0));
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
            String query = "select * from users order by username";
            users = jdbcTemplate.query(query, new UserRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return users;
    }

    public User createUser(User user) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        logger.info("details to enter:{} ", user);
        try {
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
            if (e instanceof SQLIntegrityConstraintViolationException || e instanceof DuplicateKeyException)
                throw new SQLIntegrityConstraintViolationException("Username already exists");
            else
                throw new Exception(e.getMessage());
        }
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

    public User removeUserFromTeam(Integer userID) {
        try {
            String query = "update users set team_id=? where user_id=?";
            jdbcTemplate.update(query, null, userID);
            logger.info("Removed from team - userID: {}", userID);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return getUserById(userID);
    }

    public void changeTeamByID(int userID, int new_team_id) {
        System.out.println("here: " + userID + " - " + new_team_id);
        try {
            String query = "update users set team_id=? where user_id=?";
            jdbcTemplate.update(query, new_team_id, userID);
            logger.info("Changed team - userID: {}", userID);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
    }

        public Set<?> emailsWithInMonthEvents() {
        try {
           List<String> emails= jdbcTemplate.queryForList("select email from users "+
                    "where MONTH(hire_date)=MONTH(CURRENT_DATE()) or MONTH(birth_date)=MONTH(CURRENT_DATE())", String.class);

            return new HashSet<>(emails);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    public List<User> getAllUsersWithInMonthBirthDays() {
        try {
            String query =
                    "select user_ID,username,first_name,last_name,email,hire_date,team_ID," +
                            "DATE_ADD(birth_date, INTERVAL YEAR(CURRENT_DATE()) - YEAR(birth_date) + IF(DAYOFYEAR(CURRENT_DATE())>DAYOFYEAR(birth_date), 1, 0) YEAR) AS birth_date from users\n" +
                            "where MONTH(birth_date)=MONTH(CURRENT_DATE())";
            return jdbcTemplate.query(query, new EventRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public List<User> getAllUsersWithInMonthJobAnniversaries() {
        try {
            String query =
                    "select user_ID,username,first_name,last_name,email,birth_date,team_ID," +
                            "DATE_ADD(hire_date, INTERVAL YEAR(CURRENT_DATE()) - YEAR(hire_date) + IF(DAYOFYEAR(CURRENT_DATE())>DAYOFYEAR(hire_date), 1, 0) YEAR) AS hire_date from users\n" +
                            "where MONTH(hire_date)=MONTH(CURRENT_DATE())";
            return jdbcTemplate.query(query, new EventRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    public int updateUserDetails(Integer id, User user) {
        int res = 0;
        logger.info("details to update={}", user);
        try {
            String query = "update users set birth_date=?, hire_date=?, gender=?, address=?,contact=? where user_ID=?";
            res = jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(query, new String[]{"userID"});
                            ps.setDate(1, user.getBirthDate());
                            ps.setDate(2, user.getHireDate());
                            ps.setObject(3, user.getGender());
                            ps.setObject(4, user.getAddress());
                            ps.setObject(5, user.getContact());
                            ps.setInt(6, id);
                            return ps;
                        }
                    });
            logger.info("Edited User Details");
            return res;
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            logger.error(e.getMessage());
        }
        return res;
    }
}
