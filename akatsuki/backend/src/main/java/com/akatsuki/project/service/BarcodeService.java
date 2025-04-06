package com.akatsuki.project.service;

import com.akatsuki.project.dto.BarcodeProductResponse;
import com.akatsuki.project.model.BarcodeProduct;
import com.akatsuki.project.repository.BarcodeProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarcodeService {

    @Autowired
    private BarcodeProductRepository barcodeProductRepository;

    public BarcodeProductResponse classifyByBarcode(String barcode) {
        BarcodeProduct product = barcodeProductRepository.findByCode(barcode);

        if (product == null) return null;

        // Extract fields
        String name = product.getProduct_name();
        String image = product.getImage_url();
        String packaging = product.getPackaging();
        String category = product.getCategories();

        // Custom logic to determine classification
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
        if (category != null && category.toLowerCase().contains("fruit")) return "Organic";
        if (packaging != null && packaging.toLowerCase().contains("metal")) return "Metal";
        return "Unknown";
    }
}
