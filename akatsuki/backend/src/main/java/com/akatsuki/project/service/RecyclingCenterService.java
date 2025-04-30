package com.akatsuki.project.service;

import com.akatsuki.project.model.RecyclingCenter;
import com.akatsuki.project.repository.RecyclingCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecyclingCenterService {

    @Autowired
    private RecyclingCenterRepository recyclingCenterRepository;

    public List<RecyclingCenter> findNearbyCenters(double latitude, double longitude, double radiusInKm, String type) {
        Point location = new Point(longitude, latitude); // 💡 Order matters: (longitude, latitude)
        Distance distance = new Distance(radiusInKm, Metrics.KILOMETERS);

        List<RecyclingCenter> nearbyCenters = recyclingCenterRepository.findByLocationNear(location, distance);

        // 💡 Now filter by type (plastic, metal, organic) if provided
        if (type != null && !type.isEmpty()) {
            nearbyCenters = nearbyCenters.stream()
                    .filter(center -> center.getType() != null && center.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }

        return nearbyCenters;
    }
}

