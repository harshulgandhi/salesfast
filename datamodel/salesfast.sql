-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema salesfast
-- -----------------------------------------------------
-- This schema contains data model to support backend of salesfast

-- -----------------------------------------------------
-- Schema salesfast
--
-- This schema contains data model to support backend of salesfast
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `salesfast` DEFAULT CHARACTER SET utf8 ;
USE `salesfast` ;

-- -----------------------------------------------------
-- Table `salesfast`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`User` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(20) NOT NULL,
  `lastName` VARCHAR(20) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `contactNumber` VARCHAR(15) NOT NULL,
  `addressLineOne` VARCHAR(100) NOT NULL,
  `addressLineTwo` VARCHAR(100) NULL,
  `city` VARCHAR(20) NOT NULL,
  `state` VARCHAR(20) NOT NULL,
  `zip` VARCHAR(10) NOT NULL,
  `startDate` DATETIME NOT NULL,
  `endDate` DATETIME NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
COMMENT = '				';


-- -----------------------------------------------------
-- Table `salesfast`.`Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Roles` (
  `roleId` INT NOT NULL AUTO_INCREMENT,
  `roleName` VARCHAR(20) NOT NULL,
  `shortName` VARCHAR(10) NULL,
  PRIMARY KEY (`roleId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Targets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Targets` (
  `targetId` INT NOT NULL AUTO_INCREMENT,
  `noOfSalesCall` INT NOT NULL,
  `noOfPrescribing` INT NOT NULL,
  `noOfProspecting` INT NOT NULL,
  `territorySales` INT NULL COMMENT 'Territory sales in is USD',
  `districtSales` INT NULL COMMENT 'District sales in is USD',
  `userId` INT NOT NULL,
  PRIMARY KEY (`targetId`),
  INDEX `fk_Targets_User1_idx` (`userId` ASC),
  CONSTRAINT `fk_Targets_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '				';


-- -----------------------------------------------------
-- Table `salesfast`.`Incentives`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Incentives` (
  `incentiveId` INT NOT NULL AUTO_INCREMENT,
  `amountUsd` INT NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`incentiveId`),
  INDEX `fk_Incentives_User_idx` (`userId` ASC),
  CONSTRAINT `fk_Incentives_User`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '		';


-- -----------------------------------------------------
-- Table `salesfast`.`User_To_Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`User_To_Role` (
  `userRoleId` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `roleId` INT NOT NULL,
  PRIMARY KEY (`userRoleId`),
  INDEX `fk_User_To_Role_User1_idx` (`userId` ASC),
  INDEX `fk_User_To_Role_Roles1_idx` (`roleId` ASC),
  CONSTRAINT `fk_User_To_Role_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_To_Role_Roles1`
    FOREIGN KEY (`roleId`)
    REFERENCES `salesfast`.`Roles` (`roleId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '	';


-- -----------------------------------------------------
-- Table `salesfast`.`Medical_Fields`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Medical_Fields` (
  `medicalFieldId` VARCHAR(5) NOT NULL,
  `medicalFIeldName` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`medicalFieldId`))
ENGINE = InnoDB
COMMENT = '		';


-- -----------------------------------------------------
-- Table `salesfast`.`Products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Products` (
  `productId` INT NOT NULL AUTO_INCREMENT,
  `productName` VARCHAR(45) NOT NULL,
  `releaseDate` VARCHAR(45) NOT NULL,
  `medicalFieldId` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`productId`),
  INDEX `fk_Products_Medical_Fields1_idx` (`medicalFieldId` ASC),
  CONSTRAINT `fk_Products_Medical_Fields1`
    FOREIGN KEY (`medicalFieldId`)
    REFERENCES `salesfast`.`Medical_Fields` (`medicalFieldId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Samples`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Samples` (
  `sampleId` INT NOT NULL AUTO_INCREMENT,
  `sampleName` VARCHAR(45) NOT NULL,
  `productName` VARCHAR(45) NOT NULL,
  `medicalFieldName` VARCHAR(20) NOT NULL,
  `productId` INT NOT NULL,
  `medicalFieldId` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`sampleId`),
  INDEX `fk_Samples_Products1_idx` (`productId` ASC),
  INDEX `fk_Samples_Medical_Fields1_idx` (`medicalFieldId` ASC),
  CONSTRAINT `fk_Samples_Products1`
    FOREIGN KEY (`productId`)
    REFERENCES `salesfast`.`Products` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Samples_Medical_Fields1`
    FOREIGN KEY (`medicalFieldId`)
    REFERENCES `salesfast`.`Medical_Fields` (`medicalFieldId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`User_To_Sample`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`User_To_Sample` (
  `userSameplId` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `sampleId` INT NOT NULL,
  `noOfSamples` INT NOT NULL,
  PRIMARY KEY (`userSameplId`),
  INDEX `fk_User_To_Sample_User1_idx` (`userId` ASC),
  INDEX `fk_User_To_Sample_Samples1_idx` (`sampleId` ASC),
  CONSTRAINT `fk_User_To_Sample_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_To_Sample_Samples1`
    FOREIGN KEY (`sampleId`)
    REFERENCES `salesfast`.`Samples` (`sampleId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '								';


-- -----------------------------------------------------
-- Table `salesfast`.`Training_Material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Training_Material` (
  `trainingMaterialId` INT NOT NULL AUTO_INCREMENT,
  `trainingMaterialUrl` VARCHAR(40) NOT NULL,
  `userId` INT NOT NULL,
  `medicalFieldId` VARCHAR(5) NOT NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`trainingMaterialId`),
  INDEX `fk_Training_Material_User1_idx` (`userId` ASC),
  INDEX `fk_Training_Material_Medical_Fields1_idx` (`medicalFieldId` ASC),
  INDEX `fk_Training_Material_Products1_idx` (`productId` ASC),
  CONSTRAINT `fk_Training_Material_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Training_Material_Medical_Fields1`
    FOREIGN KEY (`medicalFieldId`)
    REFERENCES `salesfast`.`Medical_Fields` (`medicalFieldId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Training_Material_Products1`
    FOREIGN KEY (`productId`)
    REFERENCES `salesfast`.`Products` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Product_To_MedicalField`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Product_To_MedicalField` (
  `productMedicalFieldId` INT NOT NULL AUTO_INCREMENT,
  `medicalFieldId` VARCHAR(5) NOT NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`productMedicalFieldId`),
  INDEX `fk_Product_To_MedicalField_Products1_idx` (`productId` ASC),
  CONSTRAINT `fk_Product_To_MedicalField_Products1`
    FOREIGN KEY (`productId`)
    REFERENCES `salesfast`.`Products` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Physicians_Source`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Physicians_Source` (
  `physicianId` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `emailId` VARCHAR(45) NOT NULL,
  `contactNumber` VARCHAR(15) NOT NULL,
  `addressLineOne` VARCHAR(100) NOT NULL,
  `addressLineTwo` VARCHAR(100) NULL,
  `city` VARCHAR(20) NOT NULL,
  `state` VARCHAR(20) NOT NULL,
  `zip` VARCHAR(10) NOT NULL,
  `medicalField` VARCHAR(5) NOT NULL,
  `practiceStartDate` DATETIME NOT NULL,
  PRIMARY KEY (`physicianId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Physicians_Staging`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Physicians_Staging` (
  `physicianId` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `emailId` VARCHAR(45) NOT NULL,
  `contactNumber` VARCHAR(15) NOT NULL,
  `addressLineOne` VARCHAR(100) NOT NULL,
  `addressLineTwo` VARCHAR(100) NULL,
  `city` VARCHAR(20) NOT NULL,
  `state` VARCHAR(20) NOT NULL,
  `zip` VARCHAR(10) NOT NULL,
  `medicalField` VARCHAR(5) NOT NULL,
  `isNew` BINARY NOT NULL,
  `status` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`physicianId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Prescribing_Physicians`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Prescribing_Physicians` (
  `prescribingPhysicianId` INT NOT NULL AUTO_INCREMENT,
  `prescribingSince` DATETIME NOT NULL,
  `productId` INT NOT NULL,
  `medicalFieldId` VARCHAR(5) NOT NULL,
  `physicianId` INT NOT NULL,
  PRIMARY KEY (`prescribingPhysicianId`),
  INDEX `fk_Prescribing_Physicians_Products1_idx` (`productId` ASC),
  INDEX `fk_Prescribing_Physicians_Medical_Fields1_idx` (`medicalFieldId` ASC),
  INDEX `fk_Prescribing_Physicians_Physicians_Staging1_idx` (`physicianId` ASC),
  CONSTRAINT `fk_Prescribing_Physicians_Products1`
    FOREIGN KEY (`productId`)
    REFERENCES `salesfast`.`Products` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescribing_Physicians_Medical_Fields1`
    FOREIGN KEY (`medicalFieldId`)
    REFERENCES `salesfast`.`Medical_Fields` (`medicalFieldId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescribing_Physicians_Physicians_Staging1`
    FOREIGN KEY (`physicianId`)
    REFERENCES `salesfast`.`Physicians_Staging` (`physicianId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`eDetailing_Credentials`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`eDetailing_Credentials` (
  `eDetailingCredentialId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `physicianId` INT NOT NULL,
  PRIMARY KEY (`eDetailingCredentialId`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `password_UNIQUE` (`password` ASC),
  INDEX `fk_eDetailing_Credentials_Physicians_Staging1_idx` (`physicianId` ASC),
  CONSTRAINT `fk_eDetailing_Credentials_Physicians_Staging1`
    FOREIGN KEY (`physicianId`)
    REFERENCES `salesfast`.`Physicians_Staging` (`physicianId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Districts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Districts` (
  `districtId` INT NOT NULL AUTO_INCREMENT,
  `districtName` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NULL,
  PRIMARY KEY (`districtId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Territories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Territories` (
  `territoryID` INT NOT NULL AUTO_INCREMENT,
  `territoryName` VARCHAR(45) NOT NULL,
  `zip` VARCHAR(10) NOT NULL,
  `userId` INT NOT NULL,
  `districtId` INT NOT NULL,
  PRIMARY KEY (`territoryID`),
  INDEX `fk_Territories_User1_idx` (`userId` ASC),
  INDEX `fk_Territories_Districts1_idx` (`districtId` ASC),
  CONSTRAINT `fk_Territories_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Territories_Districts1`
    FOREIGN KEY (`districtId`)
    REFERENCES `salesfast`.`Districts` (`districtId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Alignments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Alignments` (
  `alignmentId` INT NOT NULL AUTO_INCREMENT,
  `physicianId` INT NOT NULL,
  `userId` INT NOT NULL,
  `Territories_territoryID` INT NOT NULL,
  `districtId` INT NOT NULL,
  `zip` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`alignmentId`),
  INDEX `fk_Alignments_Physicians_Staging1_idx` (`physicianId` ASC),
  INDEX `fk_Alignments_User1_idx` (`userId` ASC),
  INDEX `fk_Alignments_Territories1_idx` (`Territories_territoryID` ASC),
  INDEX `fk_Alignments_Districts1_idx` (`districtId` ASC),
  CONSTRAINT `fk_Alignments_Physicians_Staging1`
    FOREIGN KEY (`physicianId`)
    REFERENCES `salesfast`.`Physicians_Staging` (`physicianId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alignments_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alignments_Territories1`
    FOREIGN KEY (`Territories_territoryID`)
    REFERENCES `salesfast`.`Territories` (`territoryID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alignments_Districts1`
    FOREIGN KEY (`districtId`)
    REFERENCES `salesfast`.`Districts` (`districtId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Meeting_Update`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Meeting_Update` (
  `meetingUpdateId` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `isEDetailed` BINARY NOT NULL,
  `physicianId` INT NOT NULL,
  `productId` INT NOT NULL,
  `medicalFieldId` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`meetingUpdateId`),
  INDEX `fk_Meeting_Update_Physicians_Staging1_idx` (`physicianId` ASC),
  INDEX `fk_Meeting_Update_Products1_idx` (`productId` ASC),
  INDEX `fk_Meeting_Update_Medical_Fields1_idx` (`medicalFieldId` ASC),
  CONSTRAINT `fk_Meeting_Update_Physicians_Staging1`
    FOREIGN KEY (`physicianId`)
    REFERENCES `salesfast`.`Physicians_Staging` (`physicianId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Meeting_Update_Products1`
    FOREIGN KEY (`productId`)
    REFERENCES `salesfast`.`Products` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Meeting_Update_Medical_Fields1`
    FOREIGN KEY (`medicalFieldId`)
    REFERENCES `salesfast`.`Medical_Fields` (`medicalFieldId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Meeting_Experience`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Meeting_Experience` (
  `meetingExperienceId` INT NOT NULL AUTO_INCREMENT,
  `isPhysicianEntry` BINARY NOT NULL,
  `isSalesRepEntry` BINARY NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `genuinelyLikedTheProduct` BINARY NOT NULL,
  `genuinelyDidnotLikedTheProduct` BINARY NOT NULL,
  `affordablePrice` BINARY NOT NULL,
  `notAffordablePrice` BINARY NOT NULL,
  `lessSideEffects` BINARY NOT NULL,
  `moreSideEffects` BINARY NOT NULL,
  `likedPresentation` BINARY NOT NULL,
  `didnotLikedPresentation` BINARY NOT NULL,
  `salesRepConfident` BINARY NOT NULL,
  `salesRepNotConfident` BINARY NOT NULL,
  `companyReputationImpressive` BINARY NOT NULL,
  `companyReputationNotImpressive` BINARY NOT NULL,
  `Meeting_Update_meetingUpdateId` INT NOT NULL,
  PRIMARY KEY (`meetingExperienceId`),
  INDEX `fk_Meeting_Experience_Meeting_Update1_idx` (`Meeting_Update_meetingUpdateId` ASC),
  CONSTRAINT `fk_Meeting_Experience_Meeting_Update1`
    FOREIGN KEY (`Meeting_Update_meetingUpdateId`)
    REFERENCES `salesfast`.`Meeting_Update` (`meetingUpdateId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`eDetailing_Material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`eDetailing_Material` (
  `eDetailingMaterialId` INT NOT NULL AUTO_INCREMENT,
  `detailingFileName` VARCHAR(45) NOT NULL,
  `physicianId` INT NOT NULL,
  `medicalFieldId` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`eDetailingMaterialId`),
  INDEX `fk_eDetailing_Material_Physicians_Staging1_idx` (`physicianId` ASC),
  INDEX `fk_eDetailing_Material_Medical_Fields1_idx` (`medicalFieldId` ASC),
  CONSTRAINT `fk_eDetailing_Material_Physicians_Staging1`
    FOREIGN KEY (`physicianId`)
    REFERENCES `salesfast`.`Physicians_Staging` (`physicianId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_eDetailing_Material_Medical_Fields1`
    FOREIGN KEY (`medicalFieldId`)
    REFERENCES `salesfast`.`Medical_Fields` (`medicalFieldId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Patient_Sample_Feedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Patient_Sample_Feedback` (
  `patientSampleFeedbackId` INT NOT NULL AUTO_INCREMENT,
  `isMedicineEffective` BINARY NOT NULL,
  `hasSideEffects` BINARY NOT NULL,
  `sideEffectsComments` VARCHAR(200) NULL,
  `otherComments` VARCHAR(300) NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`patientSampleFeedbackId`),
  INDEX `fk_Patient_Sample_Feedback_Products1_idx` (`productId` ASC),
  CONSTRAINT `fk_Patient_Sample_Feedback_Products1`
    FOREIGN KEY (`productId`)
    REFERENCES `salesfast`.`Products` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`Appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`Appointment` (
  `appointmentId` INT NOT NULL AUTO_INCREMENT,
  `time` TIME NOT NULL,
  `date` DATE NOT NULL,
  `physicianId` INT NOT NULL,
  `userId` INT NOT NULL,
  `productId` INT NOT NULL,
  `confirmationStatus` VARCHAR(45) NULL,
  PRIMARY KEY (`appointmentId`),
  INDEX `fk_Appointment_Physicians_Staging1_idx` (`physicianId` ASC),
  INDEX `fk_Appointment_User1_idx` (`userId` ASC),
  INDEX `fk_Appointment_Products1_idx` (`productId` ASC),
  CONSTRAINT `fk_Appointment_Physicians_Staging1`
    FOREIGN KEY (`physicianId`)
    REFERENCES `salesfast`.`Physicians_Staging` (`physicianId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_Products1`
    FOREIGN KEY (`productId`)
    REFERENCES `salesfast`.`Products` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesfast`.`User_Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesfast`.`User_Account` (
  `userAccountId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`userAccountId`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `password_UNIQUE` (`password` ASC),
  INDEX `fk_User_Account_User1_idx` (`userId` ASC),
  CONSTRAINT `fk_User_Account_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `salesfast`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
