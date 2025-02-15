CREATE TABLE
  IF NOT EXISTS space_shuttle_models (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    max_capacity INT NOT NULL,
    max_speed BIGINT NOT NULL
  );

CREATE TABLE
  IF NOT EXISTS space_shuttles (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    model_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (model_id) REFERENCES space_shuttle_models (id) ON DELETE CASCADE
  );

CREATE TABLE
  IF NOT EXISTS locations (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    radialDistance DOUBLE NOT NULL
  );

CREATE TABLE
  IF NOT EXISTS space_stations (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(10) NOT NULL,
    country VARCHAR(50) NOT NULL,
    location_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE
  );

CREATE TABLE
  IF NOT EXISTS routes (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    origin_id VARCHAR(50) NOT NULL,
    destination_id VARCHAR(50) NOT NULL,
    space_shuttle_model_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (origin_id) REFERENCES space_stations (id) ON DELETE CASCADE,
    FOREIGN KEY (destination_id) REFERENCES space_stations (id) ON DELETE CASCADE,
    FOREIGN KEY (space_shuttle_model_id) REFERENCES space_shuttle_models (id) ON DELETE CASCADE
  );

CREATE TABLE
  IF NOT EXISTS users (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    lastName VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    emailAddress VARCHAR(100) NOT NULL,
    password VARCHAR(50) NOT NULL
  );