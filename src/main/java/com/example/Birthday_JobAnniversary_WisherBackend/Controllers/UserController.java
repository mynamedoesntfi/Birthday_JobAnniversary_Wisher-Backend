package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.*;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.JwtUtilService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.RequestService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.UserService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.WishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private WishService wishService;

    @Autowired
    private JwtUtilService jwtUtilService;


    /** localhost:8080/api/testUser */
    @GetMapping("/testUser")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello User");
    }

    /** localhost:8080/api/users */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "all users retrieved");
            response.put("data", users);
            logger.info("all users retrieved successfully. ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot get users. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/users/{id} */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "user retrieved");
            response.put("data", user);
            logger.info("user retrieved successfully. ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot get user. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/users/{id}/teamChangeRequest/{teamID} */
    @PostMapping()
    @RequestMapping(value = "/users/{userID}/teamChangeRequest/{teamID}")
    public ResponseEntity<?> createRequest(@PathVariable(value = "userID") Integer userID, @PathVariable(value = "teamID") Integer teamID) {

        try {
            Request request = requestService.createRequest(userID, teamID);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "request created");
            response.put("data", request);
            logger.info("Team change request created.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot create team change request. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/users/{id}/requests */
    @GetMapping("/users/{id}/requests")
    public ResponseEntity<?> getRequestsByUserId(@PathVariable Integer id) {
        try {
            List<Request> requests = requestService.getRequestsByUserId(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "requests retrieved");
            response.put("data", requests);
            logger.info("User requests retrieved successfully. ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot get user requests. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/users/upcomingEvents */
    @GetMapping("/users/upcomingEvents")
    public ResponseEntity<?> getAllUsersWithUpcomingEvents(@RequestHeader("Authorization") String jwt) {
        try {
            jwt = jwt.substring(7);
            String username = jwtUtilService.extractUsername(jwt);

            Map<String,List<User>> users = userService.getAllUsersWithUpcomingEvents(username);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "users retrieved");
            response.put("data", users);
            logger.info("Users with upcoming events retrieved successfully. ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot get users with upcoming events. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/users/{toID}/wish */
    @PostMapping()
    @RequestMapping(value = "/users/{toID}/wish")
    public ResponseEntity<?> wishUserByID(@PathVariable(value = "toID") Integer toID, @RequestBody WishRequestBody wishRequestBody) {
        try {
            Wish wish = wishService.wishUserByID(toID, wishRequestBody);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "wish added");
            response.put("data", wish);
            logger.info("Wish created, will be sent on event day.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot create wish. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



//    /** localhost:8080/api/users/{id} */
//    @RequestMapping(value = "/users/{id}",method = RequestMethod.PUT)
//    public ResponseEntity<?> updateDetails(@RequestBody User user) {
//        return ResponseEntity.ok(userService.updateUserDetails(user));
//    }
}
