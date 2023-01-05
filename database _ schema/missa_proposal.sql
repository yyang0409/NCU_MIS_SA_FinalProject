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
-- Table structure for table `proposal`
--

DROP TABLE IF EXISTS `proposal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proposal` (
  `proposal_id` int unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int unsigned NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `raised_funds` int unsigned NOT NULL,
  `goal` int unsigned NOT NULL,
  `image` varchar(225) NOT NULL,
  `proposal_status` int unsigned NOT NULL,
  `viewed_num` int unsigned NOT NULL,
  `created_date` datetime NOT NULL,
  `due_date` datetime NOT NULL,
  PRIMARY KEY (`proposal_id`),
  KEY `fk_Category_Proposal_idx` (`category_id`),
  CONSTRAINT `fk_Category_Proposal` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposal`
--

LOCK TABLES `proposal` WRITE;
/*!40000 ALTER TABLE `proposal` DISABLE KEYS */;
INSERT INTO `proposal` VALUES (1,1,'【WOKY –[●●] 渾圓杯｜最台吸管保溫杯】 770ml 超大容量！一杯暢飲全部外帶手搖','由台灣手搖文化出發設計，有感升級開發770ml大容量結合獨家鈦陶瓷、封水結構、珍珠粗吸管、隱藏式手提及可替換止水豆設計，無畏各式飲品新口味搶攻市場，專為手搖控準備，一杯就足夠日常所需！',18056604,100000,'proposal_1.png',2,14268,'2022-10-24 12:00:00','2023-01-05 23:59:59');
INSERT INTO `proposal` VALUES (2,2,'【100% 原創】 P-Flash 智慧並聯式，金屬閃光彈行動電源','1:1 閃光彈造型，拉掉安全插銷後保險桿立即彈開並點亮手電筒。 無限擴充式行動電源。讓您不論身處何種戰場，都能有源源不絕的電力與中二力！',1081490,1000000,'proposal_2.png',2,386,'2022-12-22 21:00:00','2023-01-17 23:59:59');
INSERT INTO `proposal` VALUES (3,2,' 2023 旗艦機登，美國ELPHECO 絕美太空艙，除臭感應垃圾桶，全面啟動無菌生活！','全天候自動殺菌除臭，丟垃圾、整理垃圾，不用再憋氣！殺菌率 99.9% 垃圾久放不易有異味，感應開蓋不沾手，垃圾廚餘輕鬆丟~',7231638,100000,'proposal_3.png',2,2185,'2022-12-20 12:00:00','2023-02-01 02:00:00');
INSERT INTO `proposal` VALUES (4,2,'【突破500萬】霍曼Homerunpet 智能寵物餵食器｜不在家也能準時放飯，毛孩專屬健康飲食守護者','餵養儲糧，一機搞定！毛孩餵食旗艦機種，不在家也能準時放飯，上班、外出也不用擔心毛孩在家會餓肚子。霍曼最強餵食器，放飯就是輕鬆又寫意～',5328892,50000,'proposal_4.png',2,2265,'2022-12-13 12:00:00','2023-01-19 02:00:00');
INSERT INTO `proposal` VALUES (5,1,' 海外熱銷千萬！【Hurdle 多功能鵝型衣褲架】把衣物收納變簡單','同時具備簡約外型及使用便利性，多樣貼心設計讓你能夠更簡單、快速地整理衣褲，把你的衣櫃空間變大變整齊～',238450,10000,'proposal_5.png',2,159,'2022-12-20 12:00:00','2023-02-04 02:00:00');
INSERT INTO `proposal` VALUES (6,1,'Bouncy™斗篷胸背帶 │ 最可愛的城市探險裝備','世界唯一有「斗篷」的胸背帶。擁有 12 項最完整的機能，集安全、機能、風格於一身。搭配創新的設計：汽車安全綁帶、夜間四色警示燈、同時有支援蘋果 AirTag 的保護殼。',175810,300000,'proposal_6.png',2,12340,'2022-12-27 12:00:00','2023-02-28 23:59:59');
INSERT INTO `proposal` VALUES (7,1,'《歲月靈光｜國家地理2023年攝影日曆》 透過黃框，開啟通往世界的窗口','國家地理團隊首度推出攝影日曆《歲月靈光》，由創刊至今一百多年的照片中，精選 365 張大師經典，搭配豐富的影音知識內容，在新的一年中，每天與您分享一個精彩故事。',1770726,100000,'proposal_7.png',2,14508,'2022-10-26 12:00:00','2023-02-05 23:59:59');
INSERT INTO `proposal` VALUES (8,2,'MOFT 磁吸行動電源｜1 秒吸上、立即充電，行動電源 × 支架 × 卡夾 完美 3 in 1','首創可拆式支架電源組合！磁吸式無線充電，電源僅 120g、1.2 cm 極輕薄，直立橫放三角度輕鬆切換，內建卡夾可收納二張卡片，滿足你出國旅遊、日常攜帶的救急所需！',2965550,100000,'proposal_8.png',2,16708,'2022-12-15 12:00:00','2023-01-31 23:59:59');
INSERT INTO `proposal` VALUES (9,2,'BRITA 全球首發【 Model ONE｜智能瞬熱 UVC 滅菌開飲機 】安心生飲，「淨」乎苛求！德國淨水專家，專為台灣「健康」飲水而生！','56 年德國淨水專家匠心巨獻，超越 RO 純水唯一首選，用健康溫芯好水打造生活儀式感！',31808844,100000,'proposal_9.png',2,31008,'2022-12-01 12:00:00','2023-01-21 23:59:59');
INSERT INTO `proposal` VALUES (10,2,'美國 OASIS 濾淨飲水機｜1 秒瞬熱 X 急速製冷 X UVC 抑菌，每刻都能享受純淨好水！','美國百年飲水機領導品牌，滿足安心生飲等級！半導體急速製冷 X 1 秒瞬熱，冰熱飲一鍵智控隨時即飲！過流式 5 層過濾淨化濾芯與 UVC 抑菌，讓每杯都是純淨新鮮水。',1452770,50000,'proposal_10.png',2,11008,'2022-12-08 12:00:00','2023-01-15 23:59:59');
/*!40000 ALTER TABLE `proposal` ENABLE KEYS */;
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
