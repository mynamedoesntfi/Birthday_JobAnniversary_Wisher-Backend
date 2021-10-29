package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamChangeRequestRowMapper implements RowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();

        request.setRequestID(rs.getInt("id"));
        request.setUserID(rs.getInt("user_id"));
        request.setCurrentTeamID(rs.getInt("current_team_id"));
        request.setNew_team_ID(rs.getInt("new_team_id"));
        request.setStatus(rs.getString("status"));

        return request;
    }
}
