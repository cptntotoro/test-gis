//instead of imports
let OlMap = ol.Map;
let OSM = ol.source.OSM;
let TileLayer = ol.layer.Tile;
let VectorLayer = ol.layer.Vector;
let View = ol.View;
let GeoJSON = ol.format.GeoJSON;
let VectorSource = ol.source.Vector;

const source = new VectorSource();
let vectorLayer = new VectorLayer({
    source: source,
    projection: 'EPSG:3857',
});

const rasterLayer = new TileLayer({
    source: new OSM(),
});

const map = new OlMap({
    layers: [rasterLayer, vectorLayer],
    target: 'map',
    view: new View({
        center: ol.proj.transform([30.260864, 59.941240], 'EPSG:4326', 'EPSG:3857'),
        zoom: 17,
    }),
});

document.getElementById('zoom-out').onclick = function () {
    const view = map.getView();
    const zoom = view.getZoom();
    view.setZoom(zoom - 1);
};

document.getElementById('zoom-in').onclick = function () {
    const view = map.getView();
    const zoom = view.getZoom();
    view.setZoom(zoom + 1);
};

map.once('postrender', function () {
    const url = 'http://localhost:6060/data-rendering/render?';

    fetch(url + new URLSearchParams({
        width: map.getSize()[0],
        height: map.getSize()[1],
        bboxString: map.getView().calculateExtent(map.getSize()).toString()
    }),
        {
            method: 'GET',
        })
        .then(function (response) {
            return response.json();
        })
        .then(json => {
                const format = new GeoJSON();
                const features = format.readFeatures(json, {
                    featureProjection: 'EPSG:4326',
                });

                for (let i = 0; i < features.length; i++) {
                    const feature = features[i];
                    feature.setStyle(new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: '#4B6891',
                            width: 5 / map.getView().getResolution()
                        })
                    }));
                }
                source.addFeatures(features);
            }
        )
})
