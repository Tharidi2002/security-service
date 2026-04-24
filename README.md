# ATM Security System

A comprehensive microservices-based ATM security monitoring system with real-time alerts, responsive dashboard, and advanced analytics.

## Architecture

### Microservices

1. **Auth Service** (Port 8081)
   - User authentication and authorization
   - Role-Based Access Control (RBAC)
   - JWT token management
   - ATM location management

2. **Alert Aggregator Service** (Port 8082)
   - SMS alert collection from ATMs
   - Real-time alert processing
   - WebSocket notifications
   - Alert severity classification

3. **Security Service** (Port 8080)
   - Manages the lifecycle of security incidents, status tracking, and manual intervention logs.
   - Advanced alert management and incident tracking
   - Enterprise-grade error handling and resource validation

4. **Report Service** (Port 8083)
   - Generates analytics, daily summaries, and performance reports for each ATM.
   - Automated scheduled reporting via email

### Frontend

- **React Application** (Port 3000)
  - Professional Dark Mode UI with high-performance charts
  - Real-time monitoring dashboard with WebSocket integration
  - Comprehensive ATM location management (CRUD)

## Technologies Used

- **Spring Boot**: Backend microservices framework.
- **React**: Frontend user interface library.
- **Material UI**: UI component framework for professional design.
- **MySQL**: Relational database for data persistence.
- **WebSocket (Socket.io)**: Real-time bi-directional communication.
- **JWT**: Secure stateless authentication.

## Key Features

- **Real-time Monitoring**: Instant visibility into ATM security events via WebSockets.
- **JWT Authentication**: Secure access control for all system components.
- **Automated Email Reporting**: Scheduled security summaries sent automatically to stakeholders.
- **ATM Location Management**: Full lifecycle management of ATM stations and contact details.

## How to Start

### 1. Database Setup
Ensure MySQL is running and create the necessary databases:
```sql
CREATE DATABASE atm_auth_db;
CREATE DATABASE atm_alerts_db;
CREATE DATABASE atm_reports_db;
CREATE DATABASE atm_security_db;
```

### 2. Run Backend Microservices
Navigate to each service directory in the `backend` folder and run the following commands:
```bash
# Repeat for: auth-service, alert-aggregator, security-service, report-service
./mvnw spring-boot:run
```

### 3. Run Frontend
Navigate to the `frontend` directory and start the React application:
```bash
npm install
npm start
```
The application will be accessible at `http://localhost:3000`.

---
⚡ **Professional ATM Security Monitoring Solution**