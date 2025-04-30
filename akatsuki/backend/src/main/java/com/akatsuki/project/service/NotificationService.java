package com.akatsuki.project.service;

import com.akatsuki.project.model.AlertLog;
import com.akatsuki.project.repository.AlertLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    private AlertLogRepository alertLogRepository;

    public void sendEmail(String email, String message) {
        System.out.println("📧 Simulated Email to " + email + ": " + message);

        AlertLog log = new AlertLog();
        log.setEmail(email);
        log.setMessage(message);
        log.setType("email");
        log.setSentAt(LocalDateTime.now());
        alertLogRepository.save(log);
    }

    public void sendSMS(String email, String message) {
        System.out.println("📱 Simulated SMS to " + email + ": " + message);

        AlertLog log = new AlertLog();
        log.setEmail(email);
        log.setMessage(message);
        log.setType("sms");
        log.setSentAt(LocalDateTime.now());
        alertLogRepository.save(log);
    }
}


