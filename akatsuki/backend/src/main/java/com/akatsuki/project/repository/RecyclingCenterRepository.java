package com.akatsuki.project.repository;

import com.akatsuki.project.model.RecyclingCenter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Distance;
import java.util.List;

@Repository
public interface RecyclingCenterRepository extends MongoRepository<RecyclingCenter, String> {

    // ✅ Fetch all centers near a given location, within a distance
    List<RecyclingCenter> findByLocationNear(Point location, Distance distance);

    // ✅ (Optional) If you want filtering by type also (plastic, metal, etc)
    // You can add a manual query later if needed
}
