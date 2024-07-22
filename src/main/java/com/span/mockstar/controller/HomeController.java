package com.span.mockstar.controller;

import com.span.mockstar.entity.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
class HomeController {

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<String> welcome(@RequestParam String name) {
        return ResponseEntity
                .ok()
                .body("Welcome to Your Financial Journey " + name + "!");
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<String> welcome(@RequestBody User user) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(302))
                .headers(headers -> headers.add("location", "https://www.google.com"))
                .body("You will be redirected to <a target=\"_new\" href=\"https://www.google.com\">Google.com</a>.");
    }

}
