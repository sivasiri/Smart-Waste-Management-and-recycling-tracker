package com.akatsuki.project.repository;

import com.akatsuki.project.model.GarbageCollectionSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GarbageCollectionScheduleRepository extends MongoRepository<GarbageCollectionSchedule, String> {
    List<GarbageCollectionSchedule> findByStatusAndScheduledDateTimeBefore(String status, LocalDateTime dateTime);
    List<GarbageCollectionSchedule> findByEmail(String email);
}
