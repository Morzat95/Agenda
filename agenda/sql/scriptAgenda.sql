CREATE DATABASE `grupo_G14`;
USE grupo_G14;
CREATE TABLE `personas`
(
  `idPersona` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Email` varchar(20) NOT NULL,
  `FechaCumpleaños` DATE NOT NULL,
  `idTipoDeContacto` int(11) NOT NULL,
  `idDomicilio` int(11) NOT NULL,
  PRIMARY KEY (`idPersona`)
);
CREATE TABLE `localidades`
(
  `idLocalidad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idLocalidad`)
);
CREATE TABLE `tipos_de_contacto`
(
  `idTipoContacto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idTipoContacto`)
);
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