@echo off
@REM Maven Start Up Batch script

@REM Try SDKMAN Java first
if exist "C:\Users\USER\.sdkman\candidates\java\current\bin\java.exe" (
    set JAVA_HOME=C:\Users\USER\.sdkman\candidates\java\current
    set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
    goto initMaven
)

@REM -- use "java.exe" if JAVA_HOME is not defined
if not "%JAVA_HOME%" == "" goto OkJavaHome
set JAVA_EXE=java.exe
goto initMaven

:OkJavaHome
set JAVA_EXE="%JAVA_HOME%\bin\java.exe"

:initMaven

set MAVEN_PROJECTBASEDIR=%MAVEN_BASEDIR%
IF NOT "%MAVEN_PROJECTBASEDIR%"=="" goto endDetectBaseDir

set EXEC_DIR=%CD%
set WDIR=%EXEC_DIR%
:findBaseDir
IF EXIST "%WDIR%"\.mvn goto baseDirFound
cd ..
IF "%WDIR%"=="%CD%" goto baseDirNotFound
set WDIR=%CD%
goto findBaseDir

:baseDirFound
set MAVEN_PROJECTBASEDIR=%WDIR%
cd "%EXEC_DIR%"
goto endDetectBaseDir

:baseDirNotFound
set MAVEN_PROJECTBASEDIR=%EXEC_DIR%
cd "%EXEC_DIR%"

:endDetectBaseDir

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

set DOWNLOAD_URL="https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"

if exist %WRAPPER_JAR% (
    echo Found %WRAPPER_JAR%
) else (
    echo Downloading Maven wrapper...
    powershell -Command "Invoke-WebRequest -Uri %DOWNLOAD_URL% -OutFile %WRAPPER_JAR%"
)

set MAVEN_CMD_LINE_ARGS=%*

"%JAVA_EXE%" -cp %WRAPPER_JAR% "-Dmaven.home=%MAVEN_HOME%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %WRAPPER_LAUNCHER% %MAVEN_CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
exit /B %ERROR_CODE%
