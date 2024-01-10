package org.example.repository;

import org.example.model.Entity;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EntityRepository extends R2dbcRepository<Entity, Long> {

    @Query(value = "SELECT  ST_AsBinary(geom) FROM entity WHERE ST_Intersects(geom, ST_MakeEnvelope(:minx, :miny, :maxx, :maxy, :srid))")
    Flux<String> getUserRectangleAreaEntities(double minx, double miny, double maxx, double maxy, int srid);

    @Query(value = "SELECT ST_AsText(geom) FROM entity")
    Flux<String> getAl();

}
