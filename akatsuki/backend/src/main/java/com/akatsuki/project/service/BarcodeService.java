package com.akatsuki.project.service;

import com.akatsuki.project.dto.BarcodeProductResponse;
import com.akatsuki.project.model.ManualRecycledItem;
import com.akatsuki.project.model.RecycledItem;
import com.akatsuki.project.repository.ManualRecycledItemRepository;
import com.akatsuki.project.repository.RecycledItemRepository;
import com.akatsuki.project.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class BarcodeService {

    private final RestTemplate restTemplate = new RestTemplate();
    
    @Autowired
    private RecycledItemRepository recycledItemRepository;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private ManualRecycledItemRepository manualRecycledItemRepository;

    public BarcodeProductResponse classifyByBarcode(String barcode) {
        String apiUrl = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";
        String json = restTemplate.getForObject(apiUrl, String.class);

        JSONObject jsonObject = new JSONObject(json);
        if (!jsonObject.has("product")) return null;

        JSONObject product = jsonObject.getJSONObject("product");

        String name = product.optString("product_name", "Unknown Product");
        String image = product.optString("image_url", null);
        String packaging = product.optString("packaging", null);
        String category = product.optString("main_category", null);

        String classification = classifyWaste(packaging, category);

        BarcodeProductResponse response = new BarcodeProductResponse();
        response.setBarcode(barcode);
        response.setProductName(name);
        response.setImageUrl(image);
        response.setPackaging(packaging);
        response.setCategory(category);
        response.setClassification(classification);

        return response;
    }

    private String classifyWaste(String packaging, String category) {
        if (packaging != null && packaging.toLowerCase().contains("plastic")) return "Plastic";
        if (packaging != null && packaging.toLowerCase().contains("metal")) return "Metal";
        if (category != null && category.toLowerCase().contains("fruit")) return "Organic";
        return "Unknown";
    }
    
    public BarcodeProductResponse classifyAndLog(String barcode, String token) {
        String email = jwtUtil.extractEmail(token); // Secure user ID

        // 🧠 Fetch and classify like before
        BarcodeProductResponse response = classifyByBarcode(barcode);
        if (response == null) return null;

        // 🧾 Log to MongoDB
        RecycledItem item = new RecycledItem();
        item.setEmail(email);
        item.setBarcode(barcode);
        item.setProductName(response.getProductName());
        item.setClassification(response.getClassification());
        item.setPackaging(response.getPackaging());
        item.setCategory(response.getCategory());
        item.setImageUrl(response.getImageUrl());
        item.setRecycledAt(LocalDateTime.now());

        try {
            recycledItemRepository.save(item);
            System.out.println("Saved item for " + email);
        } catch (Exception e) {
            System.out.println("Failed to save item: " + e.getMessage());
        }

        return response;
    }
    
    public BarcodeProductResponse classifyManual(String packaging, String category) {
        if (packaging != null && packaging.toLowerCase().contains("plastic")) return new BarcodeProductResponse("Plastic");
        if (packaging != null && packaging.toLowerCase().contains("metal")) return new BarcodeProductResponse("Metal");
        if (category != null && category.toLowerCase().contains("fruit")) return new BarcodeProductResponse("Organic");
        return new BarcodeProductResponse("Unknown");
    }
    
   

    public String addManualEntry(ManualRecycledItem item, String token) {
        String email = jwtUtil.extractEmail(token);
        item.setEmail(email);
        item.setRecycledAt(LocalDateTime.now());

        if (item.getClassification() == null || item.getClassification().isEmpty()) {
            // Optional logic: classify based on packaging
            String autoClassification = classifyWaste(item.getPackaging(), item.getCategory());
            item.setClassification(autoClassification);
        }

        manualRecycledItemRepository.save(item);
        return "Item logged manually!";
    }
    

    public List<ManualRecycledItem> getManualRecycledItems(String email) {
        return manualRecycledItemRepository.findByEmail(email);
    }




}
