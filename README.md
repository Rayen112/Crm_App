# CRM Ticket Management - Système de Gestion des Réclamations

Application web complète de gestion de la relation client (CRM) et du support technique (gestion des tickets de réclamation), développée selon le cahier des charges officiel du stage de développement web (IT GATE).

---

## 🚀 Architecture Globale

Le projet repose sur une architecture moderne découplée Frontend / Backend :

* **Frontend** : **Angular 21** (TypeScript, Standalone Components, Reactive Forms, Bootstrap 5 & Bootstrap Icons).
* **Backend** : **Spring Boot 4 / 3** (Java 21, Spring Data JPA, Spring Security, JWT JJWT 0.12.6, MapStruct, SpringDoc OpenAPI / Swagger).
* **Base de données** : **MySQL 8.x** (Moteur InnoDB, encodage utf8mb4).

---

## 📋 Fonctionnalités Implémentées

### 1. Authentification & Sécurité
* Connexion sécurisée avec identifiant ou email et mot de passe masqué (affichage/masquage au clic).
* Inscription d'agents (`POST /api/auth/register`).
* Génération de tokens **JWT** (HMAC-SHA) et gestion de session sans état (`Stateless`).
* Mots de passe chiffrés avec **BCrypt**.
* Protection des routes frontend avec `AuthGuard` et `AdminGuard`.
* Intercepteur HTTP injectant automatiquement le token `Authorization: Bearer <token>`.

### 2. Tableau de Bord (Dashboard)
* Affichage en temps réel des 4 statistiques clés du CDC :
  * Nombre total de tickets
  * Tickets ouverts (Nouveau, En cours, En attente, Résolu)
  * Tickets fermés
  * Nombre total d'utilisateurs
* Raccourcis et cartes d'action rapide.

### 3. Gestion des Tickets (Réclamations)
* Génération automatique du numéro de référence (`TKT-0001`, `TKT-0002`, ...).
* Attribution des priorités : `Faible`, `Moyenne`, `Haute`, `Critique`.
* Gestion du cycle de vie par statut : `Nouveau`, `En cours`, `En attente`, `Résolu`, `Fermé`.
* **Recherche textuelle** par mot-clé sur l'objet ou la description.
* **Filtres multicritères dynamiques** : par statut, par priorité, par client et par intervalle de dates (`dateDebut` $\rightarrow$ `dateFin`).
* Fiche détaillée du ticket avec changement rapide de statut en un clic.

### 4. Gestion des Utilisateurs (Espace Administrateur)
* CRUD complet des utilisateurs (création, modification, consultation, suppression).
* Attribution des rôles : `ADMIN` ou `AGENT_CRM`.
* Sécurisation des endpoints backend via `@PreAuthorize("hasRole('ADMIN')")`.

### 5. Gestion des Clients
* Annuaire des clients (raison sociale, nom du contact, téléphone, email).
* Création, modification et suppression de clients.

---

## 🛠️ Prérequis

Avant de lancer le projet, assurez-vous d'avoir installé sur votre machine :
* **Java JDK 21**
* **Node.js** (v20+ ou v24+) et **npm**
* **Angular CLI** (`npm install -g @angular/cli`)
* **MySQL Server** (8.0 ou supérieur)

---

## ⚙️ Installation & Démarrage

### 1. Configuration de la Base de Données

1. Démarrez votre service **MySQL**.
2. Créez et alimentez la base de données avec le script fourni dans le dossier `database/` :
   ```bash
   mysql -u root -p < database/crm_ticket_management.sql
   ```
   *(Ou importez `crm_ticket_management.sql` via MySQL Workbench / phpMyAdmin).*
3. Vérifiez les identifiants de connexion dans `backend/crm/src/main/resources/application.properties` :
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/crm_ticket_managemnet
   spring.datasource.username=root
   spring.datasource.password=VOTRE_MOT_DE_PASSE
   ```

---

### 2. Démarrage du Backend (Spring Boot)

1. Ouvrez un terminal dans le dossier `backend/crm/` :
   ```bash
   cd backend/crm
   ```
2. Compilez et lancez l'application via le wrapper Maven :
   ```bash
   ./mvnw spring-boot:run
   ```
3. Le serveur démarre sur le port `8080` :
   * **API REST** : `http://localhost:8080/api`
   * **Documentation Swagger / OpenAPI** : `http://localhost:8080/swagger-ui/index.html`

---

### 3. Démarrage du Frontend (Angular)

1. Ouvrez un terminal dans le dossier `frontend/` :
   ```bash
   cd frontend
   ```
2. Installez les dépendances (si ce n'est pas déjà fait) :
   ```bash
   npm install
   ```
3. Lancez le serveur de développement Angular :
   ```bash
   npm start
   ```
4. Accédez à l'application dans votre navigateur :
   👉 **`http://localhost:4200`**

---

## 🔑 Comptes de Test par Défaut

Les comptes pré-configurés dans le script SQL sont :

| Rôle | Identifiant (Login) | Mot de passe | Description |
| :--- | :--- | :--- | :--- |
| **Administrateur** | `ahmed` | `password123` | Accès complet (Tickets, Clients, Gestion Utilisateurs) |
| **Agent CRM** | `sonia` | `password123` | Gestion des tickets et consultation clients |
| **Agent CRM** | `mohamed` | `password123` | Gestion des tickets et consultation clients |
| **Agent CRM** | `yassine` | `password123` | Gestion des tickets et consultation clients |

*(Remarque : Vous pouvez également créer un nouveau compte agent directement via la page **Inscription**).*

---

## 📁 Structure du Projet

```
CRM-TICKET-MANAGEMENT/
├── backend/crm/                     # Backend Spring Boot
│   ├── pom.xml                      # Configuration Maven & dépendances
│   └── src/main/java/com/ITGate/crm/
│       ├── config/                  # OpenAPI / Swagger configuration
│       ├── controller/              # Contrôleurs REST (Auth, Ticket, Client, User, Dashboard)
│       ├── dto/                     # Objets de transfert de données (Request/Response)
│       ├── enums/                   # RoleName, TicketPriority, TicketStatus
│       ├── mapper/                  # Mappers MapStruct
│       ├── model/                   # Entités JPA (Role, User, Client, Ticket)
│       ├── repository/              # Interfaces Spring Data JPA
│       ├── security/                # JWT Service, Filtre d'authentification, SecurityConfig
│       └── service/                 # Logique métier des services
├── database/
│   ├── crm_ticket_management.sql    # Schéma complet + données de démonstration
│   └── schema.sql                   # Définition DDL du schéma relationnel
├── frontend/                        # Frontend Angular 21
│   ├── src/app/
│   │   ├── core/                    # Services HTTP, Modèles, Guards, Intercepteur JWT
│   │   ├── features/
│   │   │   ├── auth/                # Composants Login & Inscription
│   │   │   ├── dashboard/           # Tableau de bord avec indicateurs KPI
│   │   │   ├── tickets/             # Liste, Détail, Formulaire Ticket, Recherche & Filtres
│   │   │   ├── users/               # Liste et Formulaire Utilisateurs (Espace Admin)
│   │   │   └── clients/             # Liste et Formulaire Clients
│   │   ├── layout/                  # Barre de navigation responsive (Navbar)
│   │   ├── app.routes.ts            # Routage de l'application
│   │   └── app.ts                   # Composant racine
│   └── package.json                 # Dépendances npm (Angular, Bootstrap, RxJS)
└── README.md                        # Documentation d'installation et d'utilisation
```
