@echo off
echo Starting ATM Security System Setup...
echo.

REM Set JAVA_HOME for this session
set JAVA_HOME=C:\Users\USER\.sdkman\candidates\java\current
set PATH=%PATH%;C:\Users\USER\.sdkman\candidates\java\current\bin

echo JAVA_HOME set to: %JAVA_HOME%
echo.

REM Test Java
echo Testing Java installation...
java -version
echo.

echo Starting Auth Service...
echo.
cd /d "E:\Projects\ATM_Security_System\backend\auth-service"
echo Current directory: %CD%
echo.
echo Running: .\mvnw.cmd spring-boot:run
echo.
.\mvnw.cmd spring-boot:run

pause
