CREATE DATABASE IF NOT EXISTS `grupo_G14`;
USE grupo_G14;
DROP TABLE IF EXISTS `personas`;
CREATE TABLE `personas`
(
  `idPersona` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Email` varchar(20) NOT NULL,
  `FechaCumpleaños` DATE NOT NULL,
  `idTipoDeContacto` int(11) NOT NULL,
  `idDomicilio` int(11) NOT NULL,
  `Favorito` boolean NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`idPersona`)
);
DROP TABLE IF EXISTS `localidades`;
CREATE TABLE `localidades`
(
  `idLocalidad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(70) NOT NULL,
  `idProvincia` int(11) NOT NULL,
  PRIMARY KEY (`idLocalidad`)
);
DROP TABLE IF EXISTS `tipos_de_contacto`;
CREATE TABLE `tipos_de_contacto`
(
  `idTipoContacto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idTipoContacto`)
);
DROP TABLE IF EXISTS `domicilio`;
CREATE TABLE `domicilio`
(
  `idDomicilio` int(11) NOT NULL AUTO_INCREMENT,
  `calle` varchar(45) NOT NULL,
  `altura` varchar(45) NOT NULL,
  `piso` varchar(45) NOT NULL,
  `departamento` varchar(45) NOT NULL,
  `idLocalidad` int(11) NOT NULL,
  PRIMARY KEY (`idDomicilio`)
);
DROP TABLE IF EXISTS `países`;
CREATE TABLE `países`
(
  `idPaís` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idPaís`)
);
DROP TABLE IF EXISTS `provincias`;
CREATE TABLE `provincias`
(
  `idProvincia` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(70) NOT NULL,
  `idPaís` int(11) NOT NULL,
  PRIMARY KEY (`idProvincia`)
);