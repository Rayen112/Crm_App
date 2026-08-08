-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: crm_ticket_managemnet
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `raison_sociale` varchar(150) DEFAULT NULL,
  `nom` varchar(100) NOT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Tech Solutions SARL','Ahmed Ben Ali','+216 20 123 456','ahmed@techsolutions.tn'),(2,'Tunisia Travel','Sonia Trabelsi','+216 22 456 789','sonia@tunisiatravel.tn'),(3,'Smart Business','Mohamed Mansour','+216 55 321 654','mohamed@smartbusiness.tn'),(4,'Digital Services','Yassine Khelifi','+216 98 654 321','yassine@digitalservices.tn'),(5,'Global Consulting','Amine Jaziri','+216 27 987 654','amine@globalconsulting.tn'),(6,'Tech Solutions SARL','Ahmed Ben Ali','+216 20 123 456','ahmed@techsolutions.tn'),(7,'Tunisia Travel','Sonia Trabelsi','+216 22 456 789','sonia@tunisiatravel.tn'),(8,'Smart Business','Mohamed Mansour','+216 55 321 654','mohamed@smartbusiness.tn'),(9,'Digital Services','Yassine Khelifi','+216 98 654 321','yassine@digitalservices.tn'),(10,'Global Consulting','Amine Jaziri','+216 27 987 654','amine@globalconsulting.tn');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'AGENT_CRM');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reference` varchar(30) NOT NULL,
  `objet` varchar(200) NOT NULL,
  `description` text NOT NULL,
  `priorite` enum('FAIBLE','MOYENNE','HAUTE','CRITIQUE') NOT NULL,
  `statut` enum('NOUVEAU','EN_COURS','EN_ATTENTE','RESOLU','FERME') NOT NULL DEFAULT 'NOUVEAU',
  `date_creation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modification` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` bigint NOT NULL,
  `client_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `reference` (`reference`),
  KEY `fk_ticket_user` (`user_id`),
  KEY `fk_ticket_client` (`client_id`),
  CONSTRAINT `fk_ticket_client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_ticket_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,'TKT-0001','Problème de connexion','Le client ne peut plus se connecter à son compte.','HAUTE','NOUVEAU','2026-08-08 19:06:57','2026-08-08 19:06:57',2,1),(2,'TKT-0002','Erreur lors du paiement','Le paiement échoue lorsque le client valide sa commande.','CRITIQUE','EN_COURS','2026-08-08 19:06:57','2026-08-08 19:06:57',3,2),(3,'TKT-0003','Modification des informations','Le client souhaite modifier son adresse email.','MOYENNE','RESOLU','2026-08-08 19:06:57','2026-08-08 19:06:57',2,3),(4,'TKT-0004','Application lente','Le client signale des temps de chargement très longs.','HAUTE','EN_ATTENTE','2026-08-08 19:06:57','2026-08-08 19:06:57',4,4),(5,'TKT-0005','Demande de renseignement','Le client demande des informations concernant le service.','FAIBLE','FERME','2026-08-08 19:06:57','2026-08-08 19:06:57',3,5),(6,'TKT-0006','Compte bloqué','Le compte du client est bloqué après plusieurs tentatives de connexion.','CRITIQUE','EN_COURS','2026-08-08 19:06:57','2026-08-08 19:06:57',2,1),(7,'TKT-0007','Problème de notification','Le client ne reçoit pas les notifications par email.','MOYENNE','NOUVEAU','2026-08-08 19:06:57','2026-08-08 19:06:57',4,2),(8,'TKT-0008','Erreur affichage tableau de bord','Certaines statistiques ne sont pas correctement affichées.','HAUTE','RESOLU','2026-08-08 19:06:57','2026-08-08 19:06:57',3,3);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `login` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `login` (`login`),
  KEY `fk_user_role` (`role_id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Ben Salah','Ahmed','ahmed.bensalah@crm.tn','ahmed','password123','+216 20 111 222',1),(2,'Trabelsi','Sonia','sonia.trabelsi@crm.tn','sonia','password123','+216 22 333 444',2),(3,'Mansour','Mohamed','mohamed.mansour@crm.tn','mohamed','password123','+216 55 555 666',2),(4,'Khelifi','Yassine','yassine.khelifi@crm.tn','yassine','password123','+216 98 777 888',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-08 19:13:22
