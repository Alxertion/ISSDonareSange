-- MySQL Script generated by MySQL Workbench
-- Tue May  8 12:07:06 2018
-- Model: New Model    Version: 1.0
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
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`boala`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`boala` (
  `nume` VARCHAR(100) NOT NULL,
  `idBoala` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idBoala`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
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
  PRIMARY KEY (`idDonator`),
  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC),
  INDEX `FKjydivqq69nass0l8c16tudoy3` (`username` ASC),
  CONSTRAINT `FKjydivqq69nass0l8c16tudoy3`
    FOREIGN KEY (`username`)
    REFERENCES `issdonaresange`.`cont` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
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
  `idSpital` INT(11) NOT NULL,
  `nume` VARCHAR(100) NULL DEFAULT NULL,
  `longitudine` DOUBLE NULL DEFAULT NULL,
  `latitudine` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`idSpital`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`pacient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`pacient` (
  `idPacient` INT(11) NOT NULL,
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
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issdonaresange`.`cerere`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issdonaresange`.`cerere` (
  `idMedic` INT(11) NOT NULL,
  `prioritate` INT(11) NULL DEFAULT NULL,
  `grupa` VARCHAR(100) NULL DEFAULT NULL,
  `rh` BIT(2) NULL DEFAULT NULL,
  `cantitateCeruta` DOUBLE NULL DEFAULT NULL,
  `cantitateActuala` DOUBLE NULL DEFAULT NULL,
  `dataEfectuare` DATE NULL DEFAULT NULL,
  `idPacient` INT(11) NOT NULL,
  `idCerere` INT(11) NOT NULL,
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
AUTO_INCREMENT = 1
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
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;