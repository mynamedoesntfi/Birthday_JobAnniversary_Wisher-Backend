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

        while (resultSet.next()) {
            List<Map<Object, Object>> mapArrayList;
            Map<Object, Object> map = new HashMap<>();
            String key = resultSet.getString("team_ID");

            map.put("userID", resultSet.getInt("user_ID"));
            map.put("username", resultSet.getString("username"));
            map.put("firstName", resultSet.getString("first_name"));
            map.put("lastName", resultSet.getString("last_name"));
            map.put("email", resultSet.getString("email"));
            map.put("teamname", resultSet.getString("team_name"));
            map.put("description", resultSet.getString("description"));

            if (data.containsKey(key)) {
                mapArrayList = data.get(key);
            } else {
                mapArrayList = new ArrayList<>();
            }
            mapArrayList.add(map);
            data.put(resultSet.getString("team_ID"), mapArrayList);

        }
        return data;
    }
}