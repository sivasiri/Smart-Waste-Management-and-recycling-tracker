package com.akatsuki.project.controller;

import com.akatsuki.project.model.User;
import com.akatsuki.project.service.BarcodeService;
import com.akatsuki.project.service.UserService;
import com.akatsuki.project.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akatsuki.project.dto.BarcodeProductResponse;
import com.akatsuki.project.dto.LoginRequest;
import com.akatsuki.project.dto.UserUpdateRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // POST: Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("age") int age,
            @RequestParam("sex") String sex,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture
    ) {
        String result = userService.registerUser(firstName, lastName, email, password, phone, address, age, sex, profilePicture);
        if (result.contains("exists")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    
    @GetMapping("/api/test/secure")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("You have accessed a secure endpoint!");
    }
    
    //@CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboardGreeting(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractEmail(token);
        User user = userService.getUserByEmail(email);

        String name = user.getFirstName();
        return ResponseEntity.ok("👋 Welcome, " + name + "! Here's your Smart Waste Dashboard.");
    }


    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.loginUserAndGenerateToken(loginRequest.getEmail(), loginRequest.getPassword());

        if (token == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
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
    
    @CrossOrigin(origins = "http://localhost:3000")
    @RestController
    @RequestMapping("/api/classify")
    public class BarcodeController {

        @Autowired
        private BarcodeService barcodeService;

        @GetMapping("/barcode/{barcode}")
        public ResponseEntity<?> classifyUsingBarcode(@PathVariable String barcode) {
            BarcodeProductResponse result = barcodeService.classifyByBarcode(barcode);

            if (result == null) {
                return ResponseEntity.status(404).body("Barcode not found. Try manual entry.");
            }

            return ResponseEntity.ok(result);
        }
    }




}
