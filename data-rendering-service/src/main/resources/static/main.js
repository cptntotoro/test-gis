//instead of imports
let OlMap = ol.Map;
let OSM = ol.source.OSM;
let TileLayer = ol.layer.Tile;
let ImageLayer = ol.layer.Image;
let View = ol.View;
let ImageStatic = ol.source.ImageStatic;

const rasterLayer = new TileLayer({
    source: new OSM(),
});

const map = new OlMap({
    layers: [rasterLayer],
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

let renderAwait = false;
let timeout = 3000;
map.on('postrender', function () {
    if(!renderAwait) {
        setTimeout(() => renderLines(), timeout);
        renderAwait = true;
    }
})

function renderLines() {
    const url = 'http://localhost:6060/data-rendering/render?';
    const width = map.getSize()[0];
    const height =map.getSize()[1];
    const imageExtent = map.getView().calculateExtent(map.getSize());
    const projection = map.getView().getProjection();
    const imageLayer = new ImageLayer({
        source: new ImageStatic({
            imageExtent : imageExtent,
            size : [width, height],
            projection : projection,
            url : url + new URLSearchParams({
                width: width,
                height: height,
                bboxString: imageExtent.toString()
            })
        })
    });
    map.addLayer(imageLayer);
    renderAwait = false;
}
