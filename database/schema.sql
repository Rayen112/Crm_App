-- =============================================================================
-- Schéma de Base de Données - CRM Ticket Management
-- Compatible : MySQL 8.x / MariaDB
-- =============================================================================

CREATE DATABASE IF NOT EXISTS `crm_ticket_managemnet` 
  DEFAULT CHARACTER SET utf8mb4 
  COLLATE utf8mb4_0900_ai_ci;

USE `crm_ticket_managemnet`;

-- 1. Table des Rôles
DROP TABLE IF EXISTS `tickets`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `clients`;
DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2. Table des Utilisateurs
CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(100) NOT NULL,
  `prenom` VARCHAR(100) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `login` VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `telephone` VARCHAR(30) DEFAULT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_email` (`email`),
  UNIQUE KEY `uk_user_login` (`login`),
  KEY `fk_user_role` (`role_id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 3. Table des Clients
CREATE TABLE `clients` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `raison_sociale` VARCHAR(150) DEFAULT NULL,
  `nom` VARCHAR(100) NOT NULL,
  `telephone` VARCHAR(30) DEFAULT NULL,
  `email` VARCHAR(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 4. Table des Tickets (Réclamations)
CREATE TABLE `tickets` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `reference` VARCHAR(30) NOT NULL,
  `objet` VARCHAR(200) NOT NULL,
  `description` TEXT NOT NULL,
  `priorite` ENUM('FAIBLE','MOYENNE','HAUTE','CRITIQUE') NOT NULL,
  `statut` ENUM('NOUVEAU','EN_COURS','EN_ATTENTE','RESOLU','FERME') NOT NULL DEFAULT 'NOUVEAU',
  `date_creation` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modification` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` BIGINT NOT NULL,
  `client_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ticket_reference` (`reference`),
  KEY `fk_ticket_user` (`user_id`),
  KEY `fk_ticket_client` (`client_id`),
  CONSTRAINT `fk_ticket_client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_ticket_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Données initiales pour les rôles
INSERT INTO `roles` (`id`, `name`) VALUES 
(1, 'ADMIN'),
(2, 'AGENT_CRM');
