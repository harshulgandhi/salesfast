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
) ENGINE=InnoDB AUTO_INCREMENT=9010 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alignments`
--

LOCK TABLES `alignments` WRITE;
/*!40000 ALTER TABLE `alignments` DISABLE KEYS */;
INSERT INTO `alignments` VALUES (8,1,1,1,1,'11001',4),(9,2,1,1,1,'11001',3),(10,2,1,1,1,'11001',4),(11,3,2,2,1,'11003',1),(12,3,2,2,1,'11003',2),(13,4,1,1,1,'11001',3),(14,4,1,1,1,'11001',4),(15,5,1,1,1,'11001',3),(16,5,1,1,1,'11001',4),(17,6,2,2,1,'11003',1),(18,6,2,2,1,'11003',2),(2487,17,13,4,1,'11004',1),(2488,17,13,4,1,'11004',2),(2489,18,12,4,1,'11004',3),(2490,18,12,4,1,'11004',4),(2609,16,1,1,1,'11001',3),(2610,16,1,1,1,'11001',4),(2615,19,1,1,1,'11001',3),(2616,19,1,1,1,'11001',4),(2659,1,1,1,1,'11001',11),(2662,2,1,1,1,'11001',11),(2667,4,1,1,1,'11001',11),(2670,5,1,1,1,'11001',11),(2675,16,1,1,1,'11001',11),(2680,18,12,4,1,'11004',11),(2683,19,1,1,1,'11001',11),(2692,3,2,2,1,'11003',12),(2701,6,2,2,1,'11003',12),(2707,17,13,4,1,'11004',12),(2717,1,1,1,1,'11001',13),(2721,2,1,1,1,'11001',13),(2728,4,1,1,1,'11001',13),(2732,5,1,1,1,'11001',13),(2739,16,1,1,1,'11001',13),(2746,18,12,4,1,'11004',13),(2750,19,1,1,1,'11001',13),(2755,1,1,1,1,'11001',14),(2760,2,1,1,1,'11001',14),(2768,4,1,1,1,'11001',14),(2773,5,1,1,1,'11001',14),(2781,16,1,1,1,'11001',14),(2789,18,12,4,1,'11004',14),(6970,19,1,1,1,'11001',14),(7284,1,1,1,1,'11001',33),(7290,2,1,1,1,'11001',33),(7299,4,1,1,1,'11001',33),(7305,5,1,1,1,'11001',33),(7314,16,1,1,1,'11001',33),(7323,18,12,4,1,'11004',33),(7329,19,1,1,1,'11001',33),(7585,1,1,1,1,'11001',3),(7744,1,1,1,1,'11001',34),(7751,2,1,1,1,'11001',34),(7761,4,1,1,1,'11001',34),(7768,5,1,1,1,'11001',34),(7778,16,1,1,1,'11001',34),(7788,18,12,4,1,'11004',34),(7795,19,1,1,1,'11001',34),(8107,1,1,1,1,'11001',37),(8115,2,1,1,1,'11001',37),(8126,4,1,1,1,'11001',37),(8134,5,1,1,1,'11001',37),(8145,16,1,1,1,'11001',37),(8156,18,12,4,1,'11004',37),(8164,19,1,1,1,'11001',37);
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
  `startTime` time DEFAULT NULL,
  `endTime` time DEFAULT NULL,
  `date` date DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=281 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (232,'11:00:00','11:30:00','2016-04-04',3,2,1,'CONFIRMED','11003','','',1,1,1,0),(233,'11:00:00','11:30:00','2016-04-04',3,2,2,'CONFIRMED','11003','','',1,1,1,0),(238,'11:00:00','11:30:00','2016-04-04',3,2,12,'CONFIRMED','11003','','',1,1,1,0),(239,'14:00:00','14:30:00','2016-04-04',6,2,1,'CONFIRMED','11003','','',1,1,1,1),(240,'14:00:00','14:30:00','2016-04-04',6,2,2,'CONFIRMED','11003','','',1,1,1,1),(241,'14:00:00','14:30:00','2016-04-04',6,2,12,'CONFIRMED','11003','','',1,1,1,1),(242,'11:00:00','11:30:00','2016-04-04',17,13,2,'CONFIRMED','11004','','',1,1,1,0),(243,'11:00:00','11:30:00','2016-04-04',17,13,12,'CONFIRMED','11004','','',1,1,1,0),(246,'11:00:00','11:30:00','2016-04-04',1,1,11,'CONFIRMED','11001','','',1,1,1,0),(247,'14:00:00','14:30:00','2016-04-04',5,1,11,'CONFIRMED','11001','','',1,1,1,1),(248,'16:00:00','16:30:00','2016-04-04',2,1,11,'CONFIRMED','11001','','',1,1,1,1),(249,'12:00:00','12:45:00','2016-04-04',1,1,13,'CONFIRMED','11001','','',1,1,1,1),(250,'12:00:00','12:45:00','2016-04-04',1,1,14,'CONFIRMED','11001','','',1,1,1,1),(251,'19:00:00','19:30:00','2016-04-04',5,1,13,'CONFIRMED','11001','','',1,1,1,1),(252,'19:00:00','19:30:00','2016-04-04',5,1,14,'CONFIRMED','11001','','',1,1,1,1),(255,'12:00:00','12:30:00','2016-04-05',19,1,3,'CONFIRMED','11001','','',1,1,1,0),(256,'12:00:00','12:30:00','2016-04-05',19,1,4,'CONFIRMED','11001','','',1,1,1,0),(257,'16:00:00','16:30:00','2016-04-05',16,1,4,'CONFIRMED','11001','','',1,1,1,0),(261,'15:00:00','15:30:00','2016-04-06',18,12,3,'CANCELLED','11004','not available, out of town','',0,0,0,0),(262,'11:00:00','11:30:00','2016-04-11',16,1,14,'CONFIRMED','11001','','',1,1,1,1),(263,'14:00:00','14:45:00','2016-04-11',4,1,13,'CONFIRMED','11001','','',1,1,1,1),(264,'16:08:00','17:15:00','2016-04-11',4,1,14,'CONFIRMED','11001','','',1,1,1,0),(265,'11:00:00','12:00:00','2016-04-12',16,1,3,'CONFIRMED','11001','','',1,1,1,0),(266,'13:03:00','13:45:00','2016-04-12',16,1,13,'CONFIRMED','11001','','',1,1,1,0),(267,'17:00:00','17:40:00','2016-04-12',4,1,11,'CONFIRMED','11001','','',1,1,1,1),(268,'14:00:00','14:30:00','2016-04-08',2,1,3,'CONFIRMED','11001','','',1,1,1,0),(269,'11:00:00','11:30:00','2016-04-09',19,1,14,'CONFIRMED','11001','','',1,1,0,1),(270,'11:00:00','11:30:00','2016-04-09',19,1,13,'CONFIRMED','11001','','',1,1,0,0),(271,'13:00:00','13:30:00','2016-04-09',2,1,14,'CONFIRMED','11001','','',1,1,1,0),(272,'16:00:00','16:45:00','2016-04-09',16,1,11,'CONFIRMED','11001','','',1,1,1,0),(273,'13:00:00','13:30:00','2016-04-13',1,1,4,'CONFIRMED','11001','','',1,1,1,1),(274,'11:00:00','11:45:00','2016-04-13',1,1,3,'CONFIRMED','11001','','',1,1,0,1),(275,'14:00:00','14:50:00','2016-04-13',5,1,3,'CONFIRMED','11001','','',1,1,0,1),(276,'12:00:00','13:00:00','2016-04-14',2,1,4,'CONFIRMED','11001','','',0,0,0,0),(277,'16:00:00','16:30:00','2016-04-14',19,1,11,'CONFIRMED','11001','','',0,0,0,0),(278,'11:45:00','12:30:00','2016-04-15',4,1,3,'CONFIRMED','11001','','',0,0,0,0),(279,'15:00:00','15:50:00','2016-04-15',4,1,4,'CONFIRMED','11001','','',0,0,0,0),(280,'12:00:00','12:50:00','2016-04-16',5,1,4,'CONFIRMED','11001','','',0,0,0,0);
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
-- Table structure for table `edetailing_credentials`
--

DROP TABLE IF EXISTS `edetailing_credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `edetailing_credentials` (
  `eDetailingCredentialId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `physicianId` int(11) NOT NULL,
  PRIMARY KEY (`eDetailingCredentialId`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  KEY `fk_eDetailing_Credentials_Physicians_Staging1_idx` (`physicianId`),
  CONSTRAINT `fk_eDetailing_Credentials_Physicians_Staging1` FOREIGN KEY (`physicianId`) REFERENCES `physicians_staging` (`physicianId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edetailing_credentials`
--

LOCK TABLES `edetailing_credentials` WRITE;
/*!40000 ALTER TABLE `edetailing_credentials` DISABLE KEYS */;
/*!40000 ALTER TABLE `edetailing_credentials` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edetailing_material`
--

LOCK TABLES `edetailing_material` WRITE;
/*!40000 ALTER TABLE `edetailing_material` DISABLE KEYS */;
INSERT INTO `edetailing_material` VALUES (37,'D_Med_1.pdf',3,'DIAB',1),(38,'D_Med_1.pdf',6,'DIAB',1),(39,'D_Med_2.pdf',17,'DIAB',2),(40,'D_Med_3.pdf',17,'DIAB',12),(41,'O_Med_3.pdf',1,'ONC',11),(42,'O_Med_3.pdf',5,'ONC',11),(43,'O_Med_3.pdf',2,'ONC',11),(44,'O_Med_4.pdf',1,'ONC',13),(45,'O_Med_4.pdf',2,'ONC',13),(46,'O_Med_4.pdf',5,'ONC',13),(47,'O_Med_5.pdf',1,'ONC',14),(48,'O_Med_5.pdf',2,'ONC',14),(49,'O_Med_5.pdf',5,'ONC',14),(52,'O_Med_1.pdf',19,'ONC',3),(53,'O_Med_2.pdf',16,'ONC',4),(54,'O_Med_6.pdf',1,'ONC',33),(55,'O_Med_6.pdf',2,'ONC',33),(56,'O_Med_6.pdf',5,'ONC',33),(57,'O_Med_6.pdf',16,'ONC',33),(58,'O_Med_6.pdf',19,'ONC',33),(59,'O_Med_7.pdf',1,'ONC',34),(60,'O_Med_7.pdf',2,'ONC',34),(61,'O_Med_7.pdf',5,'ONC',34),(62,'O_Med_7.pdf',16,'ONC',34),(63,'O_Med_7.pdf',19,'ONC',34),(74,'O_Med_8.pdf',1,'ONC',37),(75,'O_Med_8.pdf',2,'ONC',37),(76,'O_Med_8.pdf',5,'ONC',37),(77,'O_Med_8.pdf',16,'ONC',37),(78,'O_Med_8.pdf',19,'ONC',37),(79,'O_Med_2.pdf',1,'ONC',4),(80,'O_Med_5.pdf',16,'ONC',14),(81,'O_Med_4.pdf',4,'ONC',13),(82,'O_Med_1.pdf',16,'ONC',3),(83,'O_Med_3.pdf',4,'ONC',11),(84,'O_Med_1.pdf',5,'ONC',3);
/*!40000 ALTER TABLE `edetailing_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edetailingcredentials`
--

DROP TABLE IF EXISTS `edetailingcredentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `edetailingcredentials` (
  `eDetailingCredentialId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `physicianId` int(11) NOT NULL,
  PRIMARY KEY (`eDetailingCredentialId`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  KEY `fk_eDetailingCredentials_Physicians_Source1_idx` (`physicianId`),
  CONSTRAINT `fk_eDetailingCredentials_Physicians_Source1` FOREIGN KEY (`physicianId`) REFERENCES `physicians_source` (`physicianId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edetailingcredentials`
--

LOCK TABLES `edetailingcredentials` WRITE;
/*!40000 ALTER TABLE `edetailingcredentials` DISABLE KEYS */;
/*!40000 ALTER TABLE `edetailingcredentials` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `live_meeting_questions`
--

LOCK TABLES `live_meeting_questions` WRITE;
/*!40000 ALTER TABLE `live_meeting_questions` DISABLE KEYS */;
INSERT INTO `live_meeting_questions` VALUES (12,1,'Q1-This the question asked by physician during the meeting. Johny Depp needs an answer.',NULL,NULL,1,'2016-04-04'),(13,1,'Q2 - Physician asked me question related to side effects of O_Med_1. I dont know the answer.','O_Med_1 has this side effect but it is very mild. Hope this helps.',2,3,'2016-04-04'),(14,1,'Q3-This the question asked by physician during the meeting about O_Med_4. Why is the expiry so less. Is it not safe?','Because it is a highly effective drug. Had to be made this way.',4,3,'2016-04-04'),(15,1,'Q4 - How does this medicine behave for a patient having cardiac problems ? ','This medicine has no side effects for cardiac patients.',2,3,'2016-04-04'),(16,1,'Possible side effects for patients having very sensitive skin?','For patients having skin sensitivity towards sun burns, this medicine might aggravate the problem. Other wise this works fine.',4,3,'2016-04-04'),(17,3,'Is it ok to give extra doze of O_Med_4 to Cardiac adult patients?',NULL,NULL,1,'2016-04-04'),(18,3,'O_Med_1 is way too expensive as compared to other drugs. What are its benefits as compared to competetors. ','Its stronger as well. Needs less number dose and has lesser recovery time.',12,2,'2016-04-04'),(19,1,'Q9 physician asked me question related to side',NULL,NULL,1,'2016-04-13'),(20,1,'Q3-This the question asked by physician during the meeting.',NULL,NULL,1,'2016-04-14');
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
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting_experience`
--

LOCK TABLES `meeting_experience` WRITE;
/*!40000 ALTER TABLE `meeting_experience` DISABLE KEYS */;
INSERT INTO `meeting_experience` VALUES (42,0,1,'PRESCRIBING',1,1,1,0,0,1,232),(43,0,1,'LOST',1,0,1,0,1,1,233),(45,0,1,'PRESCRIBING',1,0,0,1,1,1,238),(46,0,1,'PRESCRIBING',0,1,0,1,1,1,239),(47,0,1,'LOST',1,0,1,1,1,0,240),(48,0,1,'LOST',0,0,0,1,0,1,241),(49,1,0,'PRESCRIBING',1,0,1,0,1,1,232),(50,1,0,'PRESCRIBING',1,1,0,0,0,1,238),(51,1,0,'PRESCRIBING',1,0,0,0,1,1,239),(52,0,1,'PRESCRIBING',1,1,1,0,0,0,242),(53,1,0,'PRESCRIBING',0,1,0,0,1,1,242),(54,0,1,'PRESCRIBING',1,0,0,1,1,1,243),(55,1,0,'PRESCRIBING',1,0,1,0,1,1,243),(56,0,1,'PRESCRIBING',0,1,0,1,1,1,246),(57,0,1,'PRESCRIBING',0,1,0,1,1,1,247),(58,0,1,'PRESCRIBING',1,1,0,1,0,1,248),(59,0,1,'LOST',1,0,1,1,0,0,249),(60,0,1,'PRESCRIBING',1,1,0,0,0,1,250),(61,0,1,'PRESCRIBING',0,1,0,0,1,1,252),(62,0,1,'LOST',0,1,0,0,1,1,251),(63,1,0,'PRESCRIBING',1,0,1,0,0,1,248),(64,1,0,'PRESCRIBING',1,0,1,0,0,1,247),(65,1,0,'PRESCRIBING',0,0,0,1,1,0,252),(66,1,0,'PRESCRIBING',1,1,1,0,0,0,246),(67,1,0,'PRESCRIBING',1,1,0,1,1,1,250),(68,0,1,'LOST',1,1,0,0,1,0,256),(69,0,1,'PRESCRIBING',0,1,0,1,0,1,255),(70,1,0,'PRESCRIBING',1,1,0,1,1,0,255),(71,0,1,'PRESCRIBING',1,0,1,0,1,0,257),(72,0,1,'LOST',1,1,0,0,1,0,268),(73,1,0,'LOST',1,1,0,1,0,1,268),(74,1,0,'LOST',1,0,1,0,1,0,233),(75,1,0,'LOST',1,1,0,0,1,1,240),(76,1,0,'LOST',1,1,0,0,0,1,241),(77,1,0,'LOST',1,1,0,1,0,0,249),(78,1,0,'LOST',1,0,1,0,1,0,251),(79,1,0,'LOST',1,0,1,0,1,1,256),(80,1,0,'PRESCRIBING',1,0,1,1,1,0,257),(81,0,1,'LOST',1,0,1,1,1,1,269),(82,0,1,'LOST',1,0,1,1,1,1,270),(83,0,1,'LOST',1,1,0,1,1,1,271),(84,1,0,'LOST',1,0,1,1,0,1,271),(85,0,1,'LOST',0,1,1,1,1,1,272),(86,1,0,'LOST',1,0,0,1,0,1,272),(87,0,1,'PRESCRIBING',0,1,0,1,1,1,273),(88,1,0,'PRESCRIBING',1,1,0,1,0,1,273),(89,0,1,'PRESCRIBING',0,0,0,1,1,1,262),(90,0,1,'PRESCRIBING',1,0,1,1,0,1,263),(91,0,1,'LOST',1,1,0,1,0,1,264),(92,0,1,'PRESCRIBING',1,1,0,0,1,1,265),(93,0,1,'LOST',1,1,0,1,0,1,266),(94,0,1,'PRESCRIBING',1,1,1,1,1,1,267),(95,1,0,'PRESCRIBING',1,0,1,1,0,1,265),(96,1,0,'LOST',1,0,0,1,1,1,266),(97,1,0,'PRESCRIBING',1,1,1,1,1,1,262),(98,1,0,'PRESCRIBING',1,1,1,1,1,0,267),(99,1,0,'PRESCRIBING',1,1,0,1,1,1,263),(100,1,0,'LOST',1,1,1,0,0,1,264),(101,0,1,'LOST',1,0,1,0,1,1,274),(102,0,1,'PRESCRIBING',1,1,1,1,0,1,275);
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
  `isExpensive` tinyint(4) DEFAULT NULL,
  `hasSideEffects` tinyint(4) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting_update`
--

LOCK TABLES `meeting_update` WRITE;
/*!40000 ALTER TABLE `meeting_update` DISABLE KEYS */;
INSERT INTO `meeting_update` VALUES (36,'2016-04-04','PRESCRIBING',1,3,1,'DIAB',232,NULL,NULL),(37,'2016-04-04','LOST',0,3,2,'DIAB',233,1,0),(39,'2016-04-04','PRESCRIBING',0,3,12,'DIAB',238,NULL,NULL),(40,'2016-04-04','PRESCRIBING',1,6,1,'DIAB',239,NULL,NULL),(41,'2016-04-04','LOST',0,6,2,'DIAB',240,0,1),(42,'2016-04-04','LOST',0,6,12,'DIAB',241,0,1),(43,'2016-04-04','PRESCRIBING',1,17,2,'DIAB',242,NULL,NULL),(44,'2016-04-04','PRESCRIBING',1,17,12,'DIAB',243,NULL,NULL),(45,'2016-04-04','PRESCRIBING',1,1,11,'ONC',246,NULL,NULL),(46,'2016-04-04','PRESCRIBING',1,5,11,'ONC',247,NULL,NULL),(47,'2016-04-04','PRESCRIBING',1,2,11,'ONC',248,NULL,NULL),(48,'2016-04-04','LOST',1,1,13,'ONC',249,0,1),(49,'2016-04-04','PRESCRIBING',1,1,14,'ONC',250,NULL,NULL),(50,'2016-04-04','PRESCRIBING',1,5,14,'ONC',252,NULL,NULL),(51,'2016-04-04','LOST',1,5,13,'ONC',251,1,0),(52,'2016-04-05','LOST',1,19,4,'ONC',256,0,1),(53,'2016-04-05','PRESCRIBING',1,19,3,'ONC',255,NULL,NULL),(54,'2016-04-05','PRESCRIBING',1,16,4,'ONC',257,NULL,NULL),(55,'2016-04-08','LOST',1,2,3,'ONC',268,1,0),(56,'2016-04-09','LOST',1,19,14,'ONC',269,1,0),(57,'2016-04-09','LOST',1,19,13,'ONC',270,0,0),(58,'2016-04-09','LOST',1,2,14,'ONC',271,1,0),(59,'2016-04-09','LOST',1,16,11,'ONC',272,1,1),(60,'2016-04-13','PRESCRIBING',1,1,4,'ONC',273,NULL,NULL),(61,'2016-04-11','PRESCRIBING',1,16,14,'ONC',262,NULL,NULL),(62,'2016-04-11','PRESCRIBING',1,4,13,'ONC',263,NULL,NULL),(63,'2016-04-11','LOST',1,4,14,'ONC',264,0,0),(64,'2016-04-12','PRESCRIBING',1,16,3,'ONC',265,NULL,NULL),(65,'2016-04-12','LOST',1,16,13,'ONC',266,1,1),(66,'2016-04-12','PRESCRIBING',1,4,11,'ONC',267,NULL,NULL),(67,'2016-04-13','LOST',1,1,3,'ONC',274,1,0),(68,'2016-04-13','PRESCRIBING',1,5,3,'ONC',275,NULL,NULL);
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
  `notification` varchar(500) NOT NULL,
  `hasRead` tinyint(1) NOT NULL DEFAULT '0',
  `userId` int(11) NOT NULL,
  `notificationCategory` varchar(250) NOT NULL,
  PRIMARY KEY (`notificationId`),
  KEY `fk_Notifications_User1_idx` (`userId`),
  CONSTRAINT `fk_Notifications_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=224 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (96,'O_Med_3 was just released. Call the most prospecting physicians who did not agree to prescribe to our last product. Click on \"Detail\" button to know all LOST physicians.',0,3,'NEW PRODUCT LOST PHYSICIAN'),(97,'O_Med_3 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,3,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(98,'O_Med_3 was just released. Call the most prospecting physicians who did not agree to prescribe to our last product. Click on \"Detail\" button to know all LOST physicians.',0,12,'NEW PRODUCT LOST PHYSICIAN'),(99,'O_Med_3 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,12,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(100,'D_Med_3 was just released. Call the most prospecting physicians who did not agree to prescribe to our last product. Click on \"Detail\" button to know all LOST physicians.',0,2,'NEW PRODUCT LOST PHYSICIAN'),(101,'D_Med_3 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,2,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(102,'D_Med_3 was just released. Call the most prospecting physicians who did not agree to prescribe to our last product. Click on \"Detail\" button to know all LOST physicians.',0,13,'NEW PRODUCT LOST PHYSICIAN'),(103,'D_Med_3 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,13,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(104,'We just release a new product O_Med_4. Click on \"Detail\" to check out your e-detailing page for more details.',0,17,'NEW PRODUCT TO PHYS'),(105,'We just release a new product O_Med_4. Click on \"Detail\" to check out your e-detailing page for more details.',0,19,'NEW PRODUCT TO PHYS'),(106,'We just release a new product O_Med_4. Click on \"Detail\" to check out your e-detailing page for more details.',0,18,'NEW PRODUCT TO PHYS'),(109,'O_Med_4 was just released. Call the most prospecting physicians who did not agree to prescribe to our last product. Click on \"Detail\" button to know all LOST physicians.',0,12,'NEW PRODUCT LOST PHYSICIAN'),(110,'O_Med_4 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,12,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(111,'We just release a new product O_Med_5. Click on \"Detail\" to check out your e-detailing page for more details.',0,17,'NEW PRODUCT TO PHYS'),(112,'We just release a new product O_Med_5. Click on \"Detail\" to check out your e-detailing page for more details.',0,19,'NEW PRODUCT TO PHYS'),(113,'We just release a new product O_Med_5. Click on \"Detail\" to check out your e-detailing page for more details.',0,18,'NEW PRODUCT TO PHYS'),(116,'O_Med_5 was just released. Call the most prospecting physicians who did not agree to prescribe to our last product. Click on \"Detail\" button to know all LOST physicians.',0,12,'NEW PRODUCT LOST PHYSICIAN'),(117,'O_Med_5 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,12,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(118,'Sales Representative Johny Depp has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(119,'Sales Representative Johny Depp has a question that needs answering.',0,3,'LIVE MEETING QUESTION'),(120,'Sales Representative Johny Depp has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(121,'Sales Representative Johny Depp has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(122,'Sales Representative Johny Depp has a question that needs answering.',0,13,'LIVE MEETING QUESTION'),(123,'Sales Representative Johny Depp has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(124,'Sales Representative Johny Depp has a question that needs answering.',0,3,'LIVE MEETING QUESTION'),(125,'Sales Representative Johny Depp has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(126,'Sales Representative Johny Depp has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(127,'Sales Representative Johny Depp has a question that needs answering.',0,13,'LIVE MEETING QUESTION'),(128,'Sales Representative Johny Depp has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(129,'Sales Representative Johny Depp has a question that needs answering.',0,3,'LIVE MEETING QUESTION'),(130,'Sales Representative Johny Depp has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(131,'Sales Representative Johny Depp has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(132,'Sales Representative Johny Depp has a question that needs answering.',0,13,'LIVE MEETING QUESTION'),(133,'Sales Representative Johny Depp has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(134,'Sales Representative Johny Depp has a question that needs answering.',0,3,'LIVE MEETING QUESTION'),(135,'Sales Representative Johny Depp has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(136,'Sales Representative Johny Depp has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(137,'Sales Representative Johny Depp has a question that needs answering.',0,13,'LIVE MEETING QUESTION'),(138,'Sales Representative Johny Depp has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(139,'Sales Representative Johny Depp has a question that needs answering.',0,3,'LIVE MEETING QUESTION'),(140,'Sales Representative Johny Depp has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(141,'Sales Representative Johny Depp has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(142,'Sales Representative Johny Depp has a question that needs answering.',0,13,'LIVE MEETING QUESTION'),(148,'Sales Representative David Schwimmer has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(149,'Sales Representative David Schwimmer has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(150,'Sales Representative David Schwimmer has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(151,'Sales Representative David Schwimmer has a question that needs answering.',0,13,'LIVE MEETING QUESTION'),(153,'Sales Representative David Schwimmer has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(154,'Sales Representative David Schwimmer has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(155,'Sales Representative David Schwimmer has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(156,'Sales Representative David Schwimmer has a question that needs answering.',0,13,'LIVE MEETING QUESTION'),(157,'Mr. Mathew Perry just answered a question you asked during one of your meetings with a physician.',0,3,'QUESTION WAS ANSWERED'),(158,'Dr. Peter Jackson has cancelled the appointment.',0,12,'CANCELLED APPOINTMENTS BY PHYS'),(159,'We just release a new product O_Med_6. Click on \"Detail\" to check out your e-detailing page for more details.',0,17,'NEW PRODUCT TO PHYS'),(160,'We just release a new product O_Med_6. Click on \"Detail\" to check out your e-detailing page for more details.',0,19,'NEW PRODUCT TO PHYS'),(161,'We just release a new product O_Med_6. Click on \"Detail\" to check out your e-detailing page for more details.',0,18,'NEW PRODUCT TO PHYS'),(162,'We just release a new product O_Med_6. Click on \"Detail\" to check out your e-detailing page for more details.',0,21,'NEW PRODUCT TO PHYS'),(163,'We just release a new product O_Med_6. Click on \"Detail\" to check out your e-detailing page for more details.',0,20,'NEW PRODUCT TO PHYS'),(167,'O_Med_6 was just released. Call the most prospecting physicians who did not agree to prescribe to our last product. Click on \"Detail\" button to know all LOST physicians.',0,12,'NEW PRODUCT LOST PHYSICIAN'),(168,'O_Med_6 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,12,'NEW PRODUCT NOT INTERESTED PHYSICIAN'),(169,'O_Med_6 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,12,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(170,'We just release a new product O_Med_7. Click on \"Detail\" to check out your e-detailing page for more details.',0,17,'NEW PRODUCT TO PHYS'),(171,'We just release a new product O_Med_7. Click on \"Detail\" to check out your e-detailing page for more details.',0,19,'NEW PRODUCT TO PHYS'),(172,'We just release a new product O_Med_7. Click on \"Detail\" to check out your e-detailing page for more details.',0,18,'NEW PRODUCT TO PHYS'),(173,'We just release a new product O_Med_7. Click on \"Detail\" to check out your e-detailing page for more details.',0,21,'NEW PRODUCT TO PHYS'),(174,'We just release a new product O_Med_7. Click on \"Detail\" to check out your e-detailing page for more details.',0,20,'NEW PRODUCT TO PHYS'),(178,'O_Med_7 was just released. Call the most prospecting physicians who did not agree to prescribe to our last product. Click on \"Detail\" button to know all LOST physicians.',0,12,'NEW PRODUCT LOST PHYSICIAN'),(179,'O_Med_7 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,12,'NEW PRODUCT NOT INTERESTED PHYSICIAN'),(180,'O_Med_7 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,12,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(199,'We just release a new product O_Med_8. Click on \"Detail\" to check out your e-detailing page for more details.',0,17,'NEW PRODUCT TO PHYS'),(200,'We just release a new product O_Med_8. Click on \"Detail\" to check out your e-detailing page for more details.',0,19,'NEW PRODUCT TO PHYS'),(201,'We just release a new product O_Med_8. Click on \"Detail\" to check out your e-detailing page for more details.',0,18,'NEW PRODUCT TO PHYS'),(202,'We just release a new product O_Med_8. Click on \"Detail\" to check out your e-detailing page for more details.',0,21,'NEW PRODUCT TO PHYS'),(203,'We just release a new product O_Med_8. Click on \"Detail\" to check out your e-detailing page for more details.',0,20,'NEW PRODUCT TO PHYS'),(204,'[NEW PRODUCT] O_Med_8 was just released which is an improved version of O_Med_3. Call the most prospecting physicians who found medicine O_Med_3 too expensive and did not agree to prescribe the same. Click on \"Detail\" button to access list of such physicians.',0,1,'IMPROVED PRODUCT LOST PHYSICIAN MORE AFFORDABLE O_Med_8'),(205,'[NEW PRODUCT] O_Med_8 was just released which is an improved version of O_Med_3. Call the most prospecting physicians who found medicine O_Med_3 having too many side effects and did not agree to prescribe the same. Click on \"Detail\" button to access list of such physicians.',0,1,'IMPROVED PRODUCT LOST PHYSICIAN LESS SIDE EFFECTS O_Med_8'),(206,'[NEW PRODUCT] O_Med_8 was just released which is an improved version of O_Med_3. Call the most prospecting physicians who found medicine O_Med_3 too expensive and having too many side effects, and did not agree to prescribe the same. Click on \"Detail\" button to access list of such physicians physicians.',0,1,'IMPROVED PRODUCT LOST PHYSICIAN MORE AFFORDABLE LESS SIDE EFFECTS O_Med_8'),(207,'O_Med_8 was just released. Call the most prospecting physicians who was not interested in meeting for last product. Click on \"Detail\" button to know all NOT INTERESTED physicians.',0,1,'NEW PRODUCT NOT INTERESTED PHYSICIAN O_Med_8'),(208,'O_Med_8 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,1,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(209,'[NEW PRODUCT] O_Med_8 was just released which is an improved version of O_Med_3. Call the most prospecting physicians who found medicine O_Med_3 too expensive and did not agree to prescribe the same. Click on \"Detail\" button to access list of such physicians.',0,12,'IMPROVED PRODUCT LOST PHYSICIAN MORE AFFORDABLE O_Med_8'),(210,'[NEW PRODUCT] O_Med_8 was just released which is an improved version of O_Med_3. Call the most prospecting physicians who found medicine O_Med_3 having too many side effects and did not agree to prescribe the same. Click on \"Detail\" button to access list of such physicians.',0,12,'IMPROVED PRODUCT LOST PHYSICIAN LESS SIDE EFFECTS O_Med_8'),(211,'[NEW PRODUCT] O_Med_8 was just released which is an improved version of O_Med_3. Call the most prospecting physicians who found medicine O_Med_3 too expensive and having too many side effects, and did not agree to prescribe the same. Click on \"Detail\" button to access list of such physicians physicians.',0,12,'IMPROVED PRODUCT LOST PHYSICIAN MORE AFFORDABLE LESS SIDE EFFECTS O_Med_8'),(212,'O_Med_8 was just released. Call the most prospecting physicians who was not interested in meeting for last product. Click on \"Detail\" button to know all NOT INTERESTED physicians.',0,12,'NEW PRODUCT NOT INTERESTED PHYSICIAN O_Med_8'),(213,'O_Med_8 was just released. Call the physicians who are already prescribing some of BioPharma\'s medicines  from this medical field. Click on \"Detail\" button to know all PRESCRIBING physicians.',0,12,'NEW PRODUCT PRESCRIBING PHYSICIAN'),(214,'Sales Representative Johny Depp has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(215,'Sales Representative Johny Depp has a question that needs answering.',0,3,'LIVE MEETING QUESTION'),(216,'Sales Representative Johny Depp has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(217,'Sales Representative Johny Depp has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(218,'Sales Representative Johny Depp has a question that needs answering.',0,13,'LIVE MEETING QUESTION'),(219,'Sales Representative Johny Depp has a question that needs answering.',0,2,'LIVE MEETING QUESTION'),(220,'Sales Representative Johny Depp has a question that needs answering.',0,3,'LIVE MEETING QUESTION'),(221,'Sales Representative Johny Depp has a question that needs answering.',0,4,'LIVE MEETING QUESTION'),(222,'Sales Representative Johny Depp has a question that needs answering.',0,12,'LIVE MEETING QUESTION'),(223,'Sales Representative Johny Depp has a question that needs answering.',0,13,'LIVE MEETING QUESTION');
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
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_sample_feedback`
--

LOCK TABLES `patient_sample_feedback` WRITE;
/*!40000 ALTER TABLE `patient_sample_feedback` DISABLE KEYS */;
INSERT INTO `patient_sample_feedback` VALUES (1,1,1,0,'Skinproblems','Other comment',1),(2,0,1,1,'Side effect 2','',2),(3,0,0,1,'','Not effective',3),(4,0,1,1,'SIde effect worse','Other comment is',2),(5,1,1,1,'Skinproblems','Other comments',1),(6,0,0,1,'Some side effects','Not effective',1),(7,1,1,0,'sore-throat','Expensive',1),(8,1,0,1,'sorethroat','No other comments',1),(9,0,0,0,'sore-throat','Yes',1),(10,1,1,0,'sore-throat','',1),(11,1,1,1,'Itching','',1),(12,0,1,0,'Itching','',2),(13,1,0,0,'','Expensive\n',2),(14,0,1,0,'Boyels','',2),(15,1,0,1,'','',2),(16,0,0,0,'','',2),(17,1,1,0,'Something','',2),(18,0,0,0,'','',2),(19,0,1,0,'Some side effects','',2),(20,1,1,1,'itching','',3),(21,0,1,0,'headache','Not effective',3),(22,0,0,0,'stomach-ache','',3),(23,1,1,0,'stomach-ache','Good product',3),(24,0,1,0,'extreme headache','good med',3),(25,0,1,0,NULL,'',4),(26,0,1,0,'Side','other comments',4),(27,1,0,1,'','Comments Comments Comments Comments Comments Comments Comments Comments Comments',4),(28,0,0,1,'','Other comments',4),(29,1,1,0,'Stomachache','',4),(30,1,1,1,'sdasd','sds',4),(31,1,1,1,'nausea','zsdaads',1),(32,1,1,0,'itching','very expensive',3),(33,1,0,0,'nausea','Very expensive',1),(34,0,1,1,'Skin problems','',1),(35,1,1,1,'sore throat','',1),(36,0,0,0,'nausea','Expensive',1),(37,1,0,1,'','',2),(38,1,0,1,'','',2),(39,1,1,1,'xyz','',2),(40,1,0,1,'nausea','',1),(41,1,0,1,'nausea','',1),(42,1,0,1,'nausea','',1),(43,1,1,1,'abc','',4),(44,1,0,1,'','',11),(45,1,0,1,'','',13),(46,1,0,0,'','',14),(47,1,0,1,'','',11),(48,0,1,0,'Head ache','',11),(49,1,0,1,'','',11),(50,1,0,0,'','',11),(51,0,1,1,'Body Ache','',11),(52,1,0,1,'','very effective',11),(53,0,0,0,'','',11),(54,1,0,1,'','',11),(55,1,1,1,'Sore throat','',11),(56,1,0,1,'','nice med',11),(57,0,0,1,'','',11),(58,1,1,0,'abc','',11),(59,0,0,0,'','expensive',11),(60,1,1,1,'xyz','',11),(61,1,0,0,'','',11),(62,1,0,1,'','',13),(63,1,0,1,'','',13),(64,0,0,1,'','',13),(65,1,1,1,'Bad Stomach','',13),(66,1,0,1,'','',13),(67,0,0,0,'','abc',13),(68,1,0,1,'','',13),(69,1,1,1,'Skin problems','xyz',13),(70,0,0,1,'','',13),(71,1,0,1,'','',13),(72,1,0,1,'','Something',13),(73,1,0,1,'','',13),(74,1,0,1,'','',13),(75,1,0,1,'','',13),(76,1,1,1,'Fatigue','',13),(77,1,0,0,'','',13),(78,1,0,1,'','',13),(79,0,0,0,'','',14),(80,1,0,0,'','',14),(81,1,0,1,'','Very expensive',14),(82,0,0,0,'','',14),(83,1,0,1,'','',14),(84,1,0,1,'','',14),(85,0,0,0,'','Cheaper med available in the market',14),(86,0,0,1,'','',14),(87,1,1,0,'Fatigue','',14),(88,1,0,1,'','',14),(89,0,0,1,'','',14),(90,0,0,1,'','',14),(91,1,0,1,'','Doze should be increased',14),(92,1,1,1,'Stomach problem','',14),(93,0,0,0,'','',14),(94,0,0,0,'','',14),(95,0,0,1,'','',14),(96,1,0,0,'','Slow reactive',14),(97,0,0,1,'','',14),(98,1,1,0,'dizzyness','Other comment',1),(99,1,0,1,'','',2),(100,1,0,0,'dizzyness','Comment comment',1),(101,1,0,1,NULL,'',2),(102,1,0,0,'dizzyness','Other comment',1),(103,1,0,1,'','',2),(104,1,0,0,'dizzyness','Some comment',1),(105,1,0,1,'','',2),(106,1,1,0,'sorethroat','Other comment',1),(107,0,0,1,'','',2),(108,1,1,0,'sorethroat','XYZ',1),(109,0,0,1,'','',2),(110,1,1,0,'sorethroat','Other comment',1),(111,0,0,1,'Side effect 2','',2),(112,0,0,1,'skin-burn','Not effective',3),(113,1,0,1,'extreme stomach-ache','',3),(114,1,0,0,'headache','',3),(115,1,0,1,'tiredness','',3),(116,1,0,1,'tiredness','',3),(117,0,0,1,'itching','',3),(118,1,0,0,'extreme headache','',3),(119,1,0,1,'extreme headache','',3),(120,0,0,0,'stomach-ache','',3),(121,1,0,1,'itching','',3),(122,1,0,1,'itching','',3),(123,1,0,0,'stomach-ache','',3),(124,0,0,1,'','Not effective',4),(125,1,1,1,'Nausea','',4),(126,1,0,0,'','',4),(127,1,0,1,'','',4),(128,1,0,1,'','',4),(129,0,0,1,'','',4),(130,1,0,0,'','',4),(131,1,0,1,'','Other Comment',4),(132,0,0,0,'','',4),(133,1,0,1,'','',4),(134,1,0,1,'','',4),(135,1,0,0,'','',4),(136,1,0,1,'','',12),(137,0,1,0,'Head ache','',12),(138,1,0,1,'','',12),(139,1,0,0,'','',12),(140,1,1,1,'Body Ache','',12),(141,1,0,1,'','very effective',12),(142,0,0,0,'','',12),(143,1,0,1,'','',12),(144,1,1,1,'Sore throat','',12),(145,1,0,0,'','nice med',12),(146,1,0,0,'','',12),(147,1,1,0,'abc','',12),(148,0,0,0,'','expensive',12),(149,1,1,1,'Itching','',12),(150,1,0,0,'','',12);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physicians_source`
--

LOCK TABLES `physicians_source` WRITE;
/*!40000 ALTER TABLE `physicians_source` DISABLE KEYS */;
INSERT INTO `physicians_source` VALUES (1,'Christopher','Nolan','salesfast.stm@gmail.com','11119999','23 Ridge Drive',NULL,'Florence','South Carolina','11001','ONC','2000-01-01'),(2,'Alfred','Hitchcock','salesfast.stm@gmail.com','11110000','25 Beverly Hill',NULL,'Florence','South Carolina','11001','ONC','2007-01-01'),(3,'Steven','Spielberg','salesfast.stm@gmail.com','6667777','1-32 Kent Ridge Drive',NULL,'Florence','South Carolina','11003','DIAB','1992-01-01'),(4,'Clint','Eastwood','salesfast.stm@gmail.com','99992299','2-43 New Ridge Mountain',NULL,'Florence','South Carolina','11001','ONC','2015-04-01'),(5,'Quentin','Tarantino','salesfast.stm@gmail.com','66667766','23-103, Normanton Park',NULL,'Florence','South Carolina','11001','ONC','2001-01-01'),(6,'James','Cameroon','salesfast.stm@gmail.com','934902248','block 4, 3-109, Normanton Park',NULL,'Florence','South Carolina','11003','DIAB','2015-02-01'),(7,'Woody','Allen','salesfast.stm@gmail.com','556442343','1-23 Ridge Drive',NULL,'Florence','South Carolina','11001','ONC','2010-01-01'),(8,'Ang','Lee','salesfast.stm@gmail.com','886442000','23-103, Normanton Park',NULL,'Florence','South Carolina','11004','DIAB','2007-01-01'),(9,'Peter','Jackson','salesfast.stm@gmail.com','00220022','2-23 Ridge Drive',NULL,'Florence','South Carolina','11004','ONC','2002-01-01'),(10,'John','Hughes','salesfast.stm@gmail.com','44532134','1-23 Ridge Drive',NULL,'Florence','South Carolina','11001','ONC','2007-01-01');
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physicians_staging`
--

LOCK TABLES `physicians_staging` WRITE;
/*!40000 ALTER TABLE `physicians_staging` DISABLE KEYS */;
INSERT INTO `physicians_staging` VALUES (1,'Christopher','Nolan','salesfast.stm@gmail.com','11119999','23 Ridge Drive',NULL,'Florence','South Carolina','11001','ONC',0,'LOST',1.96,'2000-01-01'),(2,'Alfred','Hitchcock','salesfast.stm@gmail.com','11110000','25 Beverly Hill',NULL,'Florence','South Carolina','11001','ONC',0,'LOST',1.12,'2007-01-01'),(3,'Steven','Spielberg','salesfast.stm@gmail.com','6667777','1-32 Kent Ridge Drive',NULL,'Florence','South Carolina','11003','DIAB',0,'PRESCRIBING',2.92,'1992-01-01'),(4,'Clint','Eastwood','salesfast.stm@gmail.com','99992299','2-43 New Ridge Mountain',NULL,'Florence','South Carolina','11001','ONC',1,'PRESCRIBING',0.13,'2015-04-01'),(5,'Quentin','Tarantino','salesfast.stm@gmail.com','66667766','23-103, Normanton Park',NULL,'Florence','South Carolina','11001','ONC',0,'PRESCRIBING',1.84,'2001-01-01'),(6,'James','Cameroon','salesfast.stm@gmail.com','934902248','block 4, 3-109, Normanton Park',NULL,'Florence','South Carolina','11003','DIAB',1,'LOST',0.15,'2015-02-01'),(16,'Woody','Allen','salesfast.stm@gmail.com','556442343','1-23 Ridge Drive',NULL,'Florence','South Carolina','11001','ONC',0,'LOST',0.76,'2010-01-01'),(17,'Ang','Lee','salesfast.stm@gmail.com','886442000','23-103, Normanton Park',NULL,'Florence','South Carolina','11004','DIAB',0,'PRESCRIBING',1.12,'2007-01-01'),(18,'Peter','Jackson','salesfast.stm@gmail.com','00220022','2-23 Ridge Drive',NULL,'Florence','South Carolina','11004','ONC',0,'TO BE DETAILED',1.72,'2002-01-01'),(19,'John','Hughes','salesfast.stm@gmail.com','44532134','1-23 Ridge Drive',NULL,'Florence','South Carolina','11001','ONC',0,'LOST',1.12,'2007-01-01');
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pitches`
--

LOCK TABLES `pitches` WRITE;
/*!40000 ALTER TABLE `pitches` DISABLE KEYS */;
INSERT INTO `pitches` VALUES (8,239,'PRESCRIBING','perfecting-your-pitch-99.pdf',2),(9,240,'LOST','sample_pitch.pdf',1),(10,241,'LOST','sample_pitch_wJamesCameroon.pdf',1),(11,247,'PRESCRIBING','sample_pitch_wQuentin.pdf',2),(12,248,'PRESCRIBING','sample_pitch_wAlfred.pdf',6),(13,249,'LOST','perfecting-your-pitch-wChristopher.pdf',1),(14,250,'PRESCRIBING','perfecting-your-pitch-wChristopher.pdf',2),(15,251,'LOST','sample_pitch_wQuentin.pdf',1),(16,252,'PRESCRIBING','sample_pitch_wQuentin.pdf',2),(17,269,'LOST','sample_pitch_wAlfred.pdf',1),(18,262,'PRESCRIBING','sample_pitch_wQuentin.pdf',0),(19,273,'PRESCRIBING','sample_pitch_wAlfred.pdf',1),(20,263,'PRESCRIBING','sample_pitch_wClint.pdf',1),(21,267,'PRESCRIBING','sample_pitch_wClint.pdf',1),(22,274,'LOST','sample_pitch_wChristopher.pdf',1),(23,275,'PRESCRIBING','sample_pitch_wQuentin.pdf',1);
/*!40000 ALTER TABLE `pitches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescribing_physicians`
--

DROP TABLE IF EXISTS `prescribing_physicians`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescribing_physicians` (
  `prescribingPhysicianId` int(11) NOT NULL AUTO_INCREMENT,
  `prescribingSince` datetime NOT NULL,
  `productId` int(11) NOT NULL,
  `medicalFieldId` varchar(5) NOT NULL,
  `physicianId` int(11) NOT NULL,
  PRIMARY KEY (`prescribingPhysicianId`),
  KEY `fk_Prescribing_Physicians_Products1_idx` (`productId`),
  KEY `fk_Prescribing_Physicians_Medical_Fields1_idx` (`medicalFieldId`),
  KEY `fk_Prescribing_Physicians_Physicians_Source1_idx` (`physicianId`),
  CONSTRAINT `fk_Prescribing_Physicians_Medical_Fields1` FOREIGN KEY (`medicalFieldId`) REFERENCES `medical_fields` (`medicalFieldId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescribing_Physicians_Physicians_Source1` FOREIGN KEY (`physicianId`) REFERENCES `physicians_source` (`physicianId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescribing_Physicians_Products1` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescribing_physicians`
--

LOCK TABLES `prescribing_physicians` WRITE;
/*!40000 ALTER TABLE `prescribing_physicians` DISABLE KEYS */;
/*!40000 ALTER TABLE `prescribing_physicians` ENABLE KEYS */;
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
  `typeOfProduct` varchar(45) DEFAULT NULL,
  `isAffordable` tinyint(4) DEFAULT NULL,
  `hasLessSideEffects` tinyint(4) DEFAULT NULL,
  `improvedOverProduct` int(11) DEFAULT NULL,
  PRIMARY KEY (`productId`),
  KEY `fk_Products_Medical_Fields1_idx` (`medicalFieldId`),
  CONSTRAINT `fk_Products_Medical_Fields1` FOREIGN KEY (`medicalFieldId`) REFERENCES `medical_fields` (`medicalFieldId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'D_Med_1','2016-01-12','DIAB','NEW PRODUCT',NULL,NULL,NULL),(2,'D_Med_2','2015-11-13','DIAB','NEW PRODUCT',NULL,NULL,NULL),(3,'O_Med_1','2016-01-30','ONC','NEW PRODUCT',NULL,NULL,NULL),(4,'O_Med_2','2015-11-14','ONC','NEW PRODUCT',NULL,NULL,NULL),(11,'O_Med_3','2016-04-04','ONC','NEW PRODUCT',NULL,NULL,NULL),(12,'D_Med_3','2016-04-04','DIAB','NEW PRODUCT',NULL,NULL,NULL),(13,'O_Med_4','2016-04-04','ONC','NEW PRODUCT',NULL,NULL,NULL),(14,'O_Med_5','2016-04-04','ONC',NULL,NULL,NULL,NULL),(33,'O_Med_6','2016-04-10','ONC','IMPROVED PRODUCT',1,0,3),(34,'O_Med_7','2016-04-11','ONC','IMPROVED PRODUCT',0,1,4),(37,'O_Med_8','2016-04-11','ONC','IMPROVED PRODUCT',1,1,11);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `territories`
--

LOCK TABLES `territories` WRITE;
/*!40000 ALTER TABLE `territories` DISABLE KEYS */;
INSERT INTO `territories` VALUES (1,'Terr_A','11001',1,1),(2,'Terr_C','11003',2,1),(3,'Terr_B','11006',1,1),(4,'Terr_D','11004',3,1),(5,'Terr_E','11007',2,1),(6,'Terr_F','11009',3,1),(7,'Terr_G','11010',1,1),(8,'Terr_G','110040',12,1),(9,'Terr_H','110040',13,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_material`
--

LOCK TABLES `training_material` WRITE;
/*!40000 ALTER TABLE `training_material` DISABLE KEYS */;
INSERT INTO `training_material` VALUES (1,'O_Med_1.pdf',1,'ONC',3),(2,'O_Med_2.pdf',1,'ONC',4),(3,'D_Med_1.pdf',2,'DIAB',1),(4,'D_Med_2.pdf',2,'DIAB',2),(11,'O_Med_1.pdf',12,'ONC',3),(12,'O_Med_2.pdf',12,'ONC',4),(13,'D_Med_1.pdf',13,'DIAB',1),(14,'D_Med_2.pdf',13,'DIAB',2),(15,'O_Med_1.pdf',3,'ONC',3),(16,'O_Med_2.pdf',3,'ONC',4),(17,'O_Med_3.pdf',1,'ONC',11),(18,'O_Med_3.pdf',3,'ONC',11),(19,'O_Med_3.pdf',12,'ONC',11),(20,'D_Med_3.pdf',2,'DIAB',12),(21,'D_Med_3.pdf',13,'DIAB',12),(22,'O_Med_4.pdf',1,'ONC',13),(23,'O_Med_4.pdf',12,'ONC',13),(24,'O_Med_5.pdf',1,'ONC',14),(25,'O_Med_5.pdf',12,'ONC',14),(26,'O_Med_6.pdf',1,'ONC',33),(27,'O_Med_6.pdf',12,'ONC',33),(28,'O_Med_7.pdf',1,'ONC',34),(29,'O_Med_7.pdf',12,'ONC',34),(34,'O_Med_8.pdf',1,'ONC',37),(35,'O_Med_8.pdf',12,'ONC',37);
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='				';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Johny','Depp','salesfast.stm@gmail.com','99009922','22-103, Normanton Park',NULL,'Florence','South Carolina','11001','2014-01-01','9999-01-01'),(2,'Christian','Bale','salesfast.stm@gmail.com','99223311','104-Block 5, Beverly hills',NULL,'Florence','South Carolina','11003','2014-01-01','9999-01-01'),(3,'David','Schwimmer','salesfast.stm@gmail.com','77664455','102-Block 3, Beverly hills',NULL,'Florence','South Carolina','11007','2014-01-01','9999-01-01'),(4,'Morgan','Freeman','salesfast.stm@gmail.com','00992900','18-104, Clementi Heights',NULL,'Florence','South Carolina','11006','2013-01-01','9999-01-01'),(12,'Mathew','Perry','salesfast.stm@gmail.com','011201242','102-Block 3, Beverly hills',NULL,'Florence','South Carolina','11004','2010-01-01','9999-01-01'),(13,'Matt','LeBlanc','salesfast.stm@gmail.com','011209999','104-Block 5, Beverly hills',NULL,'Florence','South Carolina','11004','2010-07-01','9999-01-01'),(14,'Steven','Spielberg','salesfast.stm@gmail.com','6667777','1-32 Kent Ridge Drive',NULL,'Florence','South Carolina','11003',NULL,NULL),(15,'James','Cameroon','salesfast.stm@gmail.com','934902248','block 4, 3-109, Normanton Park',NULL,'Florence','South Carolina','11003',NULL,NULL),(16,'Ang','Lee','salesfast.stm@gmail.com','886442000','23-103, Normanton Park',NULL,'Florence','South Carolina','11004',NULL,NULL),(17,'Christopher','Nolan','salesfast.stm@gmail.com','11119999','23 Ridge Drive',NULL,'Florence','South Carolina','11001',NULL,NULL),(18,'Quentin','Tarantino','salesfast.stm@gmail.com','66667766','23-103, Normanton Park',NULL,'Florence','South Carolina','11001',NULL,NULL),(19,'Alfred','Hitchcock','salesfast.stm@gmail.com','11110000','25 Beverly Hill',NULL,'Florence','South Carolina','11001',NULL,NULL),(20,'John','Hughes','salesfast.stm@gmail.com','44532134','1-23 Ridge Drive',NULL,'Florence','South Carolina','11001',NULL,NULL),(21,'Woody','Allen','salesfast.stm@gmail.com','556442343','1-23 Ridge Drive',NULL,'Florence','South Carolina','11001',NULL,NULL),(22,'Clint','Eastwood','salesfast.stm@gmail.com','99992299','2-43 New Ridge Mountain',NULL,'Florence','South Carolina','11001',NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1,'christain','balechristain',2),(2,'johny','deppjohny',1),(3,'david','schwimmerdavid',3),(4,'morgan','freemanmorgan',4),(12,'mathew','perrymathew',12),(13,'matt','leblancmatt',13),(14,'steven','spielberg705',14),(15,'james','cameroon650',15),(16,'ang','lee713',16),(17,'christopher','nolan983',17),(18,'quentin','tarantino939',18),(19,'alfred','hitchcock518',19),(20,'john','hughes638',20),(21,'woody','allen593',21),(22,'clint','eastwood698',22);
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_to_role`
--

LOCK TABLES `user_to_role` WRITE;
/*!40000 ALTER TABLE `user_to_role` DISABLE KEYS */;
INSERT INTO `user_to_role` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,2),(12,12,1),(13,13,1),(14,14,4),(15,15,4),(16,16,4),(17,17,4),(18,18,4),(19,19,4),(20,20,4),(21,21,4),(22,22,4);
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

-- Dump completed on 2016-04-14 14:32:27
