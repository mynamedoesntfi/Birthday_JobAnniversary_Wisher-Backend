package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.TeamService;
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

    /** localhost:8080/api/teams/{id}/members */
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<?> getTeamMembersByTeamId(@PathVariable Integer id) {
        try {
            List<User> users = teamService.getTeamMembersByTeamId(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", users);
            logger.info("Team members retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get team members. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping()
    @RequestMapping(value = "/teams/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Integer id) {

        try {
            Team team = teamService.getTeamById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", team);
            logger.info("Team retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get team. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/teams/new", method = RequestMethod.POST)
    public ResponseEntity<?> addNewTeam(@RequestBody Team team){
        try {
            Team newTeam = teamService.addNewTeam(team);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", newTeam);
            logger.info("Team added successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot add team. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
