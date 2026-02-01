# Military Asset Management System - Backend

## Overview
A secure, enterprise-grade Spring Boot REST API for tracking military assets. It features strict Role-Based Access Control (RBAC), immutable audit logging, and is deployed on Render.

* **Base URL:** [https://militaryassetmanagement-2tbh.onrender.com](https://militaryassetmanagement-2tbh.onrender.com)

## Tech Stack
- **Framework**: Spring Boot 4.0.1
- **Language**: Java 17 / 21
- **Database**: PostgreSQL 17
- **Security**: Spring Security + JWT (JSON Web Tokens)
- **Deployment**: Render (Dockerized)
- **Build Tool**: Maven

## Prerequisites
- Java 17 JDK or higher
- PostgreSQL 15+ installed and running
- Maven 3.6+

## Setup & Installation

### 1. Database Configuration
Create a PostgreSQL database named `militarymanagementsystem`.
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update