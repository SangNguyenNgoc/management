-- MariaDB dump 10.19  Distrib 10.4.27-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: management
-- ------------------------------------------------------
-- Server version	10.4.27-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devices` (
  `id` bigint(20) NOT NULL,
  `description` text NOT NULL,
  `name` varchar(100) NOT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES (1000001,'Micro không dây MS2023','Micro',''),(1000002,'Micro không dây MS2024','Micro',''),(7000001,'Bản điện tử trình chiếu','Bảng điện tử','');
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `penalties`
--

DROP TABLE IF EXISTS `penalties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `penalties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `payment` int(11) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `type` varchar(250) NOT NULL,
  `person_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8m582ovuldxi4xesy7tjnbvdt` (`person_id`),
  CONSTRAINT `FK8m582ovuldxi4xesy7tjnbvdt` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `penalties`
--

LOCK TABLES `penalties` WRITE;
/*!40000 ALTER TABLE `penalties` DISABLE KEYS */;
/*!40000 ALTER TABLE `penalties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons` (
  `id` bigint(20) NOT NULL,
  `department` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(500) NOT NULL,
  `phone_number` varchar(10) NOT NULL,
  `profession` varchar(100) NOT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1x5aosta48fbss4d5b3kuu0rd` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES (1120150184,'GDTH','ttn8883@gmail.com','Trần Thị Nữ','$2a$10$kE.DQaZtjFuh6k28nHjfcOp6EZqyT3/DSx5v/zYuDMPLWVRnq05YS','1111111111','GDTH',''),(1121530087,'TLH','ttn76464@gmail.com','Trần Thiếu Nam','$2a$10$kE.DQaZtjFuh6k28nHjfcOp6EZqyT3/DSx5v/zYuDMPLWVRnq05YS','1111111112','QLGD',''),(1123330257,'QTKD','ntn3838@gmail.com','Ngô Tuyết Nhi','$2a$10$kE.DQaZtjFuh6k28nHjfcOp6EZqyT3/DSx5v/zYuDMPLWVRnq05YS','1111111113','QTKD',''),(2147483647,'CNTT','nvn3322@gmail.com','Nguyễn Văn Nam','$2a$10$kE.DQaZtjFuh6k28nHjfcOp6EZqyT3/DSx5v/zYuDMPLWVRnq05YS','123456789','HTTT',''),(3121410417,'CNTT','ironman343@gmail.com','Tony Start','$2a$10$kE.DQaZtjFuh6k28nHjfcOp6EZqyT3/DSx5v/zYuDMPLWVRnq05YS','0916921132','KTPM','');
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usage_info`
--

DROP TABLE IF EXISTS `usage_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usage_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `booking_time` datetime DEFAULT NULL,
  `borrow_time` datetime DEFAULT NULL,
  `checkin_time` datetime DEFAULT NULL,
  `return_time` datetime DEFAULT NULL,
  `device_id` bigint(20) DEFAULT NULL,
  `person_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcaul6j9rx4pnr9d6bpxkyxm0a` (`device_id`),
  KEY `FKlmja21tafdm6mgy019esd0aj4` (`person_id`),
  CONSTRAINT `FKcaul6j9rx4pnr9d6bpxkyxm0a` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`),
  CONSTRAINT `FKlmja21tafdm6mgy019esd0aj4` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usage_info`
--

LOCK TABLES `usage_info` WRITE;
/*!40000 ALTER TABLE `usage_info` DISABLE KEYS */;
INSERT INTO `usage_info` VALUES (1,NULL,NULL,'2024-04-08 15:15:15',NULL,NULL,3121410417);
/*!40000 ALTER TABLE `usage_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-08 16:03:18
