package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRowMapper implements RowMapper<Team> {
    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        Team team = new Team();
        team.setTeamID(rs.getInt("team_ID"));
        team.setTeamname(rs.getString("team_name"));
        team.setDescription(rs.getString("description"));

        return team;
    }
}
