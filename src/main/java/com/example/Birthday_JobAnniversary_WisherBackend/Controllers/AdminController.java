package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Request;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.UserReturn;
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


    /** localhost:8080/api/admin/users/removeFromTeam */
    @RequestMapping(value = "admin/users/removeFromTeam",method = RequestMethod.POST)
    public ResponseEntity<?> removeUserFromTeam(@RequestBody Integer id) {
        try {
            User user = userService.removeUserFromTeam(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "User removed from team successfully");
            response.put("data", new UserReturn(user));
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
    public ResponseEntity<?> getAllRequests() {
        try {
            List<Map<?,?>> requests = requestService.getAllRequests();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Requests retrieved");
            response.put("data", requests);
            logger.info("Requests retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get requests. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/admin/{id}/requests/pending */
    @GetMapping()
    @RequestMapping(value = "/admin/{id}/requests/pending")
    public ResponseEntity<?> getAllPendingRequests(@PathVariable(value = "id") Integer userID) {
        try {
            List<Map<?,?>> requests = requestService.getAllPendingRequests(userID);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Pending requests retrieved");
            response.put("data", requests);
            logger.info("Pending requests retrieved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot get pending requests. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/admin/requests/approve */
    @PostMapping()
    @RequestMapping(value = "/admin/requests/approve")
    public ResponseEntity<?> approveRequestByID(@RequestBody Integer requestID) {
        try {
            Map<?,?> request = requestService.approveRequestByID(requestID);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Request approved");
            response.put("data", request);
            logger.info("Request approved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot approve request. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** localhost:8080/api/admin/requests/decline */
    @PostMapping()
    @RequestMapping(value = "/admin/requests/decline")
    public ResponseEntity<?> declineRequestByID(@RequestBody Integer requestID) {
        try {
            Map<?,?> request = requestService.declineRequestByID(requestID);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Request declined");
            response.put("data", request);
            logger.info("Request declined.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot decline request. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }







    /** localhost:8080/api/admin/sendEmailInvite */
    @PostMapping
    @RequestMapping(value = "/admin/sendEmailInvite")
    public ResponseEntity<?> sendEmailInvite(){
        try {
            emailService.sendEmailInvite("dcb0113977-4a2a66@inbox.mailtrap.io", //
                    "Celebration at end of month", //
                    "Invitation of celebration of events that occurred within the month." //
            );
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Email sent successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Cannot send email. Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
