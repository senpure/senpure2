@echo on
set APP_HOME=%cd%
echo APP_HOME=%APP_HOME%
echo Path=%Path%
start %APP_HOME%\jre1.8\bin\javaw  -jar senpure-generator-1.0-SNAPSHOT.jar

cls
@echo off
echo 程序启动中请稍后
ping /n 1 127.1 >nul
cls
echo 程序启动中请稍后。
ping /n 2 127.1 >nul
cls
echo 程序启动中请稍后。。
ping /n 2 127.1 >nul
