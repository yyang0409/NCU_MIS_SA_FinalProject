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
-- Table structure for table `proposaloption`
--

DROP TABLE IF EXISTS `proposaloption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proposaloption` (
  `proposalOption_id` int unsigned NOT NULL AUTO_INCREMENT,
  `proposal_id` int unsigned NOT NULL,
  `proposal_option_title` text NOT NULL,
  `image` varchar(225) NOT NULL,
  `description` text NOT NULL,
  `price` int unsigned NOT NULL,
  PRIMARY KEY (`proposalOption_id`),
  KEY `fk_Proposal_ProposalOption_idx` (`proposal_id`),
  CONSTRAINT `fk_Proposal_ProposalOption` FOREIGN KEY (`proposal_id`) REFERENCES `proposal` (`proposal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposaloption`
--

LOCK TABLES `proposaloption` WRITE;
/*!40000 ALTER TABLE `proposaloption` DISABLE KEYS */;
INSERT INTO `proposaloption` VALUES (1,1,'【WOKY –[●●] 渾圓杯770ml｜單入】','proposaloption_1.png','內含：WOKY –[●●] 渾圓杯770ml ×1  WOKY 矽膠粗吸管 ×1  WOKY 扭扭彈跳粗吸管( Tritan ) ×1  產品說明書 ×1',890);
INSERT INTO `proposaloption` VALUES (2,1,'【WOKY –[●●] 渾圓杯770ml｜雙入】','proposaloption_2.png','內含：WOKY –[●●] 渾圓杯770ml ×2	 WOKY 矽膠粗吸管 ×2  WOKY 扭扭彈跳粗吸管( Tritan ) ×2  產品說明書 ×2',1680);
INSERT INTO `proposaloption` VALUES (3,1,'【WOKY –[●●] 渾圓杯770ml｜六入】','proposaloption_3.png','內含：WOKY –[●●] 渾圓杯770ml ×6	 WOKY 矽膠粗吸管 ×6  WOKY 扭扭彈跳粗吸管( Tritan ) ×6  產品說明書 ×6',4800);
INSERT INTO `proposaloption` VALUES (4,2,'【嘖嘖優惠方案】','proposaloption_4.png','【P-flash 閃光彈行動電源】 x 1 請注意：P-Flash閃光彈行動電源本體為電池匣，並未包含18650電池，有需要的朋友可於贊助加購品項中購買，感謝您的支持。',2660);
INSERT INTO `proposaloption` VALUES (5,2,'【雙人同行方案】','proposaloption_5.png','【P-flash 閃光彈行動電源】 x 2 請注意：P-Flash閃光彈行動電源本體為電池匣，並未包含18650電池，有需要的朋友可於贊助加購品項中購買，感謝您的支持。',4660);
INSERT INTO `proposaloption` VALUES (6,2,'【四人小隊方案】','proposaloption_6.png','【P-flash 閃光彈行動電源】 x 3 請注意：P-Flash閃光彈行動電源本體為電池匣，並未包含18650電池，有需要的朋友可於贊助加購品項中購買，感謝您的支持。',9120);
INSERT INTO `proposaloption` VALUES (7,3,'【20L 垃圾桶｜早鳥價 45 折】','proposaloption_7.png','◆ 型號 ELPH9811◆ 未來售價 $4,580，現省 $2,500◆ 方案內含：20L 絕美太空艙除臭感應垃圾桶 x 1 使用說明書 x 1',2080);
INSERT INTO `proposaloption` VALUES (8,3,'【30L 垃圾桶｜早鳥價 46 折】','proposaloption_8.png','◆ 型號 ELPH7534◆ 未來售價 $4,980，現省 $2,700◆ 方案內含：30L 絕美太空艙除臭感應垃圾桶 x 1 使用說明書 x 1',2280);
INSERT INTO `proposaloption` VALUES (9,3,'【50L 垃圾桶｜早鳥價 43 折】','proposaloption_9.png','◆ 型號 ELPH5534◆ 未來售價 $6,880，現省 $3,900◆ 方案內含：50L 絕美太空艙除臭感應垃圾桶 x 1 使用說明書 x 1',2980);
INSERT INTO `proposaloption` VALUES (10,4,'▇ 早鳥單入（陶瓷碗）','proposaloption_10.png','本組內容物包含：✅ 餵食器本體 x 1 ✅ 陶瓷碗 x 1 ✅ 乾燥劑 x 1',2390);
INSERT INTO `proposaloption` VALUES (11,4,'▇ 早鳥單入（不鏽鋼碗）','proposaloption_11.png','本組內容物包含：✅ 餵食器本體 x 1 ✅ 不鏽鋼碗 x 1 ✅ 乾燥劑 x 1',2190);
INSERT INTO `proposaloption` VALUES (12,4,'▇ 早鳥２入（陶瓷碗）','proposaloption_12.png','本組內容物包含：✅ 餵食器本體 x 2 ✅ 陶瓷碗 x 2 ✅ 乾燥劑 x 2',4760);
INSERT INTO `proposaloption` VALUES (13,5,'▇ 【超早鳥組合A】','proposaloption_13.png','多功能鵝型衣架 x 10  多功能鵝型褲架 x 10',950);
INSERT INTO `proposaloption` VALUES (14,5,'▇ 【超早鳥組合B】','proposaloption_14.png','多功能鵝型衣架 x 20  多功能鵝型褲架 x 10',1390);
INSERT INTO `proposaloption` VALUES (15,5,'▇ 【超早鳥組合C】','proposaloption_15.png','多功能鵝型褲架 x 10',490);
INSERT INTO `proposaloption` VALUES (16,6,'【單買胸背帶】','proposaloption_16.png','未來零售價 1,390 元，現省500元',890);
INSERT INTO `proposaloption` VALUES (17,6,'【單買斗篷(可變身遛狗小包)】','proposaloption_17.png','未來零售價 1,990 元，現省700元',1290);
INSERT INTO `proposaloption` VALUES (18,6,'【胸背帶+斗篷(可變身遛狗小包)】','proposaloption_18.png','未來零售價 3,380 元，現省1400元',1980);
INSERT INTO `proposaloption` VALUES (19,7,'【方案一】','proposaloption_19.png','內含：《野性時刻：國家地理自然攝影新經典》X1、《歲月靈光》攝影曆 X1',1180);
INSERT INTO `proposaloption` VALUES (20,7,'【方案二】','proposaloption_20.png','《歲月靈光》攝影曆 X1 ',950);
INSERT INTO `proposaloption` VALUES (21,7,'【方案三】','proposaloption_21.png','《國家地理》數位全閱讀一年、《野性時刻：國家地理自然攝影新經典》X1、《國家地理》雜誌 2023 年 12 期、',3900);
INSERT INTO `proposaloption` VALUES (22,8,'超早鳥｜磁吸電源支架組 75 折','proposaloption_22.png','內含：磁吸行動電源 x 1、磁吸式手機支架 x 1',1699);
INSERT INTO `proposaloption` VALUES (23,8,'雙入｜磁吸電源支架組 7 折','proposaloption_23.png','內含：磁吸行動電源 x 2、磁吸式手機支架 x 2',3198);
INSERT INTO `proposaloption` VALUES (24,8,'磁吸行動電源 單入組','proposaloption_24.png','內含：磁吸行動電源 x 1',1399);
INSERT INTO `proposaloption` VALUES (25,9,'早鳥獨享組合優惠','proposaloption_25.png','內含：Model ONE【智能瞬熱 UVC 滅菌開飲機】1 台、MAXTRA+濾芯 去水垢專家 1 個、[贈] MAXTRA+濾芯 -全效型 9 個、[贈] 外出用濾水瓶 1 個',13888);
INSERT INTO `proposaloption` VALUES (26,9,'早鳥雙入組合優惠','proposaloption_26.png','內含：Model ONE【智能瞬熱 UVC 滅菌開飲機】1 台x2、MAXTRA+濾芯 去水垢專家 1 個x2、[贈] MAXTRA+濾芯 -全效型 12 個x2、[贈] 外出用濾水瓶 1 個x2',27776);
INSERT INTO `proposaloption` VALUES (27,9,'嘖嘖組合價','proposaloption_27.png','內含：Model ONE【 智能瞬熱 UVC 滅菌開飲機】一台、MAXTRA+濾芯 -去水垢專家 1 個',19200);
INSERT INTO `proposaloption` VALUES (28,10,'▐ 超早鳥濾芯同捆組','proposaloption_28.png','內含：OASIS CURVE 瞬熱製冷 UVC 濾淨飲水機 X 1、OASIS 五層過濾淨化濾芯 X 4（含機器附贈一顆）',9390);
INSERT INTO `proposaloption` VALUES (29,10,'▐ 超早鳥單入價','proposaloption_29.png','內含：OASIS CURVE 瞬熱製冷 UVC 濾淨飲水機 X 1、OASIS 五層過濾淨化濾芯 X 1',8690);
INSERT INTO `proposaloption` VALUES (30,10,'▐ 超早鳥濾芯同捆組（外島配送選項）','proposaloption_30.png','內含：OASIS CURVE 瞬熱製冷 UVC 濾淨飲水機 X 1、OASIS 五層過濾淨化濾芯 X 4（含機器附贈一顆）',9390);
/*!40000 ALTER TABLE `proposaloption` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12  1:57:05
