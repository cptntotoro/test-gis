package org.example.util;

import io.r2dbc.spi.Row;
import org.example.model.Entity;
import org.locationtech.jts.geom.Geometry;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import reactor.util.annotation.NonNull;

@ReadingConverter
public class GeometryReadConverter implements Converter<Row, Entity> {

    @Override
    public Entity convert(@NonNull Row source) {
        Entity entity = new Entity();
        entity.setId(source.get("id", Long.class));
        entity.setColor(source.get("color", String.class));
        entity.setGeom(source.get("geom", Geometry.class));

        return entity;
    }
}
