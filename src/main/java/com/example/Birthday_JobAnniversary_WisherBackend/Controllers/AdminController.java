package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    /** localhost:8080/api/admin/testAdmin */
    @GetMapping("/admin/testAdmin")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello Admin");
    }
}
