package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.TeamChangeRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamChangeRequestRowMapper implements RowMapper<TeamChangeRequest> {
    @Override
    public TeamChangeRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        TeamChangeRequest request = new TeamChangeRequest();

        request.setRequestID(rs.getInt("id"));
        request.setUserID(rs.getInt("user_id"));
        request.setCurrentTeamID(rs.getInt("current_team_id"));
        request.setNew_team_ID(rs.getInt("new_team_id"));
        request.setStatus(rs.getString("status"));

        return request;
    }
}
