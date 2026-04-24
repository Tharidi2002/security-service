# VS Code Setup Instructions for ATM Security System

## මෙම setup එක ඔබගේ E Drive එකේ තිබෙන project එක VS Code එකෙන් run කිරීමට අවශ්‍ය වේ

## අවශ්‍ය Software ස්ථාපනය

### 1. Java Development Kit (JDK) 17
```
Download: https://adoptium.net/
Installation Path: C:\Program Files\Java\jdk-17
```

### 2. Maven
```
Download: https://maven.apache.org/download.cgi
Installation Path: C:\Users\USER\.m2\wrapper\apache-maven-3.9.6
```

### 3. VS Code Extensions
```
- Extension Pack for Java
- Spring Boot Extension Pack
- Maven for Java
- ES7+ React/Redux/React-Native snippets
- ES6 String HTML
```

## VS Code සැකසීම

### 1. Project එක VS Code එකෙන් විවෘත කරන්න:
```
File -> Open Folder -> E:\Projects\ATM_Security_System
```

### 2. Maven ස්වයංක්‍රීයව import කිරීම:
- VS Code එක පහළ Maven tab එක open වෙනු ඇත
- "Reload All Maven Projects" ක්ලික් කරන්න
- Dependencies එක් එක් වෙනු ඇත

### 3. Terminal සැකසීම:
VS Code එකේ Terminal එක open කරන්න (Ctrl + `) සහ මේක run කරන්න:
```bash
echo $env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
echo $env:MAVEN_HOME = "C:\Users\USER\.m2\wrapper\apache-maven-3.9.6"
```

## Services Run කිරීම

### 1. Auth Service (Port 8081)
```bash
cd backend\auth-service
mvn spring-boot:run
```

### 2. Alert Aggregator Service (Port 8082)
```bash
cd backend\alert-aggregator
mvn spring-boot:run
```

### 3. Report Service (Port 8083)
```bash
cd backend\report-service
mvn spring-boot:run
```

### 4. Security Service (Port 8080)
```bash
cd backend\security-service
mvn spring-boot:run
```

### 5. Frontend (Port 3000)
```bash
cd frontend
npm install
npm start
```

## Database Setup

MySQL ස්ථාපනය කර මේ databases හදන්න:
```sql
CREATE DATABASE atm_auth_db;
CREATE DATABASE atm_alerts_db;
CREATE DATABASE atm_reports_db;
CREATE DATABASE atm_security_db;
```

## Troubleshooting

### Maven නොමැති නම්:
1. Maven download කර C:\Users\USER\.m2\wrapper\apache-maven-3.9.6 වෙත extract කරන්න
2. Environment variables add කරන්න:
   - MAVEN_HOME = C:\Users\USER\.m2\wrapper\apache-maven-3.9.6
   - PATH = %MAVEN_HOME%\bin

### Java නොමැති නම්:
1. JDK 17 download කර C:\Program Files\Java\jdk-17 වෙත install කරන්න
2. Environment variables add කරන්න:
   - JAVA_HOME = C:\Program Files\Java\jdk-17
   - PATH = %JAVA_HOME%\bin

### VS Code එකේ Maven නොපෙනුනොත්:
1. Command Palette (Ctrl+Shift+P) open කරන්න
2. "Maven: Reload All Maven Projects" සොයන්න
3. Enter ඔබන්න

## Test කිරීම

1. සියලුම services start කරන්න
2. Browser එකෙන් http://localhost:3000 open කරන්න
3. Login කරන්න:
   - Username: admin
   - Password: admin123

## Tips

- VS Code එකේ Integrated Terminal එක භාවිතා කිරීම හොඳයි
- Maven dependencies එක් වෙනතුරු ඉන්න ඕනේ
- Services එක් එක් වෙනුවට වෙන් VS Code terminal windows භාවිතා කරන්න
- Database connection errors ආවොත් MySQL service එක run වන්නේ නැද්ද බලන්න
