Airlines Reservation System

Un système de réservation de vols développé en Java en suivant les principes de la programmation orientée objet (OOP). Ce projet propose une interface console/desktop (selon implémentation) permettant la gestion des vols, des utilisateurs (passagers et admin), et des réservations.
📋 Table des matières

    Fonctionnalités

    Prérequis

    Installation & lancement

    Architecture & organisation du projet

    Utilisation

    Tests (facultatif)

    Contribution

    Licence

Fonctionnalités

    Gestion des utilisateurs

        Inscription, connexion, mise à jour et suppression de compte pour les passagers

        Création et gestion des comptes administrateurs

    Gestion des vols

        CRUD des vols (création, lecture, mise à jour, suppression) – réservé aux admins

        Génération aléatoire d’un planning de vols

    Réservation de vols

        Réservation de tickets (spécifier le numéro de vol et la quantité)

        Annulation de réservations (restitue les sièges)

        Visualisation de ses vols réservés et de leur statut

    Volet administrateur

        Affichage de tous les passagers

        Visualisation des vols réservés par chaque passager

        Affichage des passagers inscrits sur un vol donné

Prérequis

    ✅ Java 15 ou version supérieure

    🔧 Un IDE compatible Java (IntelliJ IDEA, Eclipse, VS Code...)

    📦 (Optionnel) Maven / Gradle si le projet utilise un build system

Installation & lancement

    Cloner le dépôt

git clone https://github.com/julien-zhao/airlines-reservation-system.git
cd airlines-reservation-system

Compilation

    Avec Maven : mvn clean package

    Avec Gradle : gradle build

    Ou via votre IDE : importer en tant que projet Java

Lancer l’application

    Via la classe Main.java (point d’entrée) dans votre IDE

    Ou avec la commande shell si un .jar a été généré :

        java -jar target/airlines-reservation-system.jar

Architecture & organisation du projet

src/
├── controllers/       ← logique métier (auth, réservation, annulation)
├── models/            ← classes de données : User, Flight, Reservation, Admin
├── services/          ← services pour CRUD & traitement
├── utils/             ← classes utilitaires (ex. : génération de planning)
└── Main.java          ← point d'entrée de l’application

Le projet met en œuvre des principes OOP : encapsulation, héritage (Admin ← User), composition, association.
