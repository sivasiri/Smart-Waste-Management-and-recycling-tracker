package com.akatsuki.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.akatsuki.project.dto.AIImageResponse;
import com.akatsuki.project.dto.BarcodeProductResponse;
import com.akatsuki.project.model.AIClassifiedItem;
import com.akatsuki.project.model.ManualRecycledItem;
import com.akatsuki.project.model.RecycledItem;
import com.akatsuki.project.repository.AIClassifiedItemRepository;
import com.akatsuki.project.repository.RecycledItemRepository;
import com.akatsuki.project.service.AIClassificationService;
import com.akatsuki.project.service.BarcodeService;
import com.akatsuki.project.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/dashboard/barcode")
public class BarcodeController {
	
    @Autowired
    private RecycledItemRepository recycledItemRepository;

    @Autowired
    private BarcodeService barcodeService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AIClassificationService aiClassificationService;
    
    @Autowired
    private AIClassifiedItemRepository aiClassifiedItemRepository;


    @PostMapping("/manual")
    public ResponseEntity<?> logManualItem(@RequestBody ManualRecycledItem item, HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            String result = barcodeService.addManualEntry(item, token);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to log manually: " + e.getMessage());
        }
    }
    

    @GetMapping("/{barcode}")
    public ResponseEntity<?> classifyAndLog(@PathVariable String barcode, HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        BarcodeProductResponse result = barcodeService.classifyAndLog(barcode, token);
        if (result == null) {
            return ResponseEntity.status(404).body("Product not found.");
        }

        return ResponseEntity.ok(result);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/recycled")
    public ResponseEntity<?> getScannedHistory(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(403).body("Invalid or missing token");
        }

        String email = jwtUtil.extractEmail(token);
        List<RecycledItem> items = recycledItemRepository.findByEmail(email);
        return ResponseEntity.ok(items);
    }

	
	@GetMapping("/manual")
	public ResponseEntity<?> getManualRecycledItems(HttpServletRequest request) {
	    String token = jwtUtil.extractTokenFromRequest(request);
	    if (token == null || !jwtUtil.validateToken(token)) {
	        return ResponseEntity.status(401).body("Unauthorized");
	    }

	    String email = jwtUtil.extractEmail(token);
	    List<ManualRecycledItem> items = barcodeService.getManualRecycledItems(email);
	    return ResponseEntity.ok(items);
	}

	
	@PostMapping("/image/aiclassify")
	public ResponseEntity<?> classifyImageWithAI(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
	    String token = jwtUtil.extractTokenFromRequest(request);
	    if (token == null || !jwtUtil.validateToken(token)) {
	        return ResponseEntity.status(401).body("Unauthorized");
	    }

	    try {
	        AIImageResponse result = aiClassificationService.classifyImage(file, token);
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("AI Classification failed: " + e.getMessage());
	    }
	}
	
	@GetMapping("/ai/history")
	public ResponseEntity<?> getAIClassifiedHistory(HttpServletRequest request) {
	    String token = jwtUtil.extractTokenFromRequest(request);
	    if (token == null || !jwtUtil.validateToken(token)) {
	        return ResponseEntity.status(401).body("Unauthorized");
	    }

	    String email = jwtUtil.extractEmail(token);
	    List<AIClassifiedItem> items = aiClassifiedItemRepository.findByEmail(email);
	    return ResponseEntity.ok(items);
	}



}

