package org.example.controller;

import org.example.exception.IncorrectRequestException;
import org.example.model.Entity;
import org.example.service.DataService;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping
    @RequestMapping(path = "/render")
    @ResponseBody
    public String getData(@RequestParam(required = false) Integer width,
                          @RequestParam(required = false) Integer height,
                          @RequestParam String bboxString) {

        if (bboxString == null || bboxString.isEmpty()) {
            throw new IncorrectRequestException("bboxString parameter must not be empty.");
        }

        return convertToGeoJson(new GeometryCollection(GeometryFactory.toGeometryArray(
                    StreamSupport.stream(dataService.getGeoms(width, height, bboxString).toIterable().spliterator(), false).collect(Collectors.toList())),
                    new GeometryFactory()));
    }

    private static String convertToGeoJson(Geometry geometry) {
        GeometryJSON geometryJson = new GeometryJSON();
        String msg;
        try (StringWriter writer = new StringWriter();) {
            geometryJson.write(geometry, writer);
            msg = writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("convertToGeoJson exception", e);
        }
        return msg;
    }

    @GetMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping
    @RequestMapping(path = "/render2")
    @ResponseBody
    public List<Entity> getData2(@RequestParam(required = false) Integer width,
                                 @RequestParam(required = false) Integer height,
                                 @RequestParam String bboxString) {
        return StreamSupport.stream(dataService.getEntities(width, height, bboxString).toIterable().spliterator(), false).collect(Collectors.toList());
    }
}
