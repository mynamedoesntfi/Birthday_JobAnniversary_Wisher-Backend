package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Request;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.EmailService;
import com.example.Birthday_JobAnniversary_WisherBackend.Services.RequestService;
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
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private EmailService emailService;

    /** localhost:8080/api/admin/testAdmin */
    @GetMapping("/admin/testAdmin")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello Admin");
    }


    /** localhost:8080/api/admin/users/{id}/removeFromTeam */
    @GetMapping("admin/users/{id}/removeFromTeam")
    public ResponseEntity<?> removeUserFromTeam(@PathVariable Integer userId) {
        try {
            User user = userService.removeUserFromTeam(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", user);
            logger.info("User removed from team successfully. ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Could not remove user from team. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    /** localhost:8080/api/admin/requests */
    @GetMapping()
    @RequestMapping(value = "/admin/requests")
    public ResponseEntity<?> getAllTeamChangeRequests() {
        try {
            List<Request> requests = requestService.getALlTeamChangeRequests();
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

    /** localhost:8080/api/admin/requests/pending */
    @GetMapping()
    @RequestMapping(value = "/admin/requests/pending")
    public ResponseEntity<?> getAllPendingTeamChangeRequests() {
        try {
            List<Request> requests = requestService.getALlPendingTeamChangeRequests();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("data", requests);
            logger.info("Pending requests retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get pending requests. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/admin/requests/{id}/approve */
    @PatchMapping()
    @RequestMapping(value = "/admin/requests/{id}/approve")
    public ResponseEntity<?> approveRequestByID(@PathVariable(value = "id") Integer requestID) {
        try {
            Request request = requestService.approveRequestByID(requestID);
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
    @PatchMapping()
    @RequestMapping(value = "/admin/requests/{id}/decline")
    public ResponseEntity<?> declineRequestByID(@PathVariable(value = "id") Integer requestID) {
        try {
            Request request = requestService.declineRequestByID(requestID);
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

    /** localhost:8080/api/admin/sendEmail */
    @PostMapping
    @RequestMapping(value = "/admin/sendEmail")
    public String sendEmailMessage(){
        emailService.sendMessage("springboot.dummy.test.email@gmail.com", //
                "Sent using spring boot", //
                "Text Here ..." //
                );
        return "Email sent";
    }
}
