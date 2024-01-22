package org.example.controller;

import org.example.model.Entity;
import org.example.service.DataService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.util.AffineTransformation;
import org.locationtech.jts.geom.util.AffineTransformationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping
    @RequestMapping(path = "/render")
    @ResponseBody
    public ResponseEntity<byte[]> getData(@RequestParam(required = false) Integer width,
                                          @RequestParam(required = false) Integer height,
                                          @RequestParam String bboxString) {

        String[] bbox = bboxString.split(",");
        double minX = Double.parseDouble(bbox[0]);
        double minY = Double.parseDouble(bbox[1]);
        double maxX = Double.parseDouble(bbox[2]);
        double maxY = Double.parseDouble(bbox[3]);

        AffineTransformation at = AffineTransformationFactory.createFromControlVectors(
                new Coordinate(minX,minY),
                new Coordinate(maxX,maxY),
                new Coordinate(maxX,minY),
                new Coordinate(0,0),
                new Coordinate(width,height),
                new Coordinate(width,0)
        );

        BufferedImage bImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bImg.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, width, height);
        g2d.setComposite(AlphaComposite.Src);
        g2d.setStroke(new BasicStroke(4f));

        List<Entity> entityList = StreamSupport.stream(dataService.getEntities(minX, minY, maxX, maxY)
                .toIterable().spliterator(), false).toList();

        entityList.forEach(entity -> {
            g2d.setColor(Color.decode(entity.getColor()));
            Coordinate[] coordinates = at.transform(entity.getGeom()).getCoordinates();
            g2d.draw(new Line2D.Double(coordinates[0].x, coordinates[0].y, coordinates[1].x, coordinates[1].y));
        });

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bImg , "png", byteArrayOutputStream);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
