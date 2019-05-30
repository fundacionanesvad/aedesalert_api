/*
SQLyog Community v12.09 (64 bit)
MySQL - 5.5.62-0ubuntu0.14.04.1 : Database - aedesalert_taller
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`aedesalert_taller` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*USE `aedesalert_taller`;*/

/*Table structure for table `Alerts` */

DROP TABLE IF EXISTS `Alerts`;

CREATE TABLE `Alerts` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id de la alerta',
  `date` datetime NOT NULL COMMENT 'Fecha de creación de la alerta',
  `link` varchar(250) CHARACTER SET utf8 NOT NULL COMMENT 'Enlace relacionado con la alerta',
  `closed` bit(1) NOT NULL COMMENT 'Indica si se ha leido la alerta',
  `typeId` int(11) NOT NULL COMMENT 'Tipo de la alerta',
  `userId` int(11) NOT NULL COMMENT 'Id del usuario al que va digida la alerta',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Alerts_id` (`id`),
  KEY `typeId` (`typeId`),
  KEY `userId` (`userId`),
  CONSTRAINT `FK_Alerts_TableElements` FOREIGN KEY (`typeId`) REFERENCES `TableElements` (`id`),
  CONSTRAINT `FK_Alerts_Users` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84103 DEFAULT CHARSET=latin1;

/*Table structure for table `AreaDescendants` */

DROP TABLE IF EXISTS `AreaDescendants`;

CREATE TABLE `AreaDescendants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `areaId` int(11) NOT NULL COMMENT 'Id de la zona a la que se refiere',
  `descendantId` int(11) NOT NULL COMMENT 'Zona descendiente ',
  `level` int(11) NOT NULL COMMENT 'Nivel relativo a la zona de la que se refiere',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_AreaDescendants_id` (`id`),
  KEY `areaId` (`areaId`),
  KEY `descendantId` (`descendantId`),
  CONSTRAINT `FK_AreaDescendants_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_AreaDescendants_Descendants` FOREIGN KEY (`descendantId`) REFERENCES `Areas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=87890 DEFAULT CHARSET=latin1;

/*Table structure for table `Areas` */

DROP TABLE IF EXISTS `Areas`;

CREATE TABLE `Areas` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador',
  `code` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT 'Código de la zona',
  `name` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT 'Nombre de la zona',
  `houses` int(11) DEFAULT NULL COMMENT 'Número de casas que componen la zona',
  `coords` varchar(5700) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Coordenas de la zona (en formato JSON)',
  `leaf` bit(1) NOT NULL COMMENT 'Indica si este area es una hoja, es decir si no tiene hijos',
  `parentId` int(11) DEFAULT NULL COMMENT 'Id de la zona a la que pertenece',
  `typeId` int(11) NOT NULL COMMENT 'Tipo de Area: 1:País (Nodo Padre), 2:Región, 3: Red, 4: Microrred, 5: EESS, 6: Sector, 7: Manzana',
  `latitude` decimal(9,6) DEFAULT NULL COMMENT 'Latitud del centro',
  `longitude` decimal(9,6) DEFAULT NULL COMMENT 'Longitud del centro',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Areas_id` (`id`),
  KEY `parentId` (`parentId`),
  KEY `typeId` (`typeId`),
  CONSTRAINT `FK_Areas_Parents` FOREIGN KEY (`parentId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Areas_TableElements` FOREIGN KEY (`typeId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12726 DEFAULT CHARSET=latin1 COMMENT='Zonas';

/*Table structure for table `Febriles` */

DROP TABLE IF EXISTS `Febriles`;

CREATE TABLE `Febriles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `areaId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `areaId` (`areaId`),
  CONSTRAINT `FK_Febriles_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `Houses` */

DROP TABLE IF EXISTS `Houses`;

CREATE TABLE `Houses` (
  `uuid` binary(16) NOT NULL COMMENT 'Id de la vivienda',
  `number` int(11) NOT NULL COMMENT 'Número de la vivienda',
  `code` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT 'Código de la vivienda',
  `qrcode` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Código QR',
  `latitude` decimal(9,6) DEFAULT NULL COMMENT 'Latitude',
  `longitude` decimal(9,6) DEFAULT NULL COMMENT 'Longitude',
  `streetName` varchar(250) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Calle',
  `streetNumber` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Nvarchar para dar cobertura a casos tipo ''10 A'', ''10 B'' o ''26 BIS''',
  `personsNumber` int(10) NOT NULL DEFAULT '0' COMMENT 'Número de habitantes',
  `areaId` int(11) NOT NULL COMMENT 'Id de la manzana',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UQ_Houses_uuid` (`uuid`),
  KEY `areaId` (`areaId`),
  CONSTRAINT `FK_Houses_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Viviendas';

/*Table structure for table `Inspections` */

DROP TABLE IF EXISTS `Inspections`;

CREATE TABLE `Inspections` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador',
  `startDate` date DEFAULT NULL COMMENT 'Fecha de inicio',
  `finishDate` date DEFAULT NULL COMMENT 'Fecha fin',
  `inspectionSize` int(11) NOT NULL COMMENT 'Tamaño de la muestra (10% de viviendas o total)',
  `coverage` decimal(4,2) DEFAULT NULL COMMENT 'Número de viviendas inspeccionadas / número de viviendas programadas',
  `stateId` int(11) NOT NULL COMMENT 'Estado de la inspección',
  `typeId` int(11) NOT NULL COMMENT 'Tipo de plan (Vigilancia/Control/Evaluación)',
  `areaId` int(11) NOT NULL COMMENT 'Id de la zona',
  `scheduleId` int(11) DEFAULT NULL,
  `larvicideId` int(11) NOT NULL,
  `trapLatitude` decimal(9,6) DEFAULT NULL COMMENT 'Latitud de la ovitrampa',
  `trapLongitude` decimal(9,6) DEFAULT NULL COMMENT 'Longitud de la ovitrampa',
  `trapId` int(11) DEFAULT NULL,
  `trapDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Inspections_id` (`id`),
  KEY `areaId` (`areaId`),
  KEY `typeId` (`typeId`),
  KEY `stateId` (`stateId`),
  KEY `scheduleId` (`scheduleId`),
  KEY `larvicideId` (`larvicideId`),
  KEY `trapId` (`trapId`),
  CONSTRAINT `FK_Inspections_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Inspections_Schedules` FOREIGN KEY (`scheduleId`) REFERENCES `Schedules` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Inspections_States` FOREIGN KEY (`stateId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Inspections_Traps` FOREIGN KEY (`trapId`) REFERENCES `Traps` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Inspections_Types` FOREIGN KEY (`typeId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Inspection_larvicide` FOREIGN KEY (`larvicideId`) REFERENCES `Larvicides` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=752 DEFAULT CHARSET=latin1 COMMENT='Inspecciones';

/*Table structure for table `Inventories` */

DROP TABLE IF EXISTS `Inventories`;

CREATE TABLE `Inventories` (
  `uuid` binary(16) NOT NULL COMMENT 'Id del inventario',
  `inspected` int(4) NOT NULL COMMENT 'Inspeccionado',
  `focus` int(4) NOT NULL COMMENT 'Foco',
  `treated` int(4) NOT NULL COMMENT 'Tratado',
  `packet` int(4) NOT NULL COMMENT 'Moños',
  `destroyed` int(4) NOT NULL COMMENT 'Destruido',
  `containerId` int(11) NOT NULL COMMENT 'Id del contenedor',
  `visitUuid` binary(16) NOT NULL COMMENT 'Id de la visita',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UQ_Inventories_uuid` (`uuid`),
  KEY `visitUuid` (`visitUuid`),
  KEY `containerId` (`containerId`),
  CONSTRAINT `FK_Inventories_Containers` FOREIGN KEY (`containerId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Inventories_Visits` FOREIGN KEY (`visitUuid`) REFERENCES `Visits` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Inventarios';

/*Table structure for table `InventorySummaries` */

DROP TABLE IF EXISTS `InventorySummaries`;

CREATE TABLE `InventorySummaries` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `inspected` int(10) NOT NULL,
  `focus` int(10) NOT NULL,
  `treated` int(10) NOT NULL,
  `destroyed` int(10) NOT NULL,
  `containerId` int(10) NOT NULL,
  `planId` int(10) NOT NULL,
  `areaId` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `planId` (`planId`),
  KEY `areaId` (`areaId`),
  KEY `containerId` (`containerId`),
  CONSTRAINT `InventorySummaries_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `InventorySummaries_Plans` FOREIGN KEY (`planId`) REFERENCES `Plans` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `InventorySummaries_TableElements` FOREIGN KEY (`containerId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=193348 DEFAULT CHARSET=latin1;

/*Table structure for table `Labels` */

DROP TABLE IF EXISTS `Labels`;

CREATE TABLE `Labels` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del label',
  `value` varchar(250) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Valor',
  `tableElementId` int(11) NOT NULL COMMENT 'Id de la tabla de elementos',
  `languageId` int(11) NOT NULL COMMENT 'Id del idioma',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Labels_id` (`id`),
  KEY `languageId` (`languageId`),
  KEY `tableElementId` (`tableElementId`),
  CONSTRAINT `FK_Labels_Languages` FOREIGN KEY (`languageId`) REFERENCES `Languages` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Labels_TableElements` FOREIGN KEY (`tableElementId`) REFERENCES `TableElements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=latin1 COMMENT='Literales';

/*Table structure for table `Languages` */

DROP TABLE IF EXISTS `Languages`;

CREATE TABLE `Languages` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del idioma',
  `code` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT 'Código del idioma',
  `name` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT 'Nombre del idioma',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Languages_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='Idiomas';

/*Table structure for table `Larvicides` */

DROP TABLE IF EXISTS `Larvicides`;

CREATE TABLE `Larvicides` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `unity` varchar(10) CHARACTER SET utf8 NOT NULL,
  `dose` decimal(6,2) NOT NULL,
  `doseName` varchar(50) CHARACTER SET utf8 NOT NULL,
  `waterVolume` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `Modules` */

DROP TABLE IF EXISTS `Modules`;

CREATE TABLE `Modules` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del modulo',
  `verb` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT 'Tipo de llamada http (GET, POST, PUT, DELETE,...)',
  `module` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT 'Modulo (/users, ...)',
  `group` varchar(50) NOT NULL COMMENT 'Agrupación modulo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Modules_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1 COMMENT='Modulos asociados a la API';

/*Table structure for table `Permissions` */

DROP TABLE IF EXISTS `Permissions`;

CREATE TABLE `Permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del permiso',
  `profileId` int(11) NOT NULL COMMENT 'Id del perfil',
  `moduleId` int(11) NOT NULL COMMENT 'Id del modulo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Permissions_id` (`id`),
  KEY `moduleId` (`moduleId`),
  KEY `profileId` (`profileId`),
  CONSTRAINT `FK_Permissions_Modules` FOREIGN KEY (`moduleId`) REFERENCES `Modules` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Permissions_Profiles` FOREIGN KEY (`profileId`) REFERENCES `Profiles` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9219 DEFAULT CHARSET=latin1 COMMENT='Permisos concedidos a perfiles sobre modulos de la API';

/*Table structure for table `Persons` */

DROP TABLE IF EXISTS `Persons`;

CREATE TABLE `Persons` (
  `uuid` binary(16) NOT NULL COMMENT 'Id de la persona',
  `name` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT 'Nombre',
  `cardId` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'DNI de la persona',
  `genre` char(1) NOT NULL COMMENT 'M: masculino F: Femenino',
  `birthday` date NOT NULL COMMENT 'Fecha de nacimiento / edad',
  `birthdayExact` bit(1) NOT NULL COMMENT 'Indica si la fecha de nacimiento es exacta (true) o si se ha rellenado solamente la edad (false)',
  `enabled` bit(1) NOT NULL COMMENT 'Habilitado',
  `houseUuid` binary(16) NOT NULL COMMENT 'Id de la visita',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UQ_Persons_uuid` (`uuid`),
  KEY `houseUuid` (`houseUuid`),
  CONSTRAINT `FK_Persons_Houses` FOREIGN KEY (`houseUuid`) REFERENCES `Houses` (`uuid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Personas de la vivienda';

/*Table structure for table `Plans` */

DROP TABLE IF EXISTS `Plans`;

CREATE TABLE `Plans` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del plan',
  `date` date NOT NULL COMMENT 'Fecha del plan',
  `planSize` int(4) NOT NULL COMMENT 'Tamaño de la muestra (por defecto 20)',
  `stateId` int(11) NOT NULL COMMENT 'Id del estado del plan',
  `userId` int(11) NOT NULL COMMENT 'Id del inspector',
  `inspectionId` int(11) NOT NULL COMMENT 'Id de la inspección',
  `houseInterval` tinyint(4) NOT NULL COMMENT 'Intervalo intervencion',
  `houseIni` tinyint(4) NOT NULL COMMENT 'Vivienda inicio',
  `syncFile` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Plans_id` (`id`),
  KEY `userId` (`userId`),
  KEY `inspectionId` (`inspectionId`),
  KEY `stateId` (`stateId`),
  CONSTRAINT `FK_Plans_Inspections` FOREIGN KEY (`inspectionId`) REFERENCES `Inspections` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Plans_States` FOREIGN KEY (`stateId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Plans_Users` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3832 DEFAULT CHARSET=latin1 COMMENT='Planes';

/*Table structure for table `PlansAreas` */

DROP TABLE IF EXISTS `PlansAreas`;

CREATE TABLE `PlansAreas` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador',
  `planId` int(11) NOT NULL COMMENT 'Id del plan',
  `areaId` int(11) NOT NULL COMMENT 'Id de la zona',
  `scheduledHouses` int(4) DEFAULT NULL,
  `substitute` bit(1) NOT NULL COMMENT 'Manzana suplente',
  `pin` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_PlansAreas_id` (`id`),
  KEY `areaId` (`areaId`),
  KEY `planId` (`planId`),
  CONSTRAINT `FK_PlansAreas_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_PlansAreas_Plans` FOREIGN KEY (`planId`) REFERENCES `Plans` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=353402 DEFAULT CHARSET=latin1;

/*Table structure for table `ProfileAlerts` */

DROP TABLE IF EXISTS `ProfileAlerts`;

CREATE TABLE `ProfileAlerts` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `profileId` int(11) NOT NULL,
  `alertTypeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `profileId` (`profileId`),
  KEY `alertTypeId` (`alertTypeId`),
  CONSTRAINT `FK_ProfileAlerts_Profiles` FOREIGN KEY (`profileId`) REFERENCES `Profiles` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ProfileAlerts_TableElements` FOREIGN KEY (`alertTypeId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4840 DEFAULT CHARSET=latin1;

/*Table structure for table `Profiles` */

DROP TABLE IF EXISTS `Profiles`;

CREATE TABLE `Profiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del perfil',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT 'Nombre del perfil',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Descripción del perfil',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Profiles_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1 COMMENT='Perfiles';

/*Table structure for table `ReportInspections` */

DROP TABLE IF EXISTS `ReportInspections`;

CREATE TABLE `ReportInspections` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `reportId` int(10) NOT NULL,
  `inspectionId` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `reportId` (`reportId`),
  KEY `inspectionId` (`inspectionId`),
  CONSTRAINT `FK_ReportsInspection_Inspection` FOREIGN KEY (`inspectionId`) REFERENCES `Inspections` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ReportsInspection_Report` FOREIGN KEY (`reportId`) REFERENCES `Reports` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1702 DEFAULT CHARSET=utf8;

/*Table structure for table `Reports` */

DROP TABLE IF EXISTS `Reports`;

CREATE TABLE `Reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del informe',
  `date` datetime NOT NULL COMMENT 'Fecha de creación del informe',
  `areaId` int(11) NOT NULL COMMENT 'Id de la zona asociada al informe',
  `name` varchar(100) NOT NULL,
  `startDate` datetime NOT NULL,
  `finishDate` datetime NOT NULL,
  `dataType` int(10) NOT NULL,
  `detailLevel` int(10) NOT NULL,
  `createUserId` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `areaId` (`areaId`),
  KEY `createUserId` (`createUserId`),
  KEY `detailLevel` (`detailLevel`),
  CONSTRAINT `FK_Reports_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`),
  CONSTRAINT `FK_Reports_TableElements` FOREIGN KEY (`detailLevel`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Reports_Users` FOREIGN KEY (`createUserId`) REFERENCES `Users` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=778 DEFAULT CHARSET=latin1;

/*Table structure for table `SamplePhases` */

DROP TABLE IF EXISTS `SamplePhases`;

CREATE TABLE `SamplePhases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phaseId` int(11) NOT NULL,
  `sampleUuid` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `phaseId` (`phaseId`),
  KEY `sampleUuid` (`sampleUuid`),
  CONSTRAINT `FK_SamplePhase_Samples` FOREIGN KEY (`sampleUuid`) REFERENCES `Samples` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_SamplesPhase_Phases` FOREIGN KEY (`phaseId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4169 DEFAULT CHARSET=latin1;

/*Table structure for table `Samples` */

DROP TABLE IF EXISTS `Samples`;

CREATE TABLE `Samples` (
  `uuid` binary(16) NOT NULL COMMENT 'Id de la muestra',
  `code` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT 'Código de la muestra',
  `result` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Resultado del laboratorio',
  `planId` int(11) DEFAULT NULL COMMENT 'Id del plan',
  `containerId` int(11) DEFAULT NULL COMMENT 'Id del contenedor',
  `houseUuid` binary(16) DEFAULT NULL COMMENT 'Id de la vivienda',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UQ_Samples_uuid` (`uuid`),
  KEY `planId` (`planId`),
  KEY `containerId` (`containerId`),
  KEY `houseUuid` (`houseUuid`),
  CONSTRAINT `FK_Samples_Container` FOREIGN KEY (`containerId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Samples_Houses` FOREIGN KEY (`houseUuid`) REFERENCES `Houses` (`uuid`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Samples_Plans` FOREIGN KEY (`planId`) REFERENCES `Plans` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Muestras';

/*Table structure for table `Scenes` */

DROP TABLE IF EXISTS `Scenes`;

CREATE TABLE `Scenes` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del escenario',
  `areaId` int(11) NOT NULL COMMENT 'Id de la zona',
  `sceneLevel` tinyint(4) NOT NULL COMMENT 'Nivel escenario (1/2/3)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Scenes_id` (`id`),
  KEY `areaId` (`areaId`),
  CONSTRAINT `FK_Scenes_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1 COMMENT='Escenarios';

/*Table structure for table `Schedules` */

DROP TABLE IF EXISTS `Schedules`;

CREATE TABLE `Schedules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `startDate` date NOT NULL,
  `finishDate` date NOT NULL,
  `typeId` int(11) NOT NULL,
  `areaId` int(11) NOT NULL,
  `reconversionScheduleId` int(11) DEFAULT NULL,
  `larvicideId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `typeId` (`typeId`),
  KEY `areaId` (`areaId`),
  KEY `larvicideId` (`larvicideId`),
  CONSTRAINT `FK_Schedule_areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Schedule_larvicide` FOREIGN KEY (`larvicideId`) REFERENCES `Larvicides` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Schedule_TableElements` FOREIGN KEY (`typeId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=367 DEFAULT CHARSET=latin1;

/*Table structure for table `Symptoms` */

DROP TABLE IF EXISTS `Symptoms`;

CREATE TABLE `Symptoms` (
  `uuid` binary(16) NOT NULL COMMENT 'Id del febril',
  `symptomId` int(11) NOT NULL COMMENT 'Id del sintoma',
  `personUuid` binary(16) NOT NULL COMMENT 'Id de la persona',
  `visitUuid` binary(16) NOT NULL COMMENT 'Id de la visita',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UQ_Symptoms_uuid` (`uuid`),
  KEY `personUuid` (`personUuid`),
  KEY `visitUuid` (`visitUuid`),
  KEY `symptomId` (`symptomId`),
  CONSTRAINT `FK_Symptoms_Persons` FOREIGN KEY (`personUuid`) REFERENCES `Persons` (`uuid`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Symptoms_Symptoms` FOREIGN KEY (`symptomId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Symptoms_Visits` FOREIGN KEY (`visitUuid`) REFERENCES `Visits` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TableElements` */

DROP TABLE IF EXISTS `TableElements`;

CREATE TABLE `TableElements` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del elemento',
  `sort` int(11) DEFAULT NULL COMMENT 'Orden en caso de que la tabla este ordenada',
  `tableHeaderId` int(11) NOT NULL COMMENT 'Id de la tabla asociada',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_TableElements_id` (`id`),
  KEY `tableHeaderId` (`tableHeaderId`),
  CONSTRAINT `FK_TableElements_TableHeaders` FOREIGN KEY (`tableHeaderId`) REFERENCES `TableHeaders` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11005 DEFAULT CHARSET=latin1 COMMENT='Elementos de tabla';

/*Table structure for table `TableHeaders` */

DROP TABLE IF EXISTS `TableHeaders`;

CREATE TABLE `TableHeaders` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla de tabla',
  `name` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT 'Nombre',
  `system` bit(1) NOT NULL COMMENT 'Indica si la lista es de sistema',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_TableHeaders_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COMMENT='Permiten definir listas personalizadas.';

/*Table structure for table `TrapData` */

DROP TABLE IF EXISTS `TrapData`;

CREATE TABLE `TrapData` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `eggs` int(11) DEFAULT NULL,
  `resultId` int(11) DEFAULT NULL,
  `trapId` int(11) NOT NULL,
  `trapLocationId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `trapId` (`trapId`),
  KEY `trapLocationId` (`trapLocationId`),
  KEY `resultId` (`resultId`),
  CONSTRAINT `FK_TrapData_TableElementes` FOREIGN KEY (`resultId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_TrapData_TrapLocation` FOREIGN KEY (`trapLocationId`) REFERENCES `TrapLocations` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_TrapData_Traps` FOREIGN KEY (`trapId`) REFERENCES `Traps` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=84252 DEFAULT CHARSET=latin1;

/*Table structure for table `TrapLocations` */

DROP TABLE IF EXISTS `TrapLocations`;

CREATE TABLE `TrapLocations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `longitude` decimal(9,6) DEFAULT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `altitude` decimal(9,2) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `trapId` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `trapId` (`trapId`),
  CONSTRAINT `FK_TrapLocation_Traps` FOREIGN KEY (`trapId`) REFERENCES `Traps` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1497 DEFAULT CHARSET=latin1;

/*Table structure for table `Traps` */

DROP TABLE IF EXISTS `Traps`;

CREATE TABLE `Traps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8 NOT NULL,
  `number` int(11) unsigned NOT NULL,
  `enabled` bit(1) NOT NULL,
  `areaId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `areaId` (`areaId`),
  CONSTRAINT `FK_Traps_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1416 DEFAULT CHARSET=latin1;

/*Table structure for table `Users` */

DROP TABLE IF EXISTS `Users`;

CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id del usuario',
  `login` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT 'Login',
  `password` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT 'Contraseña',
  `name` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT 'Nombre completo',
  `email` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Correo electronico',
  `enabled` bit(1) NOT NULL COMMENT 'Habilitado',
  `token` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Identificador de sesión',
  `tokenExpiration` datetime DEFAULT NULL COMMENT 'Fecha de expiración de la sesión',
  `profileId` int(11) NOT NULL COMMENT 'Id del perfil',
  `languageId` int(11) NOT NULL COMMENT 'Id del idioma',
  `areaId` int(11) NOT NULL COMMENT 'Id el área',
  `loginErrors` int(11) DEFAULT NULL COMMENT 'Número de errores haciendo login del usuario',
  `urlToken` varchar(100) DEFAULT NULL,
  `urlTokenExpiration` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Users_id` (`id`),
  KEY `languageId` (`languageId`),
  KEY `profileId` (`profileId`),
  KEY `areaId` (`areaId`),
  CONSTRAINT `FK_Users_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Users_Languages` FOREIGN KEY (`languageId`) REFERENCES `Languages` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Users_Profiles` FOREIGN KEY (`profileId`) REFERENCES `Profiles` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=742 DEFAULT CHARSET=latin1 COMMENT='Brigadista/usuario de la web';

/*Table structure for table `VisitSummaries` */

DROP TABLE IF EXISTS `VisitSummaries`;

CREATE TABLE `VisitSummaries` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `houses` int(10) NOT NULL,
  `focus` int(10) NOT NULL DEFAULT '0',
  `inspected` int(10) NOT NULL,
  `closed` int(10) NOT NULL,
  `reluctant` int(10) NOT NULL,
  `abandoned` int(10) NOT NULL,
  `treated` int(10) NOT NULL DEFAULT '0',
  `destroyed` int(10) NOT NULL DEFAULT '0',
  `people` int(10) NOT NULL,
  `febriles` int(10) NOT NULL,
  `larvicide` decimal(10,2) DEFAULT NULL,
  `planId` int(10) NOT NULL,
  `areaId` int(10) NOT NULL,
  `reconverted` int(10) NOT NULL,
  `closedReconverted` int(10) NOT NULL DEFAULT '0' COMMENT 'total casa cerradas reconvertidas',
  `reluctantReconverted` int(10) NOT NULL DEFAULT '0' COMMENT 'total casas renuentes reconvertidas',
  `abandonedReconverted` int(10) NOT NULL DEFAULT '0' COMMENT 'total casas abandonadas reconvertidas',
  PRIMARY KEY (`id`),
  KEY `planId` (`planId`),
  KEY `areaId` (`areaId`),
  CONSTRAINT `VisitSummaries_Areas` FOREIGN KEY (`areaId`) REFERENCES `Areas` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `VisitSummaries_Plans` FOREIGN KEY (`planId`) REFERENCES `Plans` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13187 DEFAULT CHARSET=latin1;

/*Table structure for table `Visits` */

DROP TABLE IF EXISTS `Visits`;

CREATE TABLE `Visits` (
  `uuid` binary(16) NOT NULL COMMENT 'Id de la visita',
  `date` date NOT NULL COMMENT 'Fecha de la visita',
  `feverish` tinyint(4) NOT NULL COMMENT 'Febriles',
  `larvicide` decimal(10,2) DEFAULT NULL,
  `comments` varchar(250) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Comentarios',
  `resultId` int(11) DEFAULT NULL COMMENT 'Resultado de la visita: Visita/Cerrada/Renuente/Deshabitada',
  `userId` int(11) DEFAULT NULL COMMENT 'Brigadista que ha registrado la visita',
  `planId` int(11) NOT NULL COMMENT 'Id del plan',
  `houseUuid` binary(16) NOT NULL COMMENT 'Id de la vivienda',
  `requalify` bit(1) NOT NULL DEFAULT b'0' COMMENT 'Recalificado',
  `beforeReconversion` char(1) DEFAULT NULL COMMENT 'Estado de la vivienda visitada antes de recalificar Cerrada/Renuente/Abandonada',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UQ_Visits_uuid` (`uuid`),
  KEY `houseUuid` (`houseUuid`),
  KEY `planId` (`planId`),
  KEY `userId` (`userId`),
  KEY `resultId` (`resultId`),
  CONSTRAINT `FK_Visits_Houses` FOREIGN KEY (`houseUuid`) REFERENCES `Houses` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Visits_Plans` FOREIGN KEY (`planId`) REFERENCES `Plans` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Visits_Results` FOREIGN KEY (`resultId`) REFERENCES `TableElements` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Visits_Users` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* Trigger structure for table `Areas` */

DELIMITER $$

CREATE
   
    TRIGGER `areasCreateDescendants` AFTER INSERT ON `Areas` 
    FOR EACH ROW CALL updateDescendants(NEW.id);
$$
DELIMITER ;


/* Function  structure for function  `ChildHouses` */

DROP FUNCTION IF EXISTS `ChildHouses` ;

DELIMITER $$
 CREATE FUNCTION `ChildHouses`(areaId INT(11)) RETURNS int(11)
    DETERMINISTIC
BEGIN
	DECLARE totalHouses INT(11);
	SELECT SUM(houses) INTO totalHouses FROM Areas WHERE parentId = areaId;
	IF (totalHouses = 0) THEN
		SET totalHouses = 1;
	END IF;
	RETURN totalHouses;
    END $$
DELIMITER ;

/* Procedure structure for procedure `rebuildDescendants` */

DROP PROCEDURE IF EXISTS  `rebuildDescendants`;

DELIMITER $$

CREATE PROCEDURE `rebuildDescendants`(pRootAreaId INT, pAreaId INT, pLevel INT)
BEGIN

		DECLARE mAreaId INT;

		DECLARE mDone INT DEFAULT FALSE;

		DECLARE mCursor CURSOR FOR SELECT id FROM Areas WHERE parentId = pAreaId;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET mDone = TRUE;

		  

		IF pRootAreaId = 1 AND pLevel = 0 THEN 

			DELETE FROM AreaDescendants;

		END IF;

		INSERT INTO AreaDescendants(areaId, descendantId, LEVEL) VALUES(pRootAreaId, pAreaId, pLevel);

	  

		OPEN mCursor;

		read_loop: LOOP

			FETCH mCursor INTO mAreaId;

			IF mDone THEN

				LEAVE read_loop;

			END IF;

			CALL rebuildDescendants(pRootAreaId, mAreaId, pLevel + 1);

			IF NOT EXISTS(SELECT * FROM AreaDescendants WHERE areaId = mAreaId) THEN

				CALL rebuildDescendants(mAreaId, mAreaId, 0);

			END IF;

		END LOOP;

		CLOSE mCursor;

	END $$
DELIMITER ;

/* Procedure structure for procedure `updateAreaHouses` */

DROP PROCEDURE IF EXISTS  `updateAreaHouses` ;

DELIMITER $$

CREATE PROCEDURE `updateAreaHouses`(areaId INT)
BEGIN
		DECLARE areaHouses INT;
		DECLARE areaParentId INT;
		DECLARE childrenHouses INT;
		SELECT SUM(houses) INTO childrenHouses FROM Areas WHERE parentId = areaId;
		SELECT IFNULL(houses, 0), parentId INTO areaHouses, areaParentId FROM Areas WHERE id = areaId;
		IF childrenHouses > areaHouses THEN 
			UPDATE Areas SET houses = childrenHouses WHERE id = areaId;
			CALL updateAreaHouses(areaParentId);
		END IF;
	END $$
DELIMITER ;

/* Procedure structure for procedure `updateDescendants` */

DROP PROCEDURE IF EXISTS  `updateDescendants`;

DELIMITER $$

CREATE PROCEDURE `updateDescendants`(areaId INT)
BEGIN
	SET @parent = areaId;
	SET @level = 0;
	REPEAT 
		INSERT INTO AreaDescendants(areaId, descendantId, LEVEL) VALUES(@parent, areaId, @level);
		SELECT parentId INTO @parent FROM Areas WHERE id = @parent;
		SET @level = @level + 1;
	UNTIL @parent IS NULL OR @level > 20 END REPEAT;
    END $$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
