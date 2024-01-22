package org.example.service;

import org.example.model.Entity;
import reactor.core.publisher.Flux;

public interface DataService {

    Flux<Entity> getEntities(double minX, double minY, double maxX, double maxY);
}
