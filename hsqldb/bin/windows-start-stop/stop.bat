@echo off
:: Stop HSQLDB server on Windows
:: This script attempts to shut down a running HSQLDB server instance.
:: Usage: stop.bat [shutdown options]

cd /d "%~dp0"
cd ..\..\data

REM use SqlTool to connect to the server and issue SHUTDOWN command
java -jar ..\lib\sqltool.jar --inlineRc=url=jdbc:hsqldb:hsql://localhost,user=SA,password= --sql="SHUTDOWN;" %*
