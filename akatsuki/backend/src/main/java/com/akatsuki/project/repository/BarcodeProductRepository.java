package com.akatsuki.project.repository;

import com.akatsuki.project.model.BarcodeProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarcodeProductRepository extends MongoRepository<BarcodeProduct, String> {
    BarcodeProduct findByCode(String code);
}

