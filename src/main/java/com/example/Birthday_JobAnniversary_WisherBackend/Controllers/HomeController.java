package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.AuthenticationRequest;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.AuthenticationResponse;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.JwtUtilService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/** localhost:8080/api **/
@RestController
public class HomeController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtilService jwt;

    /** localhost:8080/api */
    @GetMapping("/")
    public String home() {
        return("Welcome to our application!");
    }

    /** localhost:8080/api/authenticate */
    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        //region authenticating the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }
        //endregion

        //region generating token using user details
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwtToken = jwt.generateToken(userDetails);
        //endregion

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    /** localhost:8080/api/user */
    @GetMapping("/user")
    public String user() {
        return("Welcome User!");
    }

    /** localhost:8080/api/admin */
    @GetMapping("/admin")
    public String admin() {
        return("Welcome Admin!");
    }

    /** localhost:8080/api/users */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /** localhost:8080/api/user/{id} */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserByUsername(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
