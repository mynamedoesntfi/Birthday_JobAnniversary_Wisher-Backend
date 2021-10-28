package com.example.Birthday_JobAnniversary_WisherBackend.Repositories;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.TeamRowMapper;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.UserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class TeamRepository {

    private static final Logger logger = LoggerFactory.getLogger(TeamRepository.class);

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
}
