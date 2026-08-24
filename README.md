# TaskFlow

TaskFlow is a modern, top-level To-Do List application built with Spring Boot, Thymeleaf, and MySQL.  
It allows users to register, log in, and manage their daily tasks efficiently with a clean, dark glassmorphism UI.  
The application uses server-side rendering with Thymeleaf and stores data securely in a relational database.

---

## Features

- User registration with validation (username, email, password)
- Password encryption using BCrypt
- User login with username or email
- Session-based authentication
- Create new tasks (with validation to prevent empty tasks)
- View a personalized list of tasks (newest first)
- Toggle tasks as completed/incomplete
- Delete tasks permanently
- Responsive, modern UI with CSS gradients and glassmorphism
- Live reload during development (Spring Boot DevTools)

---

## Tech Stack

| Layer      | Technology                       |
|------------|----------------------------------|
| Backend    | Spring Boot 3.5.16, Java 17      |
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
CREATE DATABASE IF NOT EXISTS taskflow_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Configure Database Connection

Open src/main/resources/application.properties and update the following properties with your MySQL credential:
```
spring.datasource.url=jdbc:mysql://localhost:3306/taskflow_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build the project
Open a terminal in the project root and run:
```
mvn clean install
```
Or if you are using the Maven wrapper:
```
./mvnw clean install   # Linux/macOS
mvnw.cmd clean install # Windows
```

### 4. Run the application
Start the Spring Boot application using:
```
mvn spring-boot:run
```
or via the wrapper:
```
./mvnw spring-boot:run
```
Alternatively, you can run the main class DemoApplication directly from your IDE.

### 5. Access the app
Once started, open your browser and visit [Download](http://localhost:8080).

You should see the TaskFlow welcome page, where you can register a new account or log in. After logging in, you will be redirected to your personal task list at /tasks.

---

### 💡 Final Git Tip:
Before pushing to GitHub, make sure you have a `.gitignore` file with the following contents to keep your repository clean:

```text
target/
.idea/
.vscode/
*.iml
```
