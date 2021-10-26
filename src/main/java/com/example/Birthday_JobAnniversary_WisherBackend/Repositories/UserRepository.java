package com.example.Birthday_JobAnniversary_WisherBackend.Repositories;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
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
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserInfo(String username) {
//        logger.info("inside repository");
//        logger.info(username);
        try {
            String query = "select * from users where username=?";
            List<User> userResponse = jdbcTemplate.query(query,new UserRowMapper(),username);
            logger.info("userResponse= " + userResponse);
            if (username != null) {
                System.out.println(userResponse.get(0));
                return userResponse.get(0);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

//    public int insertUser(User user) {
//        try {
//            String username = user.getUserName();
//            String pass = user.getPassword();
//
//            logger.info("vvv::  username= "+username + " ,pass= "+pass );
//            int updateStatus = template.update("insert into userDetail(username,password) values(?,?)", new Object[] {username,encoder.encode(pass)});
//            logger.info("vvv::  updateStatus= "+updateStatus);
//            if(updateStatus == 1) {
//                return 1;
//            }
//            return 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
}
