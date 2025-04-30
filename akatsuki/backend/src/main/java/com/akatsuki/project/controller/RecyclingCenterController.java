package com.akatsuki.project.controller;

import com.akatsuki.project.model.RecyclingCenter;
import com.akatsuki.project.service.RecyclingCenterService;
import com.akatsuki.project.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/dashboard/recycling-centers")
public class RecyclingCenterController {

    @Autowired
    private RecyclingCenterService recyclingCenterService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/nearby")
    public ResponseEntity<?> findNearbyCenters(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "10") double radiusKm,
            @RequestParam(required = false) String type,
            HttpServletRequest request
    ) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<RecyclingCenter> centers = recyclingCenterService.findNearbyCenters(latitude, longitude, radiusKm, type);
        return ResponseEntity.ok(centers);
    }
}
