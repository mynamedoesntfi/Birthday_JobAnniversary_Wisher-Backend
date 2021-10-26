package com.springsecurity.jdbc;

import com.springsecurity.jdbc.models.AuthenticationRequest;
import com.springsecurity.jdbc.models.AuthenticationResponse;
import com.springsecurity.jdbc.services.UserService;
import com.springsecurity.jdbc.services.jwtServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    private jwtServices jwt;

    @GetMapping("/")
    public String home() {
        return("<h1>Welcome to our application!</h1>");
    }

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwtToken = jwt.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @GetMapping("/user")
    public String user() {
        return("<h1>Welcome User!</h1>");
    }

    @GetMapping("/admin")
    public String admin() {
        return("<h1>Welcome Admin!</h1>");
    }

}
