package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /** localhost:8080/api/testUser */
    @GetMapping("/testUser")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello User");
    }

    /** localhost:8080/api/users */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /** localhost:8080/api/users/{id} */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

//    /** localhost:8080/api/users/{id} */
//    @RequestMapping(value = "/users/{id}",method = RequestMethod.PUT)
//    public ResponseEntity<?> updateDetails(@RequestBody User user) {
//        return ResponseEntity.ok(userService.updateUserDetails(user));
//    }
}
