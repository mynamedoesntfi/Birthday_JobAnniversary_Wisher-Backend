package com.example.Birthday_JobAnniversary_WisherBackend.Filters;

import com.example.Birthday_JobAnniversary_WisherBackend.Services.UserService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.JwtUtilService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtilService.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token for this user");
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has been expired");
            }
        } else {
            logger.warn("JWT Token do not start with Bearer keyword");
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userService.loadUserByUsername(username);

            if (jwtUtilService.validateToken(jwtToken, userDetails)) {

                /**
                 * Following is stuff spring would do by default, but have to do it manually since we are
                 * validating the jwt (validating jwt is not part of default behaviour)
                 */
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

//                logger.info("\n User details {} Credentials: " + usernamePasswordAuthenticationToken.getCredentials() +
//                "Principal: "+ usernamePasswordAuthenticationToken.getPrincipal()
//                );

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        /** To continue remaining filter operations on the request */
        filterChain.doFilter(request, response);
    }
}
