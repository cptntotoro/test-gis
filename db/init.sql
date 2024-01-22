CREATE EXTENSION postgis;

CREATE TABLE IF NOT EXISTS entity (
    id serial NOT NULL,
    geom geometry(linestring, 3857) NOT NULL,
    color text NOT NULL,
    CONSTRAINT entity_pkey PRIMARY KEY(id)
);

DROP INDEX IF EXISTS entity_geom_index;

-- INSERT INTO entity
-- VALUES (1, 'LINESTRING(3368562.300 8386762.792, 3368683.638691569 8386569.439485572)', '#f89d59');

INSERT INTO entity (id, geom, color)
SELECT
    id id,
    ST_Transform(ST_MakeLine(p, ST_Translate(a.p, random()*0.001 - 0.0005, random()*0.001 - 0.0005)), 3857) geom,
    concat('#', lpad(to_hex((random()*x'FFFFFF'::int8)::int8), 6, '0')) color
FROM (
    SELECT
        id,
        ST_Point(random()*2 + 29, random() + 59.5, 4326) p
    FROM generate_series(1, 1000000, 1) as id
) a
ON CONFLICT (id)
DO UPDATE SET
    geom = EXCLUDED.geom,
    color = EXCLUDED.color;

CREATE INDEX IF NOT EXISTS entity_geom_index ON entity USING GIST (geom);