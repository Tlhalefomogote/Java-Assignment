# Banking System

A comprehensive banking system application built with JavaFX, SQLite, and a layered architecture.

## Features
- **Multiple Account Types**: Savings, Investment, and Cheque accounts.
- **Role-Based Access**: Admin and Customer dashboards.
- **Secure**: Password hashing with BCrypt.
- **Persistent**: SQLite database storage.
- **Audit Logging**: Tracks all major actions.

## Prerequisites
- Java 17 or higher
- Maven

## How to Run

### Windows
```powershell
.\mvnw.cmd clean javafx:run
```

### Linux/Mac
```bash
./mvnw clean javafx:run
```

## Running Tests
```bash
.\mvnw.cmd test
```

## Architecture
- **Controllers**: Handle UI logic and delegate to services.
- **Services**: Contain business logic and transaction management.
- **DAOs**: Handle database operations.
- **Models**: Domain objects with core validation.

## Login Credentials
- **Admin**: username `admin`, password `admin`
- **Customer**: Create a new customer via the Admin Dashboard first.
