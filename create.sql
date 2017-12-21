DROP TABLE IF EXISTS USER ;
CREATE TABLE USER (
  email CHAR(50),
  password CHAR(50),
  lastname CHAR(50),
  firstname CHAR(50),
  PRIMARY KEY (email)
);

DROP TABLE IF EXISTS RIDE;
CREATE TABLE RIDE(
  idRide INT,
  idOrganiser VARCHAR(50),
  departurePlace VARCHAR(50) ,
  departureDate VARCHAR(50),
  departureHour VARCHAR(50),
  arrivalPlace VARCHAR(50),
  distance VARCHAR(50),
  duration VARCHAR(50),
  positions BYTEA,
  waypoints BYTEA,
  autorisedEmails BYTEA,
  state VARCHAR(50),
  PRIMARY KEY (idRide),
  FOREIGN KEY (idOrganiser) REFERENCES USER(email)
);

DROP TABLE IF EXISTS STATISTICS;
CREATE TABLE STATISTICS (
  idRide INT,
  idUser VARCHAR(50),
  timedPositions BYTEA,
  PRIMARY KEY (idRide, idUser),
  FOREIGN KEY (idRide) REFERENCES RIDE(idRide),
  FOREIGN KEY (idUser) REFERENCES USER(email)
);


INSERT INTO USER VALUES ('jd@gmail.com', 'mdplol', 'jean', 'dupont');
INSERT INTO USER VALUES ('fd@gmail.com', 'mdplol2', 'francois', 'durand');
-- INSERT INTO RIDE (idRide, idOrganiser, departurePlace, departureDate, departureHour, arrivalPlace, distance, duration)
-- VALUES (0, 'fd@gmail.com', 'Toulon, France', '12 mai', '10h30', 'La Seyne',
--         '10km', '1h25min');