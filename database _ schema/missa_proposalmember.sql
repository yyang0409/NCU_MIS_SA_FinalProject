-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: missa
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `proposalmember`
--

DROP TABLE IF EXISTS `proposalmember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proposalmember` (
  `ProposalMember_id` int unsigned NOT NULL AUTO_INCREMENT,
  `proposal_id` int unsigned NOT NULL,
  `member_id` int unsigned NOT NULL,
  `is_premium` tinyint unsigned NOT NULL,
  PRIMARY KEY (`ProposalMember_id`),
  KEY `fk_Member_ProposalMember_idx` (`member_id`),
  KEY `fk_ProposalMember_Proposal1_idx` (`proposal_id`),
  CONSTRAINT `fk_Member_ProposalMember` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `fk_ProposalMember_Proposal1` FOREIGN KEY (`proposal_id`) REFERENCES `proposal` (`proposal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposalmember`
--

LOCK TABLES `proposalmember` WRITE;
/*!40000 ALTER TABLE `proposalmember` DISABLE KEYS */;
INSERT INTO `proposalmember` VALUES (1,1,1,1);
INSERT INTO `proposalmember` VALUES (2,2,1,1);
INSERT INTO `proposalmember` VALUES (3,3,1,1);
INSERT INTO `proposalmember` VALUES (4,4,1,1);
INSERT INTO `proposalmember` VALUES (5,5,1,1);
INSERT INTO `proposalmember` VALUES (6,6,1,1);
INSERT INTO `proposalmember` VALUES (7,7,1,1);
INSERT INTO `proposalmember` VALUES (8,8,1,1);
INSERT INTO `proposalmember` VALUES (9,9,1,1);
INSERT INTO `proposalmember` VALUES (10,10,1,1);
/*!40000 ALTER TABLE `proposalmember` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-13 23:28:54
