package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RequestsRowMapper implements RowMapper<Map<?,?>> {
    @Override
    public Map<?,?> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<Object,Object> requests=new HashMap<>();

        requests.put("id",rs.getInt("id"));
        requests.put("user_id",rs.getInt("user_id"));
        requests.put("current_team_id",rs.getInt("current_team_id"));
        requests.put("new_team_id",rs.getInt("new_team_id"));
        requests.put("status",rs.getString("status"));
        requests.put("username",rs.getString("username"));
        requests.put("new_team_name",rs.getString("team_name"));
        requests.put("time_stamp",rs.getDate("last_updated"));
        return requests;
    }
}
