package org.example.service;

import org.example.model.Entity;
import org.locationtech.jts.geom.LineString;
import reactor.core.publisher.Flux;

public interface DataService {
    Flux<LineString> getGeoms(Integer width, Integer height, String bbox);
    Flux<Entity> getEntities(Integer width, Integer height, String bbox);
}
