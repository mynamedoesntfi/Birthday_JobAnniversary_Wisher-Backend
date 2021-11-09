package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.RequestRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.TeamRepository;
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
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.getUserByUsername(username);
            logger.info("userInfo= " + user);
            if (user != null) {
//              Adding user roles to granted authorities collection
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole()));

                return (UserDetails) new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(), authorities);
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public User registerUser(User user) throws Exception {
//        User newUser = new User();
//        newUser.setUsername(user.getUsername());
//        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//        newUser.setPassword(user.getPassword());
            return userRepository.createUser(user);

    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
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

    public Set<?> getAllEmailsWithInMonthEvents() {
        return userRepository.emailsWithInMonthEvents();
    }

    public Map<String, List<User>> getAllUsersWithInMonthEvents() {
        Map<String, List<User>> usersWithUpcomingEvents = new HashMap<>();
        usersWithUpcomingEvents.put("Birthday", userRepository.getAllUsersWithInMonthBirthDays());
        usersWithUpcomingEvents.put("Anniversary", userRepository.getAllUsersWithInMonthJobAnniversaries());
        return usersWithUpcomingEvents;
    }

    public Map<String, List<?>> getAllTeamMembersWithUpcomingEvents(Integer teamID, String username) {
        Integer userID = userRepository.getUserByUsername(username).getUserID();

        Map<String, List<?>> teamMembersWithUpcomingEvents = new HashMap<>();
        teamMembersWithUpcomingEvents.put("Birthday", teamRepository.getAllTeamMembersWithUpcomingBirthDays(teamID, userID));
        teamMembersWithUpcomingEvents.put("Anniversary", teamRepository.getAllTeamMembersWithUpcomingJobAnniversaries(teamID, userID));
        return teamMembersWithUpcomingEvents;
    }

    public int updateUserDetails(Integer id, User user) {
        return userRepository.updateUserDetails(id,user);
    }


}
