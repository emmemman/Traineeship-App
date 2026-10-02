# Traineeship Management Application

A web-based application for managing university traineeships, developed as part of the **Software Development II** course.

The project focuses on understanding, refactoring, testing and extending an existing legacy Spring Boot application.

The system supports the complete traineeship management process involving:

- Students
- Companies
- Professors
- Traineeship Committee members

---

## About the Project

The Traineeship Management Application allows a university traineeship committee to manage open and assigned traineeship positions.

Companies can publish traineeship positions, students can apply for traineeships, the committee can assign positions and supervisors, and professors and companies can evaluate traineeships.

The main focus of this project was not only to implement new functionality, but also to improve the quality of an existing legacy codebase through:

- Code refactoring
- Separation of responsibilities
- Design patterns
- Automated testing
- Improved maintainability
- Improved extensibility

---

## Technologies

The application is implemented using:

- **Java 17**
- **Spring Boot 3.4.1**
- **Spring MVC**
- **Spring Security**
- **Spring Data JPA**
- **Thymeleaf**
- **MySQL**
- **Maven**
- **JUnit**
- **Mockito**
- **H2 Database** for integration testing

---

## Architecture

The application follows a layered architecture based on the MVC pattern.

```text
Presentation Layer
      │
      ▼
 Controllers
      │
      ▼
  Services
      │
      ▼
 Domain Model
      │
      ▼
 Mappers / Repositories
      │
      ▼
    MySQL
