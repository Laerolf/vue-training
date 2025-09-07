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
    radial_distance DOUBLE NOT NULL
  );

CREATE TABLE
  IF NOT EXISTS location_characteristics (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    characteristic VARCHAR(50) NOT NULL,
    location_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE
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
    last_name VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    email_address VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL
  );

CREATE TABLE
  IF NOT EXISTS voyages (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    departure_date TIMESTAMP NOT NULL,
    arrival_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    route_id VARCHAR(50) NOT NULL,
    space_shuttle_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (route_id) REFERENCES routes (id) ON DELETE CASCADE,
    FOREIGN KEY (space_shuttle_id) REFERENCES space_shuttles (id) ON DELETE CASCADE
  );

 CREATE TABLE
    IF NOT EXISTS bookings (
        id VARCHAR(50) NOT NULL PRIMARY KEY,
        creation_date TIMESTAMP NOT NULL,
        status VARCHAR(50) NOT NULL,
        user_id VARCHAR(50) NOT NULL,
        voyage_id VARCHAR(50) NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
        FOREIGN KEY (voyage_id) REFERENCES voyages (id) ON DELETE CASCADE
    );

 CREATE TABLE
     IF NOT EXISTS passengers (
         id VARCHAR(50) NOT NULL PRIMARY KEY,
         creation_date TIMESTAMP NOT NULL,
         meal_preference VARCHAR(30) NOT NULL,
         package_type VARCHAR(30) NOT NULL,
         booking_id VARCHAR(50) NOT NULL,
         voyage_id VARCHAR(50) NOT NULL,
         FOREIGN KEY (booking_id) REFERENCES bookings (id) ON DELETE CASCADE,
         FOREIGN KEY (voyage_id) REFERENCES voyages (id) ON DELETE CASCADE
     );

 CREATE TABLE
     IF NOT EXISTS passenger_personal_information (
         id VARCHAR(50) NOT NULL PRIMARY KEY,
         last_name VARCHAR(50) NOT NULL,
         middle_name VARCHAR(50) NOT NULL,
         first_name VARCHAR(50) NOT NULL,
         birthdate DATE NOT NULL,
         nationality VARCHAR(50) NOT NULL,
         gender VARCHAR(20) NOT NULL,
         passport_number VARCHAR(50) NOT NULL UNIQUE,
         passenger_id VARCHAR(50) NOT NULL,
         FOREIGN KEY (passenger_id) REFERENCES passengers (id) ON DELETE CASCADE
     );

CREATE TABLE
    IF NOT EXISTS pod_reservations (
        id VARCHAR(50) NOT NULL PRIMARY KEY,
        pod_code VARCHAR(10) NOT NULL,
        creation_date TIMESTAMP NOT NULL,
        passenger_id VARCHAR(50) NOT NULL,
        voyage_id VARCHAR(50) NOT NULL,
        FOREIGN KEY (passenger_id) REFERENCES passengers (id) ON DELETE CASCADE,
        FOREIGN KEY (voyage_id) REFERENCES voyages (id) ON DELETE CASCADE
    );