@echo off

SET JAR_FILE=%PROJECT_NAME%-%PROJECT_VERSION%.jar

echo ============ UNINSTALLATION ============
echo [%PROJECT_NAME%]: Checking the presence of the file '%JAR_FILE%'...
if not exist "%JAR_FILE%" (
    echo [%PROJECT_NAME%]: Error - File '%JAR_FILE%' not found. Please unzip all files from the archive.
    goto end
) else (
    echo [%PROJECT_NAME%]: File '%JAR_FILE%' found.
)

echo [%PROJECT_NAME%]: Starting %JAR_FILE%...
echo.

java -jar ./%JAR_FILE% --uninstall

echo [%PROJECT_NAME%]: Completing the uninstallation...

:end
pause
