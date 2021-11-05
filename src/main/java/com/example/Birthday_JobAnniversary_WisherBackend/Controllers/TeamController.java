package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.UserReturn;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.JwtUtilService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.TeamService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.UserService;
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
public class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private UserService userService;

    /**
     * localhost:8080/api/teams/{id}/members
     */
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<?> getTeamMembersByTeamId(@PathVariable Integer id) {
        try {
            List<User> users = teamService.getTeamMembersByTeamId(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Team members retrieved");
            response.put("data", UserReturn.convertUsersList(users));
            logger.info("Team members retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get team members. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * localhost:8080/api/teams
     */
    @GetMapping()
    @RequestMapping(value = "/teams")
    public ResponseEntity<?> getAllTeams() {

        try {
            List<Team> teams = teamService.getAllTeams();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "All teams retrieved");
            response.put("data", teams);
            logger.info("All Teams retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get teams. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * localhost:8080/api/teams/{id}
     */
    @GetMapping()
    @RequestMapping(value = "/teams/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTeamById(@PathVariable Integer id) {

        try {
            Team team = teamService.getTeamById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Team details retrieved");
            response.put("data", team);
            logger.info("Team details retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get team. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * localhost:8080/api/teams/{id}
     */
    @GetMapping()
    @RequestMapping(value = "/teams/data", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTeamDetails() {

        try {
            List<Map<?,?>> team = teamService.getAllTeamsData();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "All Teams data retrieved");
            response.put("data", team);
            logger.info("All Teams data retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get team. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    /**
     * localhost:8080/api/admin/teams/new
     */
    @PostMapping
    @RequestMapping(value = "/admin/teams/new")
    public ResponseEntity<?> addNewTeam(@RequestBody Team team) {
        try {
            Team newTeam = teamService.addNewTeam(team);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Team added successfully");
            response.put("data", newTeam);
            logger.info("Team added successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot add team. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * localhost:8080/api/admin/teams/{id}
     */
    @DeleteMapping
    @RequestMapping(value = "/admin/teams/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeamById(@PathVariable Integer id) {
        try {
            List<User> updatedUsers = teamService.deleteTeamById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            if (updatedUsers == null)
                response.put("message", "Team deleted successfully");
            else
                response.put("message", "Team deleted successfully");
            response.put("data", UserReturn.convertUsersList(updatedUsers));
            logger.info("Team deleted successfully. ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot delete. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * localhost:8080/api/teams/{id}/upcomingEvents
     */
    @GetMapping("/teams/{id}/upcomingEvents")
    public ResponseEntity<?> getAllTeamMembersWithUpcomingEvents(@PathVariable Integer id, @RequestHeader("Authorization") String jwt) {
        try {
            jwt = jwt.substring(7);
            String username = jwtUtilService.extractUsername(jwt);

            Map<String, List<?>> users = userService.getAllTeamMembersWithUpcomingEvents(id, username);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Team events retrieved");
            response.put("data", users);
            logger.info("Team members with upcoming events retrieved successfully. ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot get team members with upcoming events. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
