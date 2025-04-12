package com.akatsuki.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "recycled_items")
public class RecycledItem {

    @Id
    private String id;

    private String email;              // Who recycled it
    private String barcode;
    private String productName;
    private String classification;     // Plastic, Metal, etc.
    private String packaging;
    private String category;
    private String imageUrl;
    private LocalDateTime recycledAt;  // Date/time of recycling
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public LocalDateTime getRecycledAt() {
		return recycledAt;
	}
	public void setRecycledAt(LocalDateTime recycledAt) {
		this.recycledAt = recycledAt;
	}

    // Getters and Setters
}

