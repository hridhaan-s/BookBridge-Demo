@echo off
setlocal
cd /d "%~dp0"
where java >nul 2>nul
if errorlevel 1 (
    echo.
    echo Java was not found.
    echo Install JDK 17 or newer, then run this file again.
    echo.
    pause
    exit /b 1
)
if not exist out mkdir out
javac -d out BookBridgeDemo.java
if errorlevel 1 (
    echo.
    echo Compilation failed. Make sure JDK (not just JRE) is installed.
    echo.
    pause
    exit /b 1
)
java -cp out BookBridgeDemo
