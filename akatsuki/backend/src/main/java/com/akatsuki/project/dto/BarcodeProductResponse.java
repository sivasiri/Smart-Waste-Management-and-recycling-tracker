package com.akatsuki.project.dto;

public class BarcodeProductResponse {
	private String barcode;
    private String productName;
    private String imageUrl;
    private String packaging;
    private String category;
    private String classification;
    
    public BarcodeProductResponse(String classification) {
        this.classification = classification;
    }
	public BarcodeProductResponse() {
		// TODO Auto-generated constructor stub
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	} 

}
