CREATE DATABASE `agenda`;
USE agenda;
CREATE TABLE `personas`
(
  `idPersona` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Email` varchar(20) NOT NULL,
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
CREATE TABLE 'domicilio'
(	
	`idDomicilio` int(11) NOT NULL AUTO_INCREMENT,
	`calle` varchar(45) NOT NULL,
	`altura` varchar(45) NOT NULL,
	`piso` varchar(2),
	`departamento` varchar(5),
	`localidad` varchar(45),
	PRIMARY KEY('idDomicilio')
);