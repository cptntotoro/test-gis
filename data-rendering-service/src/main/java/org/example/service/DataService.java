package org.example.service;

import org.example.model.Entity;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import reactor.core.publisher.Flux;

public interface DataService {
    Flux<Geometry> getEntities(Integer width, Integer height, String bbox);
    Flux<String> findAll();
}
