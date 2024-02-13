#!/bin/bash

JAR_FILE="%PROJECT_NAME%-%PROJECT_VERSION%.jar"

echo "============ UNINSTALLATION ============"
echo "[%PROJECT_NAME%]: Checking the presence of the file '${JAR_FILE}'..."
if [ ! -f "$JAR_FILE" ]; then
    echo "[%PROJECT_NAME%]: Error - File '${JAR_FILE}' not found. Please unzip all files from the archive."
    exit 1
else
    echo "[%PROJECT_NAME%]: File '${JAR_FILE}' found."
fi

echo "[%PROJECT_NAME%]: Starting ${JAR_FILE}..."
java -jar ./${JAR_FILE} --uninstall

echo "[%PROJECT_NAME%]: Completing the uninstallation..."
