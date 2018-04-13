@echo on
set currentPath=%cd%
::echo %currentPath%
set fileName=""
for  %%a in (*) do (
   echo %%a|find /i "senpure-generator-" >nul && set fileName=%%a
)
if %fileName% =="" (
    echo "没有找到可运行的jar文件"
    goto :end
)
set APP_HOME=%cd%
::echo APP_HOME=%APP_HOME%
echo %fileName%
start %APP_HOME%\jre1.8\bin\javaw  -jar %fileName%

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
:end
