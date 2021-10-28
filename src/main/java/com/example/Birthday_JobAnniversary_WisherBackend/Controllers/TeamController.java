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

    /** localhost:8080/api/teams */
    @GetMapping()
    @RequestMapping(value = "/teams")
    public ResponseEntity<?> getAllTeams() {

        try {
            List<Team> teams = teamService.getAllTeams();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", teams);
            logger.info("Teams retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get teams. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/teams/{id} */
    @GetMapping()
    @RequestMapping(value = "/teams/{id}", method = RequestMethod.GET)
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

    /** localhost:8080/api/teams/new */
    @PostMapping
    @RequestMapping(value = "/teams/new")
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

    /** localhost:8080/api/teams/new */
    @DeleteMapping
    @RequestMapping(value = "/teams/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeamById(@PathVariable Integer id){
        try {
            List<User> updatedUsers = teamService.deleteTeamById(id);
            Map<String, Object> response = new HashMap<>();
            if(updatedUsers == null)
                response.put("message", "Success, no users updated");
            else
                response.put("message", "Success, users updated");
            response.put("data", updatedUsers);
            logger.info("Team deleted successfully. ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Cannot delete. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
