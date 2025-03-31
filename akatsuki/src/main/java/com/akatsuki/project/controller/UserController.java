package com.akatsuki.project.controller;

import com.akatsuki.project.model.User;
import com.akatsuki.project.service.UserService;
import com.akatsuki.project.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.akatsuki.project.dto.LoginRequest;
import com.akatsuki.project.dto.UserUpdateRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;

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
    
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUserProfile(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractEmail(token);
        User user = userService.getUserByEmail(email);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        user.setPassword(null); // Don't return password
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody UserUpdateRequest updateRequest,
                                                HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractEmail(token);

        String result = userService.updateUserProfile(email, updateRequest);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file,
                                                       HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String token = jwtUtil.extractTokenFromRequest(request);
            String email = jwtUtil.extractEmail(token);

            byte[] fileBytes = file.getBytes();  // Convert to byte array

            userService.updateProfilePicture(email, fileBytes);

            return ResponseEntity.ok("Profile picture uploaded successfully and stored in DB");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
    @PostMapping("/update-profile-picture")
    public ResponseEntity<String> updateProfilePicture(@RequestParam("file") MultipartFile file,
                                                       HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractEmail(token);

        try {
            byte[] imageBytes = file.getBytes();
            userService.updateProfilePicture(email, imageBytes);
            return ResponseEntity.ok("Profile picture updated successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }



}
