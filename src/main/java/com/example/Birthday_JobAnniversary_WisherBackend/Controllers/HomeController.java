package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.AuthenticationRequest;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.JwtUtilService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.TeamService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * localhost:8080/api
 **/
@RestController
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private JwtUtilService jwt;

    /**
     * localhost:8080/api
     */
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.status(HttpStatus.OK).body("Welcome to our application");
    }

    /**
     * localhost:8080/api/login
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        Map<String, Object> response = new HashMap<>();

        //region authenticating the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("message", "Incorrect username and password");
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        //endregion

        //region generating token using user details
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwtToken = jwt.generateToken(userDetails);
        //endregion

        response.put("status", "success");
        response.put("message", "Logged In successfully");
        response.put("data", jwtToken);
        logger.info("Loggen In successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * localhost:8080/api/signup
     */
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> data = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Registration Successful");
            data.put("authToken","something");
            data.put("userData",newUser);
            response.put("data", data);
            logger.info("User registered successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot register user. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
