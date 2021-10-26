package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Implements UserDetailsService for use with SpringSecurity
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User userInfo = userRepository.getUserInfo(username);
//            System.out.println("inside service");
            logger.info("userInfo= " + userInfo);
            if (userInfo != null) {
//              Adding user roles to granted authorities collection
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(userInfo.getRole()));

                return (UserDetails) new org.springframework.security.core.userdetails.User(
                        userInfo.getUsername(), userInfo.getPassword(), authorities);
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * TODO: ENTER OTHER SERVICE QUERIES
     *
     * /* update user details
     *
     * /* get user details
     *
     * /* get user's bday or job anniversary date
     *
     * /* send bday wish
     */




}
