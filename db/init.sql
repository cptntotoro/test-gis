CREATE EXTENSION postgis;

CREATE TABLE IF NOT EXISTS entity
(
    id serial NOT NULL, -- GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    geom geometry(linestring, 4326) NOT NULL, -- 3857
    color text NOT NULL,
    CONSTRAINT entity_pkey PRIMARY KEY(id)
);

INSERT INTO entity
VALUES (1, 'LINESTRING(-122.33 47.606, 0.0 51.5)', '#f89d59'),
       (2, 'LINESTRING(-122.33 47.606, 0.0 51.5)', '#f89d59'),
       (3, 'LINESTRING(-122.33 47.606, 0.0 51.5)', '#f89d59');