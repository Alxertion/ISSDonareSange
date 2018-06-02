-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema issdonaresange
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema issdonaresange
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `issdonaresange` DEFAULT CHARACTER SET utf8 ;
USE `issdonaresange` ;

-- -----------------------------------------------------
-- Table `issdonaresange`.`analiza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`analiza` (
  `idAnaliza` INT(11) NOT NULL AUTO_INCREMENT,
  `grupa` VARCHAR(100) NULL DEFAULT NULL,
  `rh` BIT(2) NULL DEFAULT NULL,
  PRIMARY KEY (`idAnaliza`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`boala`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`boala` (
  `nume` VARCHAR(100) NOT NULL,
  `idBoala` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idBoala`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`bolianaliza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`bolianaliza` (
  `idAnaliza` INT(11) NOT NULL,
  `idBoala` INT(11) NOT NULL,
  PRIMARY KEY (`idAnaliza`, `idBoala`),
  INDEX `FK_BoliAnaliza_idx` (`idAnaliza` ASC),
  INDEX `FKq5a91u0kg1pkmjtblf6erdttx` (`idBoala` ASC),
  CONSTRAINT `FKkrqgmftkj432c8b85nmnp7w0b`
    FOREIGN KEY (`idAnaliza`)
    REFERENCES `issdonaresange`.`analiza` (`idAnaliza`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKq5a91u0kg1pkmjtblf6erdttx`
    FOREIGN KEY (`idBoala`)
    REFERENCES `issdonaresange`.`boala` (`idBoala`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`cont`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`cont` (
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`donator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`donator` (
  `idDonator` INT(11) NOT NULL AUTO_INCREMENT,
  `nume` VARCHAR(100) NULL DEFAULT NULL,
  `prenume` VARCHAR(100) NULL DEFAULT NULL,
  `username` VARCHAR(100) NULL DEFAULT NULL,
  `sex` BIT(2) NULL DEFAULT NULL,
  `dataNasterii` DATE NULL DEFAULT NULL,
  `greutate` DOUBLE NULL DEFAULT NULL,
  `puls` INT(11) NULL DEFAULT NULL,
  `tensiuneArterialaSistolica` INT(11) NULL DEFAULT NULL,
  `interventiiChirurgicaleRecente` TINYINT(4) NULL DEFAULT NULL,
  `insarcinata` TINYINT(4) NULL DEFAULT NULL,
  `perioadaLauzie` TINYINT(4) NULL DEFAULT NULL,
  `perioadaMenstruala` TINYINT(4) NULL DEFAULT NULL,
  `consumGrasimi48h` TINYINT(4) NULL DEFAULT NULL,
  `consumBauturiAlcoolice48h` TINYINT(4) NULL DEFAULT NULL,
  `subTratament` TINYINT(4) NULL DEFAULT NULL,
  `idCentruTransfuzii` INT(11) NULL DEFAULT NULL,
  `cnp` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `telefon` VARCHAR(255) NULL DEFAULT NULL,
  `latitudine` DOUBLE NULL DEFAULT NULL,
  `longitudine` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`idDonator`),
  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC),
  INDEX `FKjydivqq69nass0l8c16tudoy3` (`username` ASC),
  CONSTRAINT `FKjydivqq69nass0l8c16tudoy3`
    FOREIGN KEY (`username`)
    REFERENCES `issdonaresange`.`cont` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`bolidonator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`bolidonator` (
  `idDonator` INT(11) NOT NULL,
  `idBoala` INT(11) NOT NULL,
  PRIMARY KEY (`idDonator`, `idBoala`),
  INDEX `FK_BoliDonator_Donator_idx` (`idDonator` ASC),
  INDEX `FK3dwlfc891u7u2i6aotxqf30cd` (`idBoala` ASC),
  CONSTRAINT `FK3dwlfc891u7u2i6aotxqf30cd`
    FOREIGN KEY (`idBoala`)
    REFERENCES `issdonaresange`.`boala` (`idBoala`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKh2woy3ry7drtbk74muu2gpjiq`
    FOREIGN KEY (`idDonator`)
    REFERENCES `issdonaresange`.`donator` (`idDonator`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`centrutransfuzii`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`centrutransfuzii` (
  `idCentruTransfuzii` INT(11) NOT NULL AUTO_INCREMENT,
  `latitudine` DOUBLE NULL DEFAULT NULL,
  `longitudine` DOUBLE NULL DEFAULT NULL,
  `nume` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idCentruTransfuzii`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`centrutransfuziidonator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`centrutransfuziidonator` (
  `idCentruTransfuzii` INT(11) NOT NULL,
  `idDonator` INT(11) NOT NULL,
  PRIMARY KEY (`idCentruTransfuzii`, `idDonator`),
  INDEX `FK3tspa93ytdasc9p89i6e6psot` (`idDonator` ASC),
  CONSTRAINT `FK3tspa93ytdasc9p89i6e6psot`
    FOREIGN KEY (`idDonator`)
    REFERENCES `issdonaresange`.`donator` (`idDonator`),
  CONSTRAINT `FK6px66cusw14f83676idrbt571`
    FOREIGN KEY (`idCentruTransfuzii`)
    REFERENCES `issdonaresange`.`centrutransfuzii` (`idCentruTransfuzii`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`spital`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`spital` (
  `idSpital` INT(11) NOT NULL AUTO_INCREMENT,
  `nume` VARCHAR(100) NULL DEFAULT NULL,
  `longitudine` DOUBLE NULL DEFAULT NULL,
  `latitudine` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`idSpital`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`pacient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`pacient` (
  `idPacient` INT(11) NOT NULL AUTO_INCREMENT,
  `cnp` VARCHAR(100) NOT NULL,
  `nume` VARCHAR(100) NULL DEFAULT NULL,
  `prenume` VARCHAR(100) NULL DEFAULT NULL,
  `idSpital` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idPacient`),
  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC),
  INDEX `FKtne6o8vybkkmgt6l57tfc4jqk` (`idSpital` ASC),
  CONSTRAINT `FKtne6o8vybkkmgt6l57tfc4jqk`
    FOREIGN KEY (`idSpital`)
    REFERENCES `issdonaresange`.`spital` (`idSpital`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`medic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`medic` (
  `idMedic` INT(11) NOT NULL AUTO_INCREMENT,
  `nume` VARCHAR(100) NULL DEFAULT NULL,
  `prenume` VARCHAR(100) NULL DEFAULT NULL,
  `username` VARCHAR(100) NULL DEFAULT NULL,
  `idSpital` INT(11) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `cnp` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idMedic`),
  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC),
  INDEX `FKqgef8u9jw3qn8ek0xos9j79d1` (`idSpital` ASC),
  INDEX `FKqjob9ry4s1kgmeg5qyp8h8pks` (`username` ASC),
  CONSTRAINT `FKqgef8u9jw3qn8ek0xos9j79d1`
    FOREIGN KEY (`idSpital`)
    REFERENCES `issdonaresange`.`spital` (`idSpital`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKqjob9ry4s1kgmeg5qyp8h8pks`
    FOREIGN KEY (`username`)
    REFERENCES `issdonaresange`.`cont` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`cerere`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`cerere` (
  `idMedic` INT(11) NULL DEFAULT NULL,
  `prioritate` INT(11) NULL DEFAULT NULL,
  `grupa` VARCHAR(100) NULL DEFAULT NULL,
  `rh` BIT(2) NULL DEFAULT NULL,
  `cantitateCeruta` DOUBLE NULL DEFAULT NULL,
  `cantitateActuala` DOUBLE NULL DEFAULT NULL,
  `dataEfectuare` DATE NULL DEFAULT NULL,
  `idPacient` INT(11) NULL DEFAULT NULL,
  `idCerere` INT(11) NOT NULL AUTO_INCREMENT,
  `tipSange` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idCerere`),
  INDEX `FKfg1ksi5g7ocmvyjhtiv4jf10r` (`idPacient` ASC),
  INDEX `FKoyfjkl9wc4a6urulkp8bmedag` (`idMedic` ASC),
  CONSTRAINT `FKfg1ksi5g7ocmvyjhtiv4jf10r`
    FOREIGN KEY (`idPacient`)
    REFERENCES `issdonaresange`.`pacient` (`idPacient`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKoyfjkl9wc4a6urulkp8bmedag`
    FOREIGN KEY (`idMedic`)
    REFERENCES `issdonaresange`.`medic` (`idMedic`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`personaltransfuzii`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`personaltransfuzii` (
  `idPersonalTransfuzii` INT(11) NOT NULL AUTO_INCREMENT,
  `nume` VARCHAR(100) NULL DEFAULT NULL,
  `prenume` VARCHAR(100) NULL DEFAULT NULL,
  `username` VARCHAR(100) NULL DEFAULT NULL,
  `idCentruTransfuzii` INT(11) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `cnp` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idPersonalTransfuzii`),
  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC),
  INDEX `FK5121yfiw93ta19e5mis8yevir` (`username` ASC),
  INDEX `FKowylwiqr4ocorgrbaias43kmm` (`idCentruTransfuzii` ASC),
  CONSTRAINT `FK5121yfiw93ta19e5mis8yevir`
    FOREIGN KEY (`username`)
    REFERENCES `issdonaresange`.`cont` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKowylwiqr4ocorgrbaias43kmm`
    FOREIGN KEY (`idCentruTransfuzii`)
    REFERENCES `issdonaresange`.`centrutransfuzii` (`idCentruTransfuzii`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`preparatsanguin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`preparatsanguin` (
  `tip` VARCHAR(50) NULL DEFAULT NULL,
  `dataPrelevare` DATE NULL DEFAULT NULL,
  `dataExpirare` DATE NULL DEFAULT NULL,
  `idDonator` INT(11) NULL DEFAULT NULL,
  `idAnaliza` INT(11) NULL DEFAULT NULL,
  `cantitate` DOUBLE NULL DEFAULT NULL,
  `idPacient` INT(11) NULL DEFAULT NULL,
  `stagiu` VARCHAR(50) NULL DEFAULT NULL,
  `idPreparatSanguin` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idPreparatSanguin`),
  INDEX `FK4p1k8ir2sasgvae1le2yse36e` (`idPacient` ASC),
  INDEX `FK8jwdsahnx8mlgpjwvx8r07c4k` (`idDonator` ASC),
  INDEX `FKncynpy9lo5l0v80ftb454l8ap` (`idAnaliza` ASC),
  CONSTRAINT `FK4p1k8ir2sasgvae1le2yse36e`
    FOREIGN KEY (`idPacient`)
    REFERENCES `issdonaresange`.`pacient` (`idPacient`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK8jwdsahnx8mlgpjwvx8r07c4k`
    FOREIGN KEY (`idDonator`)
    REFERENCES `issdonaresange`.`donator` (`idDonator`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKncynpy9lo5l0v80ftb454l8ap`
    FOREIGN KEY (`idAnaliza`)
    REFERENCES `issdonaresange`.`analiza` (`idAnaliza`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

----------------------------- INSERTURILE -----------------------------
INSERT INTO `spital` VALUES (1,'Spitalul Clinic Municipal',46.7893531,23.6053422),(2,'Spitalul Clinic Judetean de Urgenta',46.7656769,23.5798223),(3,'Spitalul Clinic de Recuperare',46.7549089,23.5800931),(4,'Spitalul Clinic de Boli Infectioase',46.7604263,23.5817718);
INSERT INTO `centrutransfuzii` VALUES (1,46.7758616,23.5957253,'Centru Unu'),(2,46.7758616,23.5957253,'Centru Doi'),(3,46.7758616,23.5957253,'Centru Trei');
INSERT INTO `boala` VALUES ('HEPATITA',1),('BOLI_DE_INIMA',2),('BOLI_PSIHICE',3),('BRUCELOZA',4),('CANCER',5),('DIABET_ZAHARAT',6),('EPILEPSIE',7),('MALARIE',8),('MIOPIE_FORTE',9),('PSORIAZIS',10),('SIFILIS',11),('TBC',12),('ULCER',13),('VITILIGO',14);
INSERT INTO `analiza` VALUES (1,'B(III)','\0'),(2,'A(II)',''),(3,'O(I)',''),(4,'O(I)','\0'),(5,'AB(IV)',''),(6,'AB(IV)','\0'),(7,'A(II)','\0'),(8,'B(III)','');
INSERT INTO `bolianaliza` VALUES (1,8);
INSERT INTO `cont` VALUES ('chise_b','pass'),('chise_boby','pass3'),('chise_bogdan','pass2'),('personal1','personal1'),('personal12','personal2'),('root','root'),('roots','roots'),('test','test'),('popescu_vasile','popescu'),('popescu_alexandra','alexandra'),('avadanei_lavinia','lavinia');
INSERT INTO `medic` VALUES (1,'Chise','Bogdan','roots',1,'oti_otniel97@yahoo.com','297072508'),(2,'Boros','Otniel','test',2,'oti_otniel97@yahoo.com','18507255038'),(3,'Cezar','Ouatu','root',3,'oti_otniel97@yahoo.com','18607255038');
INSERT INTO `personaltransfuzii` VALUES (1,'Cezara','Grigoreta','personal1',1,'oti_otniel97@yahoo.com','1970725055038'),(2,'Nicolae','Ceausescu','personal12',2,'oti_otniel97@yahoo.com','1970725055000');
INSERT INTO `pacient` VALUES (1,'1134223426','Nume1','Prenume1',1),(2,'11342234245','Nume2','Prenume2',2);
INSERT INTO `cerere` VALUES (1,3,'A(II)','',300,200,'2018-05-21',1,1,'TROMBOCITE'),(2,3,'B(III)','\0',200,100,'2018-01-26',1,2,'TROMBOCITE'),(3,1,'B(III)','\0',200,0,'2015-02-16',2,3,'GLOBULE_ROSII');
INSERT INTO `donator` VALUES (1,'Boros','Otniel','chise_bogdan',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1770725055098','chise_b@yahoo.com',NULL,46.7758616,23.597914),(2,'Chise','Bogdan','chise_boby',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1270725055088','chise_bogdan@yahoo.com',NULL,NULL,NULL),(3,'Boros','Florin','chise_b',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1270725055089','oti_otniel97@yahoo.com',NULL,NULL,NULL);
INSERT INTO `donator` VALUES (4,'Popescu','Vasile','chise_bogdan',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1770725055090','chise_b@yahoo.com',NULL,46.7758616,23.597914),(5,'Popescu','Alexandra','popescu_alexandra',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1770725055050','oti_otniel97@yahoo',NULL,46.00,23.00),(6,'Avadanei','Lavinia','avadanei_lavinia',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1770725055015','oti_otniel97@yahoo.com',NULL,42.00,20.00);
INSERT INTO `preparatsanguin` VALUES ('SANGE_NEFILTRAT','2018-05-21','2019-05-21',1,1,400,NULL,'PRELEVARE',1),('TROMBOCITE','2018-05-21','2019-05-21',1,3,100,NULL,'PRELEVARE',2),('GLOBULE_ROSII','2018-05-21','2019-05-21',1,1,100,NULL,'PRELEVARE',3),('PLASMA','2018-05-21','2019-05-21',1,2,200,NULL,'PRELEVARE',4),('SANGE_NEFILTRAT','2018-01-26','2019-01-26',2,2,400,NULL,'PRELEVARE',5),('TROMBOCITE','2018-01-26','2019-01-26',2,1,100,NULL,'PRELEVARE',6),('GLOBULE_ROSII','2018-01-26','2019-01-26',2,4,100,NULL,'PRELEVARE',7),('PLASMA','2018-01-26','2019-01-26',2,6,200,NULL,'PRELEVARE',8),('SANGE_NEFILTRAT','2018-01-26','2019-02-16',1,3,400,NULL,'PRELEVARE',9),('TROMBOCITE','2018-01-26','2019-02-16',1,2,100,NULL,'PRELEVARE',10),('GLOBULE_ROSII','2018-01-26','2019-02-16',1,4,100,NULL,'PRELEVARE',11),('PLASMA','2018-01-26','2019-02-16',1,4,200,NULL,'PRELEVARE',12),('SANGE_NEFILTRAT','2018-01-26','2019-01-28',3,5,400,NULL,'PRELEVARE',13),('TROMBOCITE','2018-01-26','2019-01-28',3,6,100,NULL,'PRELEVARE',14),('GLOBULE_ROSII','2018-01-26','2019-01-28',3,5,100,NULL,'PRELEVARE',15),('PLASMA','2018-01-26','2019-01-28',3,5,200,NULL,'PRELEVARE',16),('SANGE_NEFILTRAT','2018-01-26','2019-01-26',1,2,400,NULL,'PRELEVARE',17),('TROMBOCITE','2018-01-26','2019-01-26',1,3,100,NULL,'PRELEVARE',18),('GLOBULE_ROSII','2018-01-26','2019-01-26',2,1,100,NULL,'PRELEVARE',19),('PLASMA','2018-01-26','2019-01-26',3,6,200,NULL,'PRELEVARE',20),('TROMBOCITE','2018-01-26','2019-01-26',2,1,200,NULL,'PRELEVARE',21);
INSERT INTO `preparatsanguin` VALUES ('SANGE_NEFILTRAT','2018-05-21','2019-05-21',4,7,200,NULL,'PRELEVARE',22),('SANGE_NEFILTRAT','2018-05-21','2019-05-21',5,4,200,NULL,'PRELEVARE',23),('SANGE_NEFILTRAT','2018-05-21','2019-05-21',6,4,300,NULL,'PRELEVARE',24);