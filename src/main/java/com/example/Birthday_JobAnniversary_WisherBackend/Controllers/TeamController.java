package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    /** localhost:8080/api/teams/{id}/members */
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<?> getTeamMembersByTeamId(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.getTeamMembersByTeamId(id));
    }
}
