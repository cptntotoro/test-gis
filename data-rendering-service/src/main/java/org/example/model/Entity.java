package org.example.model;

import org.locationtech.jts.geom.LineString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("entity")
public class Entity {
    @Id
    private Long id;
    private LineString geom;
    private String color;
    public Entity(Long id, LineString geom, String color) {
        this.id = id;
        this.geom = geom;
        this.color = color;
    }

}



