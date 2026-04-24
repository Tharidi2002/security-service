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

3. **Report Service** (Port 8083)
   - Data analytics and reporting
   - Daily/Weekly/Monthly reports
   - Dashboard statistics
   - Historical data analysis

4. **Security Service** (Port 8080)
   - Core security features
   - Alert management
   - System monitoring

### Frontend

- **React Application** (Port 3000)
  - Responsive dashboard
  - Real-time notifications
  - ATM location management
  - Analytics and reporting

## Prerequisites

- Java 17+
- Maven 3.6+
- Node.js 16+
- MySQL 8.0+
- Git

## Database Setup

Create the following databases in MySQL:

```sql
CREATE DATABASE atm_auth_db;
CREATE DATABASE atm_alerts_db;
CREATE DATABASE atm_reports_db;
CREATE DATABASE atm_security_db;
```

## Installation & Setup

### Backend Services

1. **Auth Service**
```bash
cd backend/auth-service
mvn clean install
mvn spring-boot:run
```

2. **Alert Aggregator Service**
```bash
cd backend/alert-aggregator
mvn clean install
mvn spring-boot:run
```

3. **Report Service**
```bash
cd backend/report-service
mvn clean install
mvn spring-boot:run
```

4. **Security Service**
```bash
cd backend/security-service
mvn clean install
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```

## Features

### 🔐 Security Features
- **Role-Based Access Control (RBAC)**
  - Admin, Bank Manager, Security Officer, Viewer roles
  - Bank-specific data isolation
- **JWT Authentication**
  - Secure token-based authentication
  - Automatic token refresh
- **End-to-End Encryption**
  - Secure data transmission
  - Encrypted database storage

### 📊 Real-time Monitoring
- **Live Dashboard**
  - Real-time alert statistics
  - Interactive charts and graphs
  - ATM status monitoring
- **WebSocket Notifications**
  - Instant alert notifications
  - Critical alert popups
  - Real-time updates

### 🚨 Alert Management
- **SMS Alert Processing**
  - Automatic alert classification
  - Severity-based categorization
  - Alert aggregation
- **Alert Types**
  - Door openings
  - Fire alarms
  - Power failures
  - Physical tampering
  - Network disconnects
  - Cash level warnings
  - Card reader errors
  - Vandalism

### 📍 ATM Location Management
- **ATM Registration**
  - Location details
  - Contact information
  - GPS coordinates
  - Status management
- **Search and Filter**
  - By bank code
  - By city/district
  - By status
  - Location-based search

### 📈 Analytics & Reporting
- **Automated Reports**
  - Daily summaries
  - Weekly analytics
  - Monthly trends
- **Custom Reports**
  - Time-range filtering
  - Bank-specific reports
  - ATM-specific analytics

## API Endpoints

### Auth Service
- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - User login
- `POST /api/auth/validate` - Validate token
- `GET /api/atm-locations/*` - ATM location management

### Alert Aggregator Service
- `POST /api/sms-alerts/receive` - Receive SMS alert
- `GET /api/sms-alerts/unprocessed` - Get unprocessed alerts
- `GET /api/sms-alerts/critical` - Get critical alerts
- `GET /api/sms-alerts/dashboard` - Dashboard data

### Report Service
- `POST /api/reports/daily/{bankCode}` - Generate daily report
- `POST /api/reports/weekly/{bankCode}` - Generate weekly report
- `POST /api/reports/monthly/{bankCode}` - Generate monthly report
- `GET /api/reports/dashboard/{bankCode}` - Dashboard summary

## Demo Credentials

**Default Admin User:**
- Username: `admin`
- Password: `admin123`
- Role: `ADMIN`
- Bank Code: `BOC`

## Usage

1. **Start all microservices** in the order listed above
2. **Start the React frontend**
3. **Login** with demo credentials
4. **Navigate** through the dashboard
5. **Register ATMs** using the location management
6. **Monitor** real-time alerts

## Future Enhancements

- 🤖 **AI Integration**
  - Anomaly detection
  - Predictive analytics
  - Pattern recognition
- 📹 **CCTV Integration**
  - Live video feeds
  - Motion detection
  - Facial recognition
- 🔔 **Advanced Notifications**
  - SMS notifications
  - Email alerts
  - Mobile app push notifications
- 🗺️ **Geographic Mapping**
  - Interactive ATM maps
  - Location-based analytics
  - Route optimization

## Technology Stack

### Backend
- **Spring Boot 3.5.13**
- **Spring Security**
- **Spring Data JPA**
- **MySQL**
- **WebSocket**
- **JWT**
- **Maven**

### Frontend
- **React 18**
- **Material-UI**
- **Recharts**
- **Socket.io**
- **Axios**
- **React Router**

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and queries, please contact the development team.

---

⚡ **Built with modern microservices architecture for maximum scalability and reliability**
