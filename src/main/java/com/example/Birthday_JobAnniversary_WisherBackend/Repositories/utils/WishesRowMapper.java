package com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Wish;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishesRowMapper implements RowMapper<Wish> {
    @Override
    public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wish wish = new Wish();

        wish.setWishID(rs.getInt("id"));
        wish.setSenderID(rs.getInt("sender_id"));
        wish.setReceiverID(rs.getInt("receiver_id"));
        wish.setSubject(rs.getString("subject"));
        wish.setMessage(rs.getString("message"));
        wish.setSendDate(rs.getDate("send_date"));
        wish.setStatus(rs.getString("status"));

        return wish;
    }
}
