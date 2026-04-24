@echo off
echo Setting up Maven for ATM Security System...
echo.

REM Create Maven directory
if not exist "C:\Users\USER\.m2\wrapper" mkdir "C:\Users\USER\.m2\wrapper"

REM Download Maven
echo Downloading Apache Maven...
powershell -Command "Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip' -OutFile 'C:\Users\USER\.m2\wrapper\maven.zip'"

REM Extract Maven
echo Extracting Maven...
powershell -Command "Expand-Archive -Path 'C:\Users\USER\.m2\wrapper\maven.zip' -DestinationPath 'C:\Users\USER\.m2\wrapper' -Force"

REM Set Environment Variables
echo Setting Environment Variables...
setx MAVEN_HOME "C:\Users\USER\.m2\wrapper\apache-maven-3.9.6"
setx PATH "%PATH%;C:\Users\USER\.m2\wrapper\apache-maven-3.9.6\bin"

REM Test Maven
echo Testing Maven installation...
C:\Users\USER\.m2\wrapper\apache-maven-3.9.6\bin\mvn.cmd --version

echo.
echo Maven setup complete!
echo Please restart your terminal and try again.
pause
