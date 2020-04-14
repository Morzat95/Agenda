--CREATE DATABASE IF NOT EXISTS `grupo_G14`;
--USE grupo_G14;

--DROP TABLE IF EXISTS `tipos_de_contacto`;
CREATE TABLE IF NOT EXISTS `tipos_de_contacto`
(
  `idTipoContacto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idTipoContacto`)
);

--DROP TABLE IF EXISTS `personas`;
CREATE TABLE IF NOT EXISTS `personas`
--CREATE TABLE `personas`
(
  `idPersona` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `FechaCumpleaños` DATE NOT NULL,
  `idTipoDeContacto` int(11) NOT NULL,
  `Favorito` boolean NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`idPersona`),
  FOREIGN KEY (`idTipoDeContacto`) REFERENCES `tipos_de_contacto` (`idTipoContacto`)
);

--DROP TABLE IF EXISTS `domicilio`;
CREATE TABLE IF NOT EXISTS `domicilio`
(
  `idDomicilio` int(11) NOT NULL AUTO_INCREMENT,
  `calle` varchar(45) NOT NULL,
  `altura` varchar(5) NOT NULL,
  `piso` varchar(45) NOT NULL,
  `departamento` varchar(45) NOT NULL,
  `idLocalidad` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  PRIMARY KEY (`idDomicilio`),
  FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`) ON DELETE CASCADE
);

--DROP TABLE IF EXISTS `países`;
CREATE TABLE IF NOT EXISTS `países`
(
  `idPaís` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idPaís`)
);

--DROP TABLE IF EXISTS `provincias`;
CREATE TABLE IF NOT EXISTS `provincias`
(
  `idProvincia` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(70) NOT NULL,
  `idPaís` int(11) NOT NULL,
  PRIMARY KEY (`idProvincia`),
  FOREIGN KEY (`idPaís`) REFERENCES `países` (`idPaís`) ON DELETE CASCADE
);

--DROP TABLE IF EXISTS `localidades`;
CREATE TABLE IF NOT EXISTS `localidades`
(
  `idLocalidad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(70) NOT NULL,
  `idProvincia` int(11) NOT NULL,
  PRIMARY KEY (`idLocalidad`),
  FOREIGN KEY (`idProvincia`) REFERENCES `provincias` (`idProvincia`) ON DELETE CASCADE
);