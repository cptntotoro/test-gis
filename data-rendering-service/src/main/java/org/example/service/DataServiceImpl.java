package org.example.service;

import org.example.exception.GeometryMappingException;
import org.example.model.Entity;
import org.example.repository.EntityRepository;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private EntityRepository entityRepository;

    @Override
    public Flux<LineString> getGeoms(Integer width, Integer height, String bboxString) {

        String[] bbox = bboxString.split(",");
        double minX = Double.parseDouble(bbox[0]);
        double minY = Double.parseDouble(bbox[1]);
        double maxX = Double.parseDouble(bbox[2]);
        double maxY = Double.parseDouble(bbox[3]);

        WKBReader wkbReader = new WKBReader();
        return entityRepository.getUserRectangleAreaGeoms(minX, minY, maxX, maxY, 3857).map(hexString -> {
            try {
                return (LineString) wkbReader.read(WKBReader.hexToBytes(hexString.substring(2)));
            } catch (ParseException e) {
                throw new GeometryMappingException(e);
            }
        });
    }

    @Override
    public Flux<Entity> getEntities(Integer width, Integer height, String bboxString) {

        String[] bbox = bboxString.split(",");
        double minX = Double.parseDouble(bbox[0]);
        double minY = Double.parseDouble(bbox[1]);
        double maxX = Double.parseDouble(bbox[2]);
        double maxY = Double.parseDouble(bbox[3]);

        WKBReader wkbReader = new WKBReader();

        // TODO: Request processing failed: org.springframework.data.mapping.MappingException: Could not read property private
        //  org.locationtech.jts.geom.LineString org.example.model.Entity.geom from column geom
        return entityRepository.getUserRectangleAreaEntities(minX, minY, maxX, maxY, 3857);
    }

}
