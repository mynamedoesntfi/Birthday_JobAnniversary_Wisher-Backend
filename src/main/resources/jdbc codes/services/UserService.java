package com.springsecurity.jdbc.services;

import com.springsecurity.jdbc.models.User;
import com.springsecurity.jdbc.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User userInfo = userRepository.getUserInfo(username);
            logger.info("userInfo= " + userInfo);
            if (userInfo != null) {
                return (UserDetails) new org.springframework.security.core.userdetails.User(
                        userInfo.getUserName(), userInfo.getPassword(), new ArrayList<>());
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}