package com.akatsuki.project.controller;

import com.akatsuki.project.repository.AlertLogRepository;
import com.akatsuki.project.service.NotificationService;
import com.akatsuki.project.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/dashboard/alerts")
public class AlertController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AlertLogRepository alertLogRepository;

    @PostMapping("/send")
    public ResponseEntity<?> sendGarbageCollectionAlert(HttpServletRequest request, @RequestParam String method) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = jwtUtil.extractEmail(token);
        String message = "♻️ Friendly reminder: Garbage collection is scheduled for today! Please place your bins outside.";

        // Dummy flow
        if ("email".equalsIgnoreCase(method)) {
            notificationService.sendEmail(email, message);
        } else if ("sms".equalsIgnoreCase(method)) {
            notificationService.sendSMS(email, message);
        } else {
            return ResponseEntity.badRequest().body("Invalid method. Use 'email' or 'sms'");
        }

        return ResponseEntity.ok("Alert sent successfully!");
    }
    
    @GetMapping("/logs")
    public ResponseEntity<?> getUserAlertLogs(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = jwtUtil.extractEmail(token);
        return ResponseEntity.ok(alertLogRepository.findByEmail(email));
    }

}

