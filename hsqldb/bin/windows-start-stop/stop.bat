@echo off
:: Stop HSQLDB server on Windows
:: This script attempts to shut down a running HSQLDB server instance.
:: Usage: stop.bat [shutdown options]

cd /d "%~dp0"
cd ..\data

REM the Server class understands --shutdown which connects to a running
REM server and issues the shutdown command. additional options such as
REM --database.0 may be specified if needed.
java -classpath ..\lib\hsqldb.jar org.hsqldb.server.Server --shutdown %*
