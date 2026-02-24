@echo off
:: Start HSQLDB server on Windows
:: Usage: start.bat [server options]

REM switch to distribution root (bin is sibling of data and lib)
cd /d "%~dp0"
cd ..\data

REM delegate to the packaged server launcher
java -classpath ..\lib\hsqldb.jar org.hsqldb.server.Server %*
