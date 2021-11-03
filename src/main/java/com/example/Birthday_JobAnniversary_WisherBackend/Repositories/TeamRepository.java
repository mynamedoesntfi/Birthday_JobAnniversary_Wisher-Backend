package com.example.Birthday_JobAnniversary_WisherBackend.Repositories;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.Wish;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.EventRowMapper;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.TeamRowMapper;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.UserRowMapper;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.WishRowMapper;
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
import java.util.*;

@Repository
public class TeamRepository {

    private static final Logger logger = LoggerFactory.getLogger(TeamRepository.class);

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param id Team ID
     * @return - List of team members
     * - Empty list if no team members
     */
    public List<User> getTeamMembersByTeamId(Integer id) {
        List<User> users = null;
        try {
            String query = "select * from users where team_ID=?";
            users = jdbcTemplate.query(query, new UserRowMapper(), id);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return users;
    }

    public List<Team> getAllTeams() {
        List<Team> teams = null;
        try {
            String query = "select * from teams";
            teams = jdbcTemplate.query(query, new TeamRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return teams;
    }

    public Team getTeamById(Integer id) {
        Team team = null;
        try {
            String query = "select * from teams where team_ID=?";
            team = jdbcTemplate.query(query, new TeamRowMapper(), id).get(0);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return team;
    }

    public Team addNewTeam(Team team) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        logger.info("details to enter:{} ", team);
        try {
            String query = "insert into teams(team_name, description) values(?,?)";
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(query, new String[]{"team_id"});
                            ps.setString(1, team.getTeamname());
                            ps.setString(2, team.getDescription());
                            return ps;
                        }
                    }, keyHolder);
            logger.info("Registered teamID = {}", Objects.requireNonNull(keyHolder.getKey()).intValue());
            return getTeamById(Objects.requireNonNull(keyHolder.getKey()).intValue());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public void deleteTeam(Integer id) {
        String query = "delete from teams where team_id=?";
        jdbcTemplate.update(query, id);
    }


    public List<?> getAllTeamMembersWithUpcomingBirthDays(Integer teamID, Integer userID) {

        List<User> users = null;
        List<Wish> wishes = null;
        List<Map<Object, Object>> events = new ArrayList<>();

        try {
            String query =
                    "select user_ID,username,first_name,last_name,email,hire_date,team_ID," +
                            "DATE_ADD(birth_date, INTERVAL YEAR(CURRENT_DATE()) - YEAR(birth_date) + IF(DAYOFYEAR(CURRENT_DATE())>DAYOFYEAR(birth_date), 1, 0) YEAR) AS birth_date from users\n" +
                            "where (DATE_ADD(birth_date, INTERVAL YEAR(CURRENT_DATE()) - YEAR(birth_date) + IF(DAYOFYEAR(CURRENT_DATE())>DAYOFYEAR(birth_date), 1, 0) YEAR)\n" +
                            "\t\tBETWEEN CURRENT_DATE()+7 AND DATE_ADD(CURRENT_DATE(), INTERVAL 7 DAY)) and team_ID=? and user_id!=?";
            users = jdbcTemplate.query(query, new EventRowMapper(), teamID, userID);

            String q = "select * from wishes where sender_id=? and subject='BIRTHDAY_WISHES';";
            wishes = jdbcTemplate.query(q, new WishRowMapper(), userID);


            System.out.println(wishes);
            for (User user : users) {
                Map<Object, Object> res = new HashMap<>();
                res.put("username", user.getUsername());
                res.put("first_name", user.getFirstName());
                res.put("last_name", user.getLastName());
                res.put("user_ID", user.getUserID());
                res.put("email", user.getEmail());
                res.put("birth_date", user.getBirthDate());
                res.put("hire_date", user.getHireDate());
                res.put("wish_id", null);
                res.put("status", null);

                for (Wish wish : wishes) {
                    if ((wish.getReceiverID() == user.getUserID()) && (wish.getSenderID() == userID) &&
                            (wish.getSendDate().toLocalDate().toString().equals(user.getBirthDate().toLocalDate().toString()))) {
                        res.replace("wish_id", wish.getWishID());
                        res.replace("status",wish.getStatus());
                    }
                }
                events.add(res);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return events;
    }

    public List<?> getAllTeamMembersWithUpcomingJobAnniversaries(Integer teamID, Integer userID) {
        List<User> users = null;
        List<Wish> wishes = null;
        List<Map<Object, Object>> events = new ArrayList<>();
        try {
            String query =
                    "select user_ID,username,first_name,last_name,email,birth_date,team_ID," +
                            "DATE_ADD(hire_date, INTERVAL YEAR(CURRENT_DATE()) - YEAR(hire_date) + IF(DAYOFYEAR(CURRENT_DATE())>DAYOFYEAR(hire_date), 1, 0) YEAR) AS hire_date from users\n" +
                            "where (DATE_ADD(hire_date, INTERVAL YEAR(CURRENT_DATE()) - YEAR(hire_date) + IF(DAYOFYEAR(CURRENT_DATE())>DAYOFYEAR(hire_date), 1, 0) YEAR)\n" +
                            "\t\tBETWEEN CURRENT_DATE()+7 AND DATE_ADD(CURRENT_DATE(), INTERVAL 7 DAY)) and team_ID=? and user_id!=?;";
            users = jdbcTemplate.query(query, new EventRowMapper(), teamID, userID);

            String q = "select * from wishes where sender_id=? and subject='JOB_ANNIVERSARY_WISHES';";
            wishes = jdbcTemplate.query(q, new WishRowMapper(), userID);

            System.out.println(wishes);
            for (User user : users) {
                Map<Object, Object> res = new HashMap<>();
                res.put("username", user.getUsername());
                res.put("first_name", user.getFirstName());
                res.put("last_name", user.getLastName());
                res.put("user_ID", user.getUserID());
                res.put("email", user.getEmail());
                res.put("birth_date", user.getBirthDate());
                res.put("hire_date", user.getHireDate());
                res.put("wish_id", null);
                res.put("status", null);

                for (Wish wish : wishes) {
                    if ((wish.getReceiverID() == user.getUserID()) && (wish.getSenderID() == userID) &&
                            (wish.getSendDate().toLocalDate().toString().equals(user.getHireDate().toLocalDate().toString()))) {
                        res.replace("wish_id", wish.getWishID());
                        res.replace("status",wish.getStatus());
                    }
                }
                events.add(res);
            }

        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return events;
    }
}
