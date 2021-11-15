package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamsDataRowMapper implements RowMapper<Map<?, ?>> {
    public Map<?, ?> mapRow(ResultSet resultSet, int i) throws SQLException {

        Map<Object, List<Map<Object, Object>>> data = new HashMap<>();

        do{
            List<Map<Object, Object>> members;
            Map<Object, Object> member = new HashMap<>();
            String key = resultSet.getString("team_ID");

            member.put("userID", resultSet.getInt("user_ID"));
            member.put("username", resultSet.getString("username"));
            member.put("firstName", resultSet.getString("first_name"));
            member.put("lastName", resultSet.getString("last_name"));
            member.put("email", resultSet.getString("email"));
            member.put("teamname", resultSet.getString("team_name"));
            member.put("description", resultSet.getString("description"));

            if (data.containsKey(key)) {
                members = data.get(key);
            } else {
                members = new ArrayList<>();
            }
            members.add(member);
            data.put(resultSet.getString("team_ID"), members);

        }while (resultSet.next());
            return data;
    }
}