package com.akatsuki.project.repository;

import com.akatsuki.project.model.AlertLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertLogRepository extends MongoRepository<AlertLog, String> {
    List<AlertLog> findByEmail(String email);
}
