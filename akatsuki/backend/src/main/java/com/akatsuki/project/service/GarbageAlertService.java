package com.akatsuki.project.service;

import com.akatsuki.project.model.GarbageCollectionSchedule;
import com.akatsuki.project.repository.GarbageCollectionScheduleRepository;
import com.akatsuki.project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GarbageAlertService {

    @Autowired
    private GarbageCollectionScheduleRepository repository;

    public String scheduleCollection(GarbageCollectionSchedule schedule, String token, JwtUtil jwtUtil) {
        String email = jwtUtil.extractEmail(token);
        schedule.setEmail(email);
        schedule.setStatus("PENDING");
        repository.save(schedule);
        return "Garbage collection alert scheduled successfully.";
    }

    public List<GarbageCollectionSchedule> getUserSchedules(String token, JwtUtil jwtUtil) {
        String email = jwtUtil.extractEmail(token);
        return repository.findByEmail(email);
    }

    // This job runs every minute
    @Scheduled(fixedRate = 60000)
    public void sendAlerts() {
        List<GarbageCollectionSchedule> dueAlerts =
                repository.findByStatusAndScheduledDateTimeBefore("PENDING", LocalDateTime.now());

        for (GarbageCollectionSchedule schedule : dueAlerts) {
            // You can replace this with actual Twilio/Email logic
            System.out.println("Sending " + schedule.getAlertType() + " alert to " + schedule.getEmail());

            // Mark as SENT
            schedule.setStatus("SENT");
            repository.save(schedule);
        }
    }
}

