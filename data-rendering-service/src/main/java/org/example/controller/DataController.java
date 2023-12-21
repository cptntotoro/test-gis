package org.example.controller;

import org.example.exception.IncorrectRequestException;
import org.example.model.Entity;
import org.example.service.DataService;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping
    @RequestMapping("/render")
    public ResponseEntity<Flux<String>> getData(@RequestParam(required = false) Integer width,
                                                    @RequestParam(required = false) Integer height,
                                                    @RequestParam String bboxString) {

        if (bboxString == null || bboxString.isEmpty()) {
            throw new IncorrectRequestException("bboxString parameter must not be empty.");
        }

        return ResponseEntity.ok().body(dataService.getEntities(width, height, bboxString).map(Geometry::toString));
    }

    @GetMapping
    @RequestMapping("/all")
    public Flux<String> getAll() {
        return dataService.findAll();
    }
}
