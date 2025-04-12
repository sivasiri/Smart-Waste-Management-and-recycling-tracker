package com.akatsuki.project.repository;

import com.akatsuki.project.model.RecycledItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecycledItemRepository extends MongoRepository<RecycledItem, String> {
    List<RecycledItem> findByEmail(String email);
    List<RecycledItem> findByEmailAndRecycledAtBetween(String email, LocalDate start, LocalDate end);
}
