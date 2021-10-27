package com.example.Birthday_JobAnniversary_WisherBackend.Config;

import com.example.Birthday_JobAnniversary_WisherBackend.Filters.JwtRequestFilter;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
//                setting true for enabled/active fields as users table doesn't contain those fields
                .usersByUsernameQuery(
                        "SELECT username, password, true FROM users WHERE username=?")
//                users role can be either ROLE_ADMIN or ROLE_USER
                .authoritiesByUsernameQuery(
                        "SELECT username, role FROM users WHERE username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable().authorizeRequests()

                //region ADMIN URLS
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/users").hasRole("ADMIN")
                //endregion

                //region USER URLS
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers("/teams/{id}/members").hasAnyRole("USER", "ADMIN")
                //endregion

                //region FULL ACCESS URLS
                .antMatchers("/").permitAll()
                //endregion
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .and().formLogin();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
