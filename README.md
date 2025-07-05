# ✈️ Airlines Reservation System

Un système de réservation de vols développé en Java, mettant en œuvre les principes de la programmation orientée objet (POO). Il permet aux passagers de réserver des vols et aux administrateurs de gérer les vols et les utilisateurs.

---

## 📋 Table des matières

- [Fonctionnalités](#fonctionnalités)
- [Prérequis](#prérequis)
- [Installation & Lancement](#installation--lancement)
- [Architecture du Projet](#architecture-du-projet)
- [Utilisation](#utilisation)
- [Contribution](#contribution)
- [Licence](#licence)

---

## 🚀 Fonctionnalités

### Passagers
- Inscription et connexion
- Réservation et annulation de vols
- Consultation des vols réservés
- Suppression de compte

### Administrateurs
- Connexion sécurisée
- Création, modification et suppression de vols
- Consultation de tous les passagers
- Affichage des vols réservés par passager
- Affichage des passagers par vol

---

## ✅ Prérequis

- Java 15 ou plus
- Un IDE Java (IntelliJ, Eclipse, VS Code...)
- Git

---

## ⚙️ Installation & Lancement

1. **Cloner le projet**
   ```bash
   git clone https://github.com/julien-zhao/airlines-reservation-system.git
   cd airlines-reservation-system


2. **Compiler le projet**

    Avec un IDE : importer le projet et exécuter Main.java

    En ligne de commande :
  ```bash
    javac src/Main.java
    java src/Main
  ```

3. **Architecture du Projet**
  ```bash
src/
├── controllers/       → logique métier (authentification, réservation)
├── models/            → entités : User, Admin, Flight, Reservation
├── services/          → traitement métier (gestion vols, réservations)
├── utils/             → outils divers (par ex. générateur de vols)
└── Main.java          → point d’entrée de l’application
  ```

User est une classe abstraite utilisée par les rôles Passenger et Admin
Séparation claire des responsabilités avec un découpage en MVC simplifié
