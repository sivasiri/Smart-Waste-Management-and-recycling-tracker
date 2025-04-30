package com.akatsuki.project.controller;

import com.akatsuki.project.model.GarbageCollectionSchedule;
import com.akatsuki.project.service.GarbageAlertService;
import com.akatsuki.project.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "http://localhost:3000")
public class GarbageAlertController {

    @Autowired
    private GarbageAlertService garbageAlertService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleCollection(@RequestBody GarbageCollectionSchedule schedule, HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String result = garbageAlertService.scheduleCollection(schedule, token, jwtUtil);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my-schedules")
    public ResponseEntity<?> getMySchedules(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<GarbageCollectionSchedule> schedules = garbageAlertService.getUserSchedules(token, jwtUtil);
        return ResponseEntity.ok(schedules);
    }
}
