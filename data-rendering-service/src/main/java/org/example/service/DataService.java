package org.example.service;

import org.locationtech.jts.geom.Geometry;
import reactor.core.publisher.Flux;

public interface DataService {
    Flux<Geometry> getEntities(Integer width, Integer height, String bbox);
}
