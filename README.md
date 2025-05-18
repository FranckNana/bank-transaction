# bank-transaction

# 💳 Bank Transaction Reconciliation

Projet de réconciliation de transactions financières réparties sur plusieurs serveurs, avec interface web Angular et backend Spring Boot.

## 📌 Objectif

Développer une application capable de reconstruire la chaîne d’événements d’une transaction financière à partir d’une base de données, en prenant en compte :

- des dates mal formatées,
- des identifiants multiples (primaires et secondaires),
- un ordre logique d’événements basé sur un référentiel `stepCode`, `stepRank`, `eventRank`.

---

## 🧩 Cas d’usage

Vous travaillez dans une grande banque internationale. Chaque serveur génère un ID unique pour chaque transaction et un nouvel ID lors des transmissions. Vous devez :

- Regrouper les événements appartenant à une même transaction,
- Les ordonner par `stepRank` puis `eventRank`,
- Gérer les incohérences de format de date,
- Offrir une recherche par ID depuis une interface web.

---

## 🧰 Technologies utilisées

- **Frontend** : Angular 18+
- **Backend** : Java 17, Spring Boot
- **Base de données** : PostgreSQL
- **Conteneurs** : Docker, Docker Compose

---

## 🚀 Lancement du projet

#### 📦 1. Cloner le dépôt

```bash
git clone https://github.com/FranckNana/bank-transaction.git
cd bank-transaction
```

### 🐳 Lancer avec Docker

#### 1. Construire les images et démarrer les services

```bash
docker-compose up --build
```

#### 2. Accéder à l’application

- Frontend : http://localhost:8000
- Backend (Spring Boot API) : http://localhost:8080

---

## 🔍 Fonctionnalités UI

- ✅ Formulaire de recherche par ID
- ✅ Affichage des événements liés triés par `stepRank` et `eventRank`
- ✅ Timestamps affichés
- ✅ Détection des événements incohérents ou invalides

---

## ✅ Critères de performance

- **Exactitude** : réconciliation correcte et triée.
- **Robustesse** : gestion des dates mal formatées.
- **Efficacité** : traitement d’un volume élevé de transactions.

---

## 🙋‍♂️ Auteur

**Franck Nana**  
[GitHub](https://github.com/FranckNana)

---
