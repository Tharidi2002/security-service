@echo off
echo Setting JAVA_HOME for SDKMAN Java...
echo.

REM Set JAVA_HOME permanently
setx JAVA_HOME "C:\Users\USER\.sdkman\candidates\java\current"
setx PATH "%PATH%;C:\Users\USER\.sdkman\candidates\java\current\bin"

REM Set for current session
set JAVA_HOME=C:\Users\USER\.sdkman\candidates\java\current
set PATH=%PATH%;C:\Users\USER\.sdkman\candidates\java\current\bin

echo JAVA_HOME set to: %JAVA_HOME%
echo.
echo Testing Java...
java -version
echo.
echo Testing Maven...
C:\Users\USER\.sdkman\candidates\java\current\bin\java.exe -version

echo.
echo JAVA_HOME setup complete!
echo Please restart your terminal and try running:
echo cd backend\auth-service
echo .\mvnw.cmd spring-boot:run
pause
