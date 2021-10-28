package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Enums.EventSubject;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.TeamChangeRequestRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implements UserDetailsService for use with SpringSecurity
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamChangeRequestRepository teamChangeRequestRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User userInfo = userRepository.getUserByUsername(username);
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

    public User registerUser(User user) {
//        User newUser = new User();
//        newUser.setUsername(user.getUsername());
//        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//        newUser.setPassword(user.getPassword());
        return userRepository.createUser(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(Integer id) {
        return userRepository.getUserById(id);
    }

    public User removeUserFromTeam(Integer userId) {
        return userRepository.removeUserFromTeam(userId);
    }

    public Map<String, List<User>> getAllUsersWithUpcomingEvents() {
        Map<String, List<User>> usersWithUpcomingEvents = new HashMap<>();
        usersWithUpcomingEvents.put("Birthday", userRepository.getAllUsersWithUpcomingBirthDays());
        usersWithUpcomingEvents.put("Anniversary", userRepository.getAllUsersWithUpcomingJobAnniversaries());
        return usersWithUpcomingEvents;
    }

//
//    public User updateUserDetails(User user) {
//        return userRepository.updateUserDetails(user);
//    }


}
