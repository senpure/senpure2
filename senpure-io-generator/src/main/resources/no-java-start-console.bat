@echo off

set currentPath=%cd%
:echo %currentPath%
set fileName=""
for  %%a in (*) do (
   echo %%a|find /i "senpure-generator-" >nul && set fileName=%%a
)
if %fileName% =="" (
    echo "没有找到可运行的jar文件"
    goto :end
)
set APP_HOME=%cd%
:echo APP_HOME=%APP_HOME%
echo %fileName%
call %APP_HOME%\jre1.8\bin\java  -jar %fileName%
:end
pause

