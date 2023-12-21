package org.example.model;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("entity")
public class Entity {
    @Id
    private Long id;
    private Geometry geom;
    private String color;

    public Entity(Long id, Geometry geom, String color) {
        this.id = id;
        this.geom = geom;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public Geometry getGeom() {
        return geom;
    }

    public String getColor() {
        return color;
    }

}



