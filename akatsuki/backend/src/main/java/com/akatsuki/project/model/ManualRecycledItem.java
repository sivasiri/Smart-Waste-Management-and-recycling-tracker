package com.akatsuki.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "manual_recycled_items")
public class ManualRecycledItem {

    @Id
    private String id;

    private String email;              // User who logs it
    private String barcode;            // Optional
    private String productName;
    private String packaging;
    private String category;
    private String classification;     // Plastic, Organic, etc.
    private String imageUrl;           // Optional image URL
    private LocalDateTime recycledAt;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getPackaging() { return packaging; }
    public void setPackaging(String packaging) { this.packaging = packaging; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getRecycledAt() { return recycledAt; }
    public void setRecycledAt(LocalDateTime recycledAt) { this.recycledAt = recycledAt; }
}

