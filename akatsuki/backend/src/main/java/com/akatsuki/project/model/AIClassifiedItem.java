package com.akatsuki.project.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ai_classified_items")
public class AIClassifiedItem {

    @Id
    private String id;
    private String email;
    
    private byte[] image;
    private String classification;
    private LocalDateTime timestamp;

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
	public void setModelUsed(String string) {
		// TODO Auto-generated method stub
		
	}
}
