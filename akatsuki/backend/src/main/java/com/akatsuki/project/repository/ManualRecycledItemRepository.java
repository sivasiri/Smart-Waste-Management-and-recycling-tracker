package com.akatsuki.project.repository;

import com.akatsuki.project.model.ManualRecycledItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManualRecycledItemRepository extends MongoRepository<ManualRecycledItem, String> {
    List<ManualRecycledItem> findByEmail(String email);
}

