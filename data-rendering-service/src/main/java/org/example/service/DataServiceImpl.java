package org.example.service;

import org.example.exception.GeometryMappingException;
import org.example.repository.EntityRepository;
import org.locationtech.jts.geom.Geometry;
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
    public Flux<Geometry> getEntities(Integer width, Integer height, String bboxString) {

        String[] bbox = bboxString.split(",");
        double minx = Double.parseDouble(bbox[0]);
        double miny = Double.parseDouble(bbox[1]);
        double maxx = Double.parseDouble(bbox[2]);
        double maxy = Double.parseDouble(bbox[3]);

        WKBReader wkbReader = new WKBReader();
        return entityRepository.getUserRectangleAreaEntities(minx, miny, maxx, maxy, 4326).map(hexString -> {
            try {
                Geometry geometry = wkbReader.read(WKBReader.hexToBytes(hexString.substring(2)));
                return geometry;
            } catch (ParseException e) {
                throw new GeometryMappingException(e);
            }
        });
    }

    @Override
    public Flux<String> findAll() {
        return entityRepository.getAl();
    }
}
