package org.example.service;

import org.example.model.Entity;
import org.example.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private EntityRepository entityRepository;

    @Override
    public Flux<Entity> getEntities(double minX, double minY, double maxX, double maxY) {
        return entityRepository.getUserRectangleAreaEntities(minX, minY, maxX, maxY, 3857);
    }

}
