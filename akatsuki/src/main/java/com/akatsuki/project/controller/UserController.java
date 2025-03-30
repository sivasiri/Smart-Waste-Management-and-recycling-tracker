package com.akatsuki.project.controller;

import com.akatsuki.project.model.User;
import com.akatsuki.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.akatsuki.project.dto.LoginRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST: Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String result = userService.registerUser(user);
        if (result.contains("exists")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/api/test/secure")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("You have accessed a secure endpoint!");
    }
    @GetMapping("/hello")
    public String sayHello() {
        return "👋 Hello! You're authenticated!";
    }

    

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String response = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (response.contains("successful")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(response);
    }

}
