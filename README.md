# CoBuild - Spring Boot Backend

## ⚙️ Backend : Configuration Spring Boot

### 1. Prérequis
- Java 17+
- Maven
- MySQL

### 2. Base de données

Créer une base de données nommée `cobuild` :
```sql
CREATE DATABASE cobuild;
```

### 3. Fichier `.env.local` ou `application.properties`

Configurer les identifiants MySQL :
```
DB_URL=jdbc:mysql://localhost:3306/cobuild?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=NOM_UTILISATEUR
DB_PASSWORD=MOT_DE_PASSE
```

Lancer l'application Spring Boot :
```bash
./mvnw spring-boot:run
```

L'API sera accessible sur : `http://localhost:8080`