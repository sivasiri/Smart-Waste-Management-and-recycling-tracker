package com.akatsuki.project.dto;

import java.time.LocalDateTime;

public class AIImageResponse {
    private String classification;
    private String message;
    private LocalDateTime timestamp;
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

    // Getters and Setters
}

