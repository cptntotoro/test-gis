package org.example.repository;

import org.example.model.Entity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EntityRepository extends R2dbcRepository<Entity, Long> {

    @Query(value = "SELECT id, geom, color FROM entity WHERE ST_Intersects(geom, ST_MakeEnvelope(:minX, :minY, :maxX, :maxY, :srid))")
    Flux<Entity> getUserRectangleAreaEntities(double minX, double minY, double maxX, double maxY, int srid);
}
