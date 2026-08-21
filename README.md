# SocialHub

SocialHub is a simple social media platform built with Spring Boot, Thymeleaf, and MySQL.  
It allows users to register, log in, and share short posts (up to 280 characters).  
The frontend features a modern dark glassmorphism design and uses Thymeleaf for server-side rendering.

---

## Features

- User registration with validation (username, email, password)
- Password encryption using BCrypt
- User login with username or email
- Session-based authentication
- Create posts (up to 280 characters)
- View a feed of all posts (newest first)
- Responsive, modern UI with CSS gradients and glassmorphism
- Live reload during development (Spring Boot DevTools)

---

## Tech Stack

| Layer      | Technology                       |
|------------|----------------------------------|
| Backend    | Spring Boot 3.3.5, Java 17       |
| Database   | MySQL (via WampServer)           |
| ORM        | Spring Data JPA / Hibernate      |
| Frontend   | Thymeleaf, HTML5, CSS3           |
| Security   | Spring Security Crypto (BCrypt)  |
| Build Tool | Maven                            |

---

## Prerequisites

Before running the project, ensure you have the following installed:

- **Java 17 or 21** (JDK) – [Download](https://adoptium.net/)
- **Maven 3.8+** – [Download](https://maven.apache.org/download.cgi)  
  *(or use the Maven wrapper: `./mvnw` on Linux/macOS, `mvnw.cmd` on Windows)*
- **WampServer** (with MySQL) – [Download](https://www.wampserver.com/)
- **VS Code** with recommended extensions:
  - Extension Pack for Java
  - Spring Boot Extension Pack
  - LiveReload (optional, for browser auto-refresh)

---

## Setup Instructions

### 1. Start WampServer and create the database

1. Launch WampServer and make sure the icon in the system tray is green.
2. Open **phpMyAdmin** by clicking the Wamp icon → `phpMyAdmin` or visiting [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
3. Execute the following SQL to create the database:

```sql
CREATE DATABASE IF NOT EXISTS socialdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Running the Application

1. Configure database connection
2. Open src/main/resources/application.properties and update the following properties with your MySQL credentials:
```
properties
spring.datasource.url=jdbc:mysql://localhost:3306/socialdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
(Replace your_username and your_password with your WampServer MySQL credentials. The default is often root with no password.)
```

### 3. Build the project
1. Open a terminal in the project root and run:
```
bash
mvn clean install
```

Or if you are using the Maven wrapper:
```
bash
./mvnw clean install   # Linux/macOS
mvnw.cmd clean install # Windows
```

### 4. Run the application
1. Start the Spring Boot application using:
```
bash
mvn spring-boot:run
```

or via the wrapper:
```
bash
./mvnw spring-boot:run
```

Alternatively, you can run the main class SocialHubApplication directly from your IDE.

### 5. Access the app
Once started, open your browser and visit http://localhost:8080.
You should see the SocialHub login/registration page.
