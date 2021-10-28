package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.TeamChangeRequest;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.TeamChangeRequestRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.TeamChangeRequestService;
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
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    TeamChangeRequestService teamChangeRequestService;

    /** localhost:8080/api/admin/testAdmin */
    @GetMapping("/admin/testAdmin")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello Admin");
    }


    /** localhost:8080/api/admin/requests */
    @GetMapping()
    @RequestMapping(value = "/admin/requests")
    public ResponseEntity<?> getAllTeamChangeRequests() {
        try {
            List<TeamChangeRequest> requests = teamChangeRequestService.getALlTeamChangeRequests();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", requests);
            logger.info("Requests retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get requests. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/admin/requests/{id}/approve */
    @PutMapping()
    @RequestMapping(value = "/admin/requests/{id}/approve")
    public ResponseEntity<?> approveRequestByID(@PathVariable(value = "id") Integer requestID) {
        try {
            TeamChangeRequest request = teamChangeRequestService.approveRequestByID(requestID);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", request);
            logger.info("Request approved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot approve request. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/admin/requests/{id}/decline */
    @PutMapping()
    @RequestMapping(value = "/admin/requests/{id}/decline")
    public ResponseEntity<?> declineRequestByID(@PathVariable(value = "id") Integer requestID) {
        try {
            TeamChangeRequest request = teamChangeRequestService.declineRequestByID(requestID);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", request);
            logger.info("Request declined.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot decline request. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
