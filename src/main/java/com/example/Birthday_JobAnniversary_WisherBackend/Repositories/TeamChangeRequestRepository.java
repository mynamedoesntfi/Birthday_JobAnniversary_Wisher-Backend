package com.example.Birthday_JobAnniversary_WisherBackend.Repositories;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Enums.TeamChangeRequestStatus;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.TeamChangeRequest;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.TeamChangeRequestRowMapper;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.TeamRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class TeamChangeRequestRepository {

    private static final Logger logger = LoggerFactory.getLogger(TeamChangeRequestRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public TeamChangeRequest createTeamChangeRequest(User user, Integer teamID) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        logger.info("request to change teams for UserID: {}, {} -> {}", user.getUserID(), user.getTeamID(), teamID);
        try{
            String query = "insert into requests(user_id, current_team_id, new_team_id) values(?,?,?)";
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
                            ps.setInt(1, user.getUserID());
                            ps.setInt(2, user.getTeamID());
                            ps.setInt(3, teamID);
                            return ps;
                        }
                    }, keyHolder);
            logger.info("Request ID = {}", Objects.requireNonNull(keyHolder.getKey()).intValue());
            return getRequestByID(Objects.requireNonNull(keyHolder.getKey()).intValue());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    private TeamChangeRequest getRequestByID(int id) {
        TeamChangeRequest request = null;
        try {
            String query = "select * from requests where id=?";
            request = jdbcTemplate.query(query, new TeamChangeRequestRowMapper(), id).get(0);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return request;
    }

    public List<TeamChangeRequest> getAllTeamChangeRequests() {
        List<TeamChangeRequest> requests = null;
        try {
            String query = "select * from requests";
            requests = jdbcTemplate.query(query, new TeamChangeRequestRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return requests;
    }

    public List<TeamChangeRequest> getAllPendingTeamChangeRequests() {
        List<TeamChangeRequest> requests = null;
        try {
            String query = "select * from requests where status='PENDING'";
            requests = jdbcTemplate.query(query, new TeamChangeRequestRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return requests;
    }

    public TeamChangeRequest approveRequestByID(Integer requestID) {
        try {
            String query = "update requests set status=? where id=?";
            jdbcTemplate.update(query, TeamChangeRequestStatus.APPROVED.toString(), requestID);
            logger.info("Approved request - requestID: {}", requestID);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return getRequestByID(requestID);
    }

    public TeamChangeRequest declineRequestByID(Integer requestID) {
        try {
            String query = "update requests set status=? where id=?";
            jdbcTemplate.update(query, TeamChangeRequestStatus.DECLINED.toString(), requestID);
            logger.info("Declined request - requestID: {}", requestID);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return getRequestByID(requestID);
    }
}
