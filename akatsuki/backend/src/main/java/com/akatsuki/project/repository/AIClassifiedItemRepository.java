package com.akatsuki.project.repository;

import java.util.List;

import com.akatsuki.project.model.AIClassifiedItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIClassifiedItemRepository extends MongoRepository<AIClassifiedItem, String> {
    List<AIClassifiedItem> findByEmail(String email);
}
