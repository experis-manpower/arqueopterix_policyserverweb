-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: arqueopterix
-- ------------------------------------------------------
-- Server version	5.7.9-log

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
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert` (
  `idalert` mediumint(9) NOT NULL AUTO_INCREMENT,
  `sessionid` mediumint(9) NOT NULL,
  `latency` float(5,2) DEFAULT NULL,
  `jitter` float(15,2) DEFAULT NULL,
  `bandwidthup` float(15,2) DEFAULT NULL,
  `bandwidthdown` float(15,2) DEFAULT NULL,
  `packetloss` float(15,2) DEFAULT NULL,
  `qoslevel` varchar(4) DEFAULT NULL,
  `receptionDate` datetime NOT NULL,
  PRIMARY KEY (`idalert`),
  KEY `fk_alert_session_idx` (`sessionid`),
  CONSTRAINT `fk_alert_session` FOREIGN KEY (`sessionid`) REFERENCES `session` (`idsession`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
INSERT INTO `alert` VALUES (1,5231360,2.00,2.00,100.00,50.00,0.00,'0','2018-02-05 17:04:22'),(2,5231360,2.00,2.00,100.00,50.00,0.00,'0','2018-02-05 18:38:32'),(3,5231360,2.00,2.00,100.00,50.00,0.00,'0','2018-02-05 18:39:04'),(4,5231360,2.00,2.00,100.00,50.00,0.00,'0','2018-02-05 18:39:17'),(5,5231360,2.00,2.00,100.00,50.00,0.00,'0','2018-02-05 18:41:33'),(6,5231360,2.00,2.00,100.00,50.00,0.00,'0','2018-02-05 18:56:22'),(7,5231360,2.00,2.00,100.00,50.00,0.00,'0','2018-02-05 19:18:22'),(8,5231360,2.00,2.00,6000.00,6000.00,0.00,'0','2018-02-05 19:19:12');
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `datetime` varchar(45) DEFAULT NULL,
  `alerttype` varchar(45) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=823 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `policies`
--

DROP TABLE IF EXISTS `policies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `policies` (
  `idpolicy` mediumint(9) NOT NULL,
  `endpoint` varchar(15) NOT NULL,
  `numSessions` mediumint(9) NOT NULL,
  `bandwidthUP` float(15,2) DEFAULT NULL,
  `bandwidthDOWN` float(15,2) DEFAULT NULL,
  `pingMaxDelay` float(15,2) DEFAULT NULL,
  `userid` mediumint(9) DEFAULT NULL,
  PRIMARY KEY (`idpolicy`),
  KEY `fk_policies_user_idx` (`userid`),
  CONSTRAINT `fk_policies_user` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `policies`
--

LOCK TABLES `policies` WRITE;
/*!40000 ALTER TABLE `policies` DISABLE KEYS */;
INSERT INTO `policies` VALUES (7665744,'192.168.1.1',10,5000.00,5000.00,20.00,4);
/*!40000 ALTER TABLE `policies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` mediumint(9) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `idsession` mediumint(9) NOT NULL COMMENT 'IP v4 Address',
  `destination` varchar(15) NOT NULL,
  `destinationPort` varchar(5) DEFAULT NULL,
  `origin` varchar(15) DEFAULT NULL,
  `originPort` varchar(5) DEFAULT NULL,
  `userid` mediumint(9) NOT NULL,
  `creationDate` datetime DEFAULT NULL,
  `expired` tinyint(1) NOT NULL,
  PRIMARY KEY (`idsession`),
  KEY `fk_session_user_idx` (`userid`),
  CONSTRAINT `fk_session_user` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (1335973,'192.168.1.1','3006','192.168.1.2','3006',4,'2018-02-05 18:36:04',0),(1363480,'192.168.1.1','3006','192.168.1.2','3006',4,'2018-02-05 18:36:16',0),(1884246,'192.168.1.1','3006','192.168.1.2','3006',4,'2018-02-05 18:36:24',0),(2012275,'192.168.1.1','3006','192.168.1.2','3006',4,NULL,1),(2544871,'192.168.1.1','3006','192.168.1.2','3006',4,NULL,0),(2673005,'192.168.1.1','3006','192.168.1.2','3006',4,'2018-02-05 18:36:17',0),(3178526,'192.168.1.1','3006','192.168.1.2','3006',4,'2018-02-05 18:36:13',0),(3201689,'192.168.1.1','3006','192.168.1.2','3006',4,'2018-02-05 18:36:17',0),(5231360,'192.168.1.1','3006','192.168.1.2','3006',4,'2018-02-05 16:59:22',0),(5966919,'192.168.1.1','3006','192.168.1.2','3006',4,'2018-02-05 18:36:15',0);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` mediumint(9) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `pwd` varchar(200) NOT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,'admin',NULL,'admin',NULL,'$2a$10$daEFnsyWbdXBKkAXJtyqPuFmaAZMEj/NTQnIRl8OXW/pez0as90/u',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` mediumint(9) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_role` mediumint(9) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role_user_idx` (`id_user`),
  KEY `user_role_role_idx` (`id_role`),
  CONSTRAINT `user_role_role` FOREIGN KEY (`id_role`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_role_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-05 19:21:40
