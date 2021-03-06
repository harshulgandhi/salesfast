-- MySQL dump 10.13  Distrib 5.7.10, for Win64 (x86_64)
--
-- Host: localhost    Database: salesfast
-- ------------------------------------------------------
-- Server version	5.7.10-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alignments`
--

DROP TABLE IF EXISTS `alignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alignments` (
  `alignmentId` int(11) NOT NULL AUTO_INCREMENT,
  `physicianId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `territoryId` int(11) NOT NULL,
  `districtId` int(11) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `productId` int(11) NOT NULL,
  PRIMARY KEY (`alignmentId`),
  UNIQUE KEY `Unique_index` (`physicianId`,`userId`,`productId`),
  KEY `fk_Alignments_Physicians_Staging1_idx` (`physicianId`),
  KEY `fk_Alignments_User1_idx` (`userId`),
  KEY `fk_Alignments_Territories1_idx` (`territoryId`),
  KEY `fk_Alignments_Districts1_idx` (`districtId`),
  KEY `fk_Alignments_Product1_idx` (`productId`),
  CONSTRAINT `fk_Alignments_Districts1` FOREIGN KEY (`districtId`) REFERENCES `districts` (`districtId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alignments_Physicians_Staging1` FOREIGN KEY (`physicianId`) REFERENCES `physicians_staging` (`physicianId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alignments_Product1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alignments_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2473 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alignments`
--

LOCK TABLES `alignments` WRITE;
/*!40000 ALTER TABLE `alignments` DISABLE KEYS */;
INSERT INTO `alignments` VALUES (7,1,1,1,1,'11001',3),(8,1,1,1,1,'11001',4),(9,2,1,1,1,'11001',3),(10,2,1,1,1,'11001',4),(11,3,2,2,1,'11003',1),(12,3,2,2,1,'11003',2),(13,4,1,1,1,'11001',3),(14,4,1,1,1,'11001',4),(15,5,1,1,1,'11001',3),(16,5,1,1,1,'11001',4),(17,6,2,2,1,'11003',1),(18,6,2,2,1,'11003',2);
/*!40000 ALTER TABLE `alignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `appointmentId` int(11) NOT NULL AUTO_INCREMENT,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `date` date NOT NULL,
  `physicianId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `confirmationStatus` varchar(45) DEFAULT NULL,
  `zip` varchar(10) DEFAULT NULL,
  `cancellationReason` varchar(150) DEFAULT NULL,
  `additionalNotes` varchar(150) DEFAULT NULL,
  `hasMeetingUpdate` tinyint(4) NOT NULL DEFAULT '0',
  `hasMeetingExperienceFromSR` tinyint(4) NOT NULL DEFAULT '0',
  `hasMeetingExperienceFromPH` tinyint(4) NOT NULL DEFAULT '0',
  `hasPitch` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`appointmentId`),
  KEY `fk_Appointment_Physicians_Staging1_idx` (`physicianId`),
  KEY `fk_Appointment_User1_idx` (`userId`),
  KEY `fk_Appointment_Products1_idx` (`productId`),
  CONSTRAINT `fk_Appointment_Physicians_Staging1` FOREIGN KEY (`physicianId`) REFERENCES `physicians_staging` (`physicianId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_Products1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `districts`
--

DROP TABLE IF EXISTS `districts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `districts` (
  `districtId` int(11) NOT NULL AUTO_INCREMENT,
  `districtName` varchar(45) NOT NULL,
  `state` varchar(45) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`districtId`),
  KEY `fk_District_User1_idx` (`userId`),
  CONSTRAINT `fk_District_User2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `districts`
--

LOCK TABLES `districts` WRITE;
/*!40000 ALTER TABLE `districts` DISABLE KEYS */;
INSERT INTO `districts` VALUES (1,'East','Illinois',4),(2,'East','Maryland',4);
/*!40000 ALTER TABLE `districts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edetailing_material`
--

DROP TABLE IF EXISTS `edetailing_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `edetailing_material` (
  `eDetailingMaterialId` int(11) NOT NULL AUTO_INCREMENT,
  `detailingFileName` varchar(45) NOT NULL,
  `physicianId` int(11) NOT NULL,
  `medicalFieldId` varchar(5) NOT NULL,
  `productId` int(11) NOT NULL,
  PRIMARY KEY (`eDetailingMaterialId`),
  UNIQUE KEY `Phys_Prod_Material` (`physicianId`,`productId`,`detailingFileName`),
  KEY `fk_eDetailing_Material_Physicians_Staging1_idx` (`physicianId`),
  KEY `fk_eDetailing_Material_Medical_Fields1_idx` (`medicalFieldId`),
  KEY `fk_eDetailing_Material_Products1_idx` (`productId`),
  CONSTRAINT `fk_eDetailing_Material_Medical_Fields1` FOREIGN KEY (`medicalFieldId`) REFERENCES `medical_fields` (`medicalFieldId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_eDetailing_Material_Physicians_Staging1` FOREIGN KEY (`physicianId`) REFERENCES `physicians_staging` (`physicianId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_eDetailing_Material_Products1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edetailing_material`
--

LOCK TABLES `edetailing_material` WRITE;
/*!40000 ALTER TABLE `edetailing_material` DISABLE KEYS */;
/*!40000 ALTER TABLE `edetailing_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incentives`
--

DROP TABLE IF EXISTS `incentives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incentives` (
  `incentiveId` int(11) NOT NULL AUTO_INCREMENT,
  `amountUsd` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`incentiveId`),
  KEY `fk_Incentives_User_idx` (`userId`),
  CONSTRAINT `fk_Incentives_User` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incentives`
--

LOCK TABLES `incentives` WRITE;
/*!40000 ALTER TABLE `incentives` DISABLE KEYS */;
/*!40000 ALTER TABLE `incentives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `live_meeting_questions`
--

DROP TABLE IF EXISTS `live_meeting_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `live_meeting_questions` (
  `liveMeetingQuestionId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `question` varchar(200) NOT NULL,
  `answer` varchar(500) DEFAULT NULL,
  `answeredByUser` int(11) DEFAULT NULL,
  `importanceIndex` float DEFAULT '1',
  `questionAskedOn` date NOT NULL,
  PRIMARY KEY (`liveMeetingQuestionId`),
  KEY `fk_user_live_meeting_update_1_idx` (`userId`),
  CONSTRAINT `fk_user_live_meeting_update_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `live_meeting_questions`
--

LOCK TABLES `live_meeting_questions` WRITE;
/*!40000 ALTER TABLE `live_meeting_questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `live_meeting_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_fields`
--

DROP TABLE IF EXISTS `medical_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medical_fields` (
  `medicalFieldId` varchar(5) NOT NULL,
  `medicalFIeldName` varchar(20) NOT NULL,
  PRIMARY KEY (`medicalFieldId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_fields`
--

LOCK TABLES `medical_fields` WRITE;
/*!40000 ALTER TABLE `medical_fields` DISABLE KEYS */;
INSERT INTO `medical_fields` VALUES ('DIAB','DIABETES'),('ONC','ONCOLOGY');
/*!40000 ALTER TABLE `medical_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting_experience`
--

DROP TABLE IF EXISTS `meeting_experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meeting_experience` (
  `meetingExperienceId` int(11) NOT NULL AUTO_INCREMENT,
  `isPhysicianEntry` tinyint(4) NOT NULL,
  `isSalesRepEntry` tinyint(4) NOT NULL,
  `status` varchar(45) NOT NULL,
  `likedTheProduct` tinyint(4) NOT NULL,
  `likedPriceAffordability` tinyint(4) NOT NULL,
  `impressiveLessSideEffects` tinyint(4) NOT NULL,
  `likedPresentation` tinyint(4) NOT NULL,
  `salesRepConfidence` tinyint(4) NOT NULL,
  `impressiveCompanyReputation` tinyint(4) NOT NULL,
  `appointmentId` int(11) NOT NULL,
  PRIMARY KEY (`meetingExperienceId`),
  KEY `fk_Meeting_Experience_Appointment1_idx` (`appointmentId`),
  CONSTRAINT `fk_Meeting_Experience_Appointment1` FOREIGN KEY (`appointmentId`) REFERENCES `appointment` (`appointmentId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting_experience`
--

LOCK TABLES `meeting_experience` WRITE;
/*!40000 ALTER TABLE `meeting_experience` DISABLE KEYS */;
/*!40000 ALTER TABLE `meeting_experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting_update`
--

DROP TABLE IF EXISTS `meeting_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meeting_update` (
  `meetingUpdateId` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `status` varchar(45) NOT NULL,
  `isEDetailed` tinyint(4) NOT NULL,
  `physicianId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `medicalFieldId` varchar(5) NOT NULL,
  `appointmentId` int(11) NOT NULL,
  PRIMARY KEY (`meetingUpdateId`),
  UNIQUE KEY `appointmentId_UNIQUE` (`appointmentId`),
  KEY `fk_Meeting_Update_Physicians_Staging1_idx` (`physicianId`),
  KEY `fk_Meeting_Update_Products1_idx` (`productId`),
  KEY `fk_Meeting_Update_Medical_Fields1_idx` (`medicalFieldId`),
  KEY `fk_Meeting_Update_Appointment1_idx` (`appointmentId`),
  CONSTRAINT `fk_Meeting_Update_Appointment1` FOREIGN KEY (`appointmentId`) REFERENCES `appointment` (`appointmentId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Meeting_Update_Medical_Fields1` FOREIGN KEY (`medicalFieldId`) REFERENCES `medical_fields` (`medicalFieldId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Meeting_Update_Physicians_Staging1` FOREIGN KEY (`physicianId`) REFERENCES `physicians_staging` (`physicianId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Meeting_Update_Products1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting_update`
--

LOCK TABLES `meeting_update` WRITE;
/*!40000 ALTER TABLE `meeting_update` DISABLE KEYS */;
/*!40000 ALTER TABLE `meeting_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `notificationId` int(11) NOT NULL AUTO_INCREMENT,
  `notification` varchar(250) NOT NULL,
  `hasRead` tinyint(1) NOT NULL DEFAULT '0',
  `userId` int(11) NOT NULL,
  `notificationCategory` varchar(50) NOT NULL,
  PRIMARY KEY (`notificationId`),
  KEY `fk_Notifications_User1_idx` (`userId`),
  CONSTRAINT `fk_Notifications_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_sample_feedback`
--

DROP TABLE IF EXISTS `patient_sample_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_sample_feedback` (
  `patientSampleFeedbackId` int(11) NOT NULL AUTO_INCREMENT,
  `isMedicineEffective` tinyint(4) NOT NULL,
  `hasSideEffects` tinyint(4) NOT NULL,
  `isAffordable` tinyint(4) NOT NULL,
  `sideEffectsComments` varchar(200) DEFAULT NULL,
  `otherComments` varchar(300) DEFAULT NULL,
  `productId` int(11) NOT NULL,
  PRIMARY KEY (`patientSampleFeedbackId`),
  KEY `fk_Patient_Sample_Feedback_Products1_idx` (`productId`),
  CONSTRAINT `fk_Patient_Sample_Feedback_Products1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_sample_feedback`
--

LOCK TABLES `patient_sample_feedback` WRITE;
/*!40000 ALTER TABLE `patient_sample_feedback` DISABLE KEYS */;
INSERT INTO `patient_sample_feedback` VALUES (1,1,1,0,'Side effect 1','Other comment',1),(2,0,1,1,'Side effect 2','',2),(3,0,0,1,'','Not effective',3),(4,0,1,1,'SIde effect worse','Other comment is',2),(5,1,1,1,'Some side effects','Other comments',1),(6,0,0,1,'Some side effects','Not effective',1),(7,1,1,0,'Not much','Expensive',1),(8,1,0,1,'Not much','No other comments',1),(9,0,0,0,'','Yes',1),(10,1,1,0,'Some','',1),(11,1,1,1,'Some','',1),(12,0,1,0,'Itching','',2),(13,1,0,0,'','Expensive\n',2),(14,0,1,0,'Boyels','',2),(15,1,0,1,'','',2),(16,0,0,0,'','',2),(17,1,1,0,'Something','',2),(18,0,0,0,'','',2),(19,0,1,0,'Some side effects','',2),(20,1,1,1,'SIde','',3),(21,0,1,0,'Side Effect','Not effective',3),(22,0,0,0,'','',3),(23,1,1,0,'Itching','Good product',3),(24,0,1,0,'Serious side effect','good med',3),(25,0,1,0,'has','',4),(26,0,1,0,'Side','other comments',4),(27,1,0,1,'','Comments Comments Comments Comments Comments Comments Comments Comments Comments',4),(28,0,0,1,'','Other comments',4),(29,1,1,0,'Stomachache','',4),(30,1,1,1,'sdasd','sds',4),(31,1,1,1,'dasdDA','zsdaads',1),(32,1,1,0,'itching','very expensive',3);
/*!40000 ALTER TABLE `patient_sample_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physicians_source`
--

DROP TABLE IF EXISTS `physicians_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `physicians_source` (
  `physicianId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `emailId` varchar(45) NOT NULL,
  `contactNumber` varchar(15) NOT NULL,
  `addressLineOne` varchar(100) NOT NULL,
  `addressLineTwo` varchar(100) DEFAULT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `medicalField` varchar(5) NOT NULL,
  `practiceStartDate` date NOT NULL,
  PRIMARY KEY (`physicianId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physicians_source`
--

LOCK TABLES `physicians_source` WRITE;
/*!40000 ALTER TABLE `physicians_source` DISABLE KEYS */;
INSERT INTO `physicians_source` VALUES (1,'Christopher','Nolan','nolan.christopher@biopharma.com','11119999','23 Ridge Drive',NULL,'Florence','South Carolina','11001','ONC','2000-01-01'),(2,'Alfred','Hitchcock','hitchcock.alfred@biopharma.com','11110000','25 Beverly Hill',NULL,'Florence','South Carolina','11001','ONC','2007-01-01'),(3,'Steven','Spielberg','spielberg.steven@biopharma.com','6667777','1-32 Kent Ridge Drive',NULL,'Florence','South Carolina','11003','DIAB','1992-01-01'),(4,'Clint','Eastwood','eastwood.clint@biopharma.com','99992299','2-43 New Ridge Mountain',NULL,'Florence','South Carolina','11001','ONC','2015-04-01'),(5,'Quentin','Tarantino','tarantino.quentin@biopharma.com','66667766','23-103, Normanton Park',NULL,'Florence','South Carolina','11001','ONC','2001-01-01'),(6,'James','Cameroon','cameroon.james@biopharma.com','934902248','block 4, 3-109, Normanton Park',NULL,'Florence','South Carolina','11003','DIAB','2015-02-01');
/*!40000 ALTER TABLE `physicians_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physicians_staging`
--

DROP TABLE IF EXISTS `physicians_staging`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `physicians_staging` (
  `physicianId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `contactNumber` varchar(15) NOT NULL,
  `addressLineOne` varchar(100) NOT NULL,
  `addressLineTwo` varchar(100) DEFAULT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `medicalField` varchar(5) NOT NULL,
  `isNew` tinyint(4) NOT NULL,
  `status` varchar(25) NOT NULL,
  `importanceFactor` float DEFAULT NULL,
  `practiceStartDate` date NOT NULL,
  PRIMARY KEY (`physicianId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physicians_staging`
--

LOCK TABLES `physicians_staging` WRITE;
/*!40000 ALTER TABLE `physicians_staging` DISABLE KEYS */;
INSERT INTO `physicians_staging` VALUES (1,'Christopher','Nolan','salesfast.stm@gmail.com','11119999','23 Ridge Drive',NULL,'Florence','South Carolina','11001','ONC',0,'PRESCRIBING',1.96,'2000-01-01'),(2,'Alfred','Hitchcock','salesfast.stm@gmail.com','11110000','25 Beverly Hill',NULL,'Florence','South Carolina','11001','ONC',0,'PRESCRIBING',1.12,'2007-01-01'),(3,'Steven','Spielberg','salesfast.stm@gmail.com','6667777','1-32 Kent Ridge Drive',NULL,'Florence','South Carolina','11003','DIAB',0,'TO BE DETAILED',2.92,'1992-01-01'),(4,'Clint','Eastwood','salesfast.stm@gmail.com','99992299','2-43 New Ridge Mountain',NULL,'Florence','South Carolina','11001','ONC',1,'LOST',0.13,'2015-04-01'),(5,'Quentin','Tarantino','salesfast.stm@gmail.com','66667766','23-103, Normanton Park',NULL,'Florence','South Carolina','11001','ONC',0,'PRESCRIBING',1.84,'2001-01-01'),(6,'James','Cameroon','salesfast.stm@gmail.com','934902248','block 4, 3-109, Normanton Park',NULL,'Florence','South Carolina','11003','DIAB',1,'TO BE DETAILED',0.15,'2015-02-01');
/*!40000 ALTER TABLE `physicians_staging` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pitches`
--

DROP TABLE IF EXISTS `pitches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pitches` (
  `pitchesId` int(11) NOT NULL AUTO_INCREMENT,
  `appointmentId` int(11) NOT NULL,
  `meetingStatus` varchar(45) NOT NULL,
  `fileName` varchar(100) NOT NULL,
  `pitchScore` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`pitchesId`),
  KEY `fk_Appointment_Pitches_idx` (`appointmentId`),
  CONSTRAINT `fk_Appointment_Pitches` FOREIGN KEY (`appointmentId`) REFERENCES `appointment` (`appointmentId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pitches`
--

LOCK TABLES `pitches` WRITE;
/*!40000 ALTER TABLE `pitches` DISABLE KEYS */;
/*!40000 ALTER TABLE `pitches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `productId` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(45) NOT NULL,
  `releaseDate` varchar(45) NOT NULL,
  `medicalFieldId` varchar(5) NOT NULL,
  PRIMARY KEY (`productId`),
  KEY `fk_Products_Medical_Fields1_idx` (`medicalFieldId`),
  CONSTRAINT `fk_Products_Medical_Fields1` FOREIGN KEY (`medicalFieldId`) REFERENCES `medical_fields` (`medicalFieldId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'D_Med_1','2016-01-12','DIAB'),(2,'D_Med_2','2015-11-13','DIAB'),(3,'O_Med_1','2016-01-30','ONC'),(4,'O_Med_2','2015-11-14','ONC');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) NOT NULL,
  `shortName` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Sales Representative','SalesRep'),(2,'District Manager','DM'),(3,'National Head','NH'),(4,'Physician','PH'),(5,'Patients','PTN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `samples`
--

DROP TABLE IF EXISTS `samples`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `samples` (
  `sampleId` int(11) NOT NULL AUTO_INCREMENT,
  `sampleName` varchar(45) NOT NULL,
  `productName` varchar(45) NOT NULL,
  `medicalFieldName` varchar(20) NOT NULL,
  `productId` int(11) NOT NULL,
  `medicalFieldId` varchar(5) NOT NULL,
  PRIMARY KEY (`sampleId`),
  KEY `fk_Samples_Products1_idx` (`productId`),
  KEY `fk_Samples_Medical_Fields1_idx` (`medicalFieldId`),
  CONSTRAINT `fk_Samples_Medical_Fields1` FOREIGN KEY (`medicalFieldId`) REFERENCES `medical_fields` (`medicalFieldId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Samples_Products1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `samples`
--

LOCK TABLES `samples` WRITE;
/*!40000 ALTER TABLE `samples` DISABLE KEYS */;
/*!40000 ALTER TABLE `samples` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `targets`
--

DROP TABLE IF EXISTS `targets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `targets` (
  `targetId` int(11) NOT NULL AUTO_INCREMENT,
  `noOfSalesCall` int(11) NOT NULL,
  `noOfPrescribing` int(11) NOT NULL,
  `noOfProspecting` int(11) NOT NULL,
  `territorySales` int(11) DEFAULT NULL COMMENT 'Territory sales in is USD',
  `districtSales` int(11) DEFAULT NULL COMMENT 'District sales in is USD',
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`targetId`),
  KEY `fk_Targets_User1_idx` (`userId`),
  CONSTRAINT `fk_Targets_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='				';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `targets`
--

LOCK TABLES `targets` WRITE;
/*!40000 ALTER TABLE `targets` DISABLE KEYS */;
/*!40000 ALTER TABLE `targets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `territories`
--

DROP TABLE IF EXISTS `territories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `territories` (
  `territoryID` int(11) NOT NULL AUTO_INCREMENT,
  `territoryName` varchar(45) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `userId` int(11) NOT NULL,
  `districtId` int(11) NOT NULL,
  PRIMARY KEY (`territoryID`),
  KEY `fk_Territories_User1_idx` (`userId`),
  KEY `fk_Territories_Districts1_idx` (`districtId`),
  CONSTRAINT `fk_Territories_Districts1` FOREIGN KEY (`districtId`) REFERENCES `districts` (`districtId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Territories_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `territories`
--

LOCK TABLES `territories` WRITE;
/*!40000 ALTER TABLE `territories` DISABLE KEYS */;
INSERT INTO `territories` VALUES (1,'Terr_A','11001',1,1),(2,'Terr_C','11003',2,1),(3,'Terr_B','11006',1,1),(4,'Terr_D','11004',3,1),(5,'Terr_E','11007',2,1),(6,'Terr_F','11009',3,1),(7,'Terr_G','11010',1,1);
/*!40000 ALTER TABLE `territories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_material`
--

DROP TABLE IF EXISTS `training_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_material` (
  `trainingMaterialId` int(11) NOT NULL AUTO_INCREMENT,
  `trainingMaterialUrl` varchar(150) NOT NULL,
  `userId` int(11) NOT NULL,
  `medicalFieldId` varchar(5) NOT NULL,
  `productId` int(11) NOT NULL,
  PRIMARY KEY (`trainingMaterialId`),
  KEY `fk_Training_Material_User1_idx` (`userId`),
  KEY `fk_Training_Material_Medical_Fields1_idx` (`medicalFieldId`),
  KEY `fk_Training_Material_Products1_idx` (`productId`),
  CONSTRAINT `fk_Training_Material_Medical_Fields1` FOREIGN KEY (`medicalFieldId`) REFERENCES `medical_fields` (`medicalFieldId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Training_Material_Products1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Training_Material_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_material`
--

LOCK TABLES `training_material` WRITE;
/*!40000 ALTER TABLE `training_material` DISABLE KEYS */;
INSERT INTO `training_material` VALUES (1,'O_Med_1.pdf',1,'ONC',3),(2,'O_Med_2.pdf',1,'ONC',4),(3,'D_Med_1.pdf',2,'DIAB',1),(4,'D_Med_2.pdf',2,'DIAB',2);
/*!40000 ALTER TABLE `training_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `email` varchar(45) NOT NULL,
  `contactNumber` varchar(15) NOT NULL,
  `addressLineOne` varchar(100) NOT NULL,
  `addressLineTwo` varchar(100) DEFAULT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='				';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Johny','Depp','salesfast.stm@gmail.com','99009922','22-103, Normanton Park',NULL,'Florence','South Carolina','11001','2014-01-01','9999-01-01'),(2,'Chritain','Bale','salesfast.stm@gmail.com','99223311','104-Block 5, Beverly hills',NULL,'Florence','South Carolina','11003','2014-01-01','9999-01-01'),(3,'David','Schwimmer','salesfast.stm@gmail.com','77664455','102-Block 3, Beverly hills',NULL,'Florence','South Carolina','11006','2014-01-01','9999-01-01'),(4,'Morgan','Freeman','salesfast.stm@gmail.com','00992900','18-104, Clementi Heights',NULL,'Florence','South Carolina','11006','2013-01-01','9999-01-01');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `userAccountId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`userAccountId`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  KEY `fk_User_Account_User1_idx` (`userId`),
  CONSTRAINT `fk_User_Account_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1,'christain','balechristain',2),(2,'johny','depjohny',1),(3,'david','schwimmerdavid',3),(4,'morgan','freemanmorgan',4);
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_to_role`
--

DROP TABLE IF EXISTS `user_to_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_to_role` (
  `userRoleId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`userRoleId`),
  KEY `fk_User_To_Role_User1_idx` (`userId`),
  KEY `fk_User_To_Role_Roles1_idx` (`roleId`),
  CONSTRAINT `fk_User_To_Role_Roles1` FOREIGN KEY (`roleId`) REFERENCES `roles` (`roleId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_To_Role_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_to_role`
--

LOCK TABLES `user_to_role` WRITE;
/*!40000 ALTER TABLE `user_to_role` DISABLE KEYS */;
INSERT INTO `user_to_role` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,2);
/*!40000 ALTER TABLE `user_to_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_to_sample`
--

DROP TABLE IF EXISTS `user_to_sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_to_sample` (
  `userSameplId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `sampleId` int(11) NOT NULL,
  PRIMARY KEY (`userSameplId`),
  KEY `fk_User_To_Sample_User1_idx` (`userId`),
  KEY `fk_User_To_Sample_Samples1_idx` (`sampleId`),
  CONSTRAINT `fk_User_To_Sample_Samples1` FOREIGN KEY (`sampleId`) REFERENCES `samples` (`sampleId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_To_Sample_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='								';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_to_sample`
--

LOCK TABLES `user_to_sample` WRITE;
/*!40000 ALTER TABLE `user_to_sample` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_to_sample` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-04 15:41:17
