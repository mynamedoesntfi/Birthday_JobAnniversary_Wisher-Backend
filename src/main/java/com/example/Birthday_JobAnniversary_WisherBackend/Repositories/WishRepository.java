package com.example.Birthday_JobAnniversary_WisherBackend.Repositories;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.Wish;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.WishRequestBody;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.TeamRowMapper;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.utils.WishRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class WishRepository {

    private static final Logger logger = LoggerFactory.getLogger(WishRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Wish addWish(Integer toID, WishRequestBody wishRequestBody, Date eventDate) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        logger.info("Adding wish ...");
        try{
            String query = "insert into wishes(sender_id, receiver_id, subject, message, send_date)" +
                    " values(?, ?, ?, ?, " +
                    "DATE_FORMAT(DATE_ADD(?, INTERVAL (YEAR(CURRENT_DATE()) - YEAR(?) + if(DAYOFYEAR(CURRENT_DATE()) > DAYOFYEAR(?), 1, 0)) YEAR), '%Y-%m-%d'))";
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
                            ps.setInt(1, wishRequestBody.getFromID());
                            ps.setInt(2, toID);
                            ps.setString(3, wishRequestBody.getSubject());
                            ps.setString(4, wishRequestBody.getMessage());
                            ps.setDate(5, eventDate);
                            ps.setDate(6, eventDate);
                            ps.setDate(7, eventDate);
                            return ps;
                        }
                    }, keyHolder);
            logger.info("Request ID = {}", Objects.requireNonNull(keyHolder.getKey()).intValue());
            return getWishByID(Objects.requireNonNull(keyHolder.getKey()).intValue());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    private Wish getWishByID(int id) {
        Wish wish = null;
        try {
            String query = "select * from wishes where id=?";
            wish = jdbcTemplate.query(query, new WishRowMapper(), id).get(0);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return wish;
    }

    public List<Wish> getAllWishesForToday() {
        List<Wish> wishes = null;
        try {
            String query = "select * from wishes where send_date=CURRENT_DATE and status='PENDING'";
            wishes = jdbcTemplate.query(query, new WishRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return wishes;
    }

    public List<Wish> getAllWishes() {
        List<Wish> wishes = null;
        try {
            String query = "select * from wishes";
            wishes = jdbcTemplate.query(query, new WishRowMapper());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return wishes;
    }
}
