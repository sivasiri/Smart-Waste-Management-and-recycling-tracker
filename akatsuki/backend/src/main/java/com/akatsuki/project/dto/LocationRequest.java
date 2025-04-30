package com.akatsuki.project.dto;

public class LocationRequest {
    private double latitude;
    private double longitude;
    private String materialType; // Plastic, Metal, Organic, etc.

    // === Getters and Setters ===
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getMaterialType() {
        return materialType;
    }
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}
