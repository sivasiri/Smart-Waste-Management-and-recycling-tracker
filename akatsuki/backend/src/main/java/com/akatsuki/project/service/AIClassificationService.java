package com.akatsuki.project.service;

import com.akatsuki.project.dto.AIImageResponse;
import com.akatsuki.project.model.AIClassifiedItem;
import com.akatsuki.project.repository.AIClassifiedItemRepository;
import com.akatsuki.project.util.JwtUtil;
import com.akatsuki.project.util.MultipartInputStreamFileResource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class AIClassificationService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AIClassifiedItemRepository aiClassifiedItemRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public AIImageResponse classifyImage(MultipartFile file, String token) throws Exception {
        String email = jwtUtil.extractEmail(token);
        byte[] imageBytes = file.getBytes();

        // Create the multipart body for Flask
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:5000/classify",
                requestEntity,
                String.class
        );

        String classification = new JSONObject(response.getBody()).getString("classification");

        // Save result in MongoDB
        AIClassifiedItem item = new AIClassifiedItem();
        item.setEmail(email);
        item.setImage(imageBytes);
        item.setClassification(classification);
        item.setTimestamp(LocalDateTime.now());
        aiClassifiedItemRepository.save(item);

        AIImageResponse result = new AIImageResponse();
        result.setClassification(classification);
        result.setMessage("♻️ Thank you for recycling! Your item is classified as " + classification + ".");
        result.setTimestamp(LocalDateTime.now()); 
        return result;
    }
}
