# CoBuild - Spring Boot Backend

## 🚀 Project Overview
CoBuild is a Spring Boot backend project using **Spring Security**, **JWT authentication**, and **role-based access control (RBAC)**. It supports user authentication and authorization with **ADMIN** and **USER** roles.

---

## 🔧 Setup Instructions

### 1️⃣ Create `.env.local` for Database Credentials
Before running the project, you **must** create a `.env.local` file in the **root directory** to store your MySQL credentials.

#### **Create and add the following environment variables in `.env.local`:**
```ini
DB_URL=jdbc:mysql://localhost:3306/cobuild?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=your_mysql_user
DB_PASSWORD=your_mysql_password
```
