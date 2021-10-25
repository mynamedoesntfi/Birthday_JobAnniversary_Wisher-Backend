package com.example.Birthday_JobAnniversary_WisherBackend.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    @RequestMapping("/")
    String welcome() {
        return "<h1>Welcome<h1>";
    }

}
