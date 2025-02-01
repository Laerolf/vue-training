CREATE TABLE IF NOT EXISTS space_shuttle_models (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    max_capacity INT NOT NULL,
    max_speed BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS space_shuttles (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    model_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (model_id) REFERENCES space_shuttle_models(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS locations (
  id VARCHAR(50) NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  radialDistance DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS space_stations (
  id VARCHAR(50) NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  code VARCHAR(10) NOT NULL,
  country VARCHAR(50) NOT NULL,
  location_id VARCHAR(50) NOT NULL,
  FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE
);