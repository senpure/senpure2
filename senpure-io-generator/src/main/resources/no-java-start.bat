@echo on
set currentPath=%cd%
::echo %currentPath%
set fileName=""
for  %%a in (*) do (
   echo %%a|find /i "senpure-generator-" >nul && set fileName=%%a
)
if %fileName% =="" (
    echo "û���ҵ������е�jar�ļ�"
    goto :end
)
set APP_HOME=%cd%
::echo APP_HOME=%APP_HOME%
echo %fileName%
start %APP_HOME%\jre1.8\bin\javaw  -jar %fileName%

cls
@echo off
echo �������������Ժ�
ping /n 1 127.1 >nul
cls
echo �������������Ժ�
ping /n 2 127.1 >nul
cls
echo �������������Ժ󡣡�
ping /n 2 127.1 >nul
:end
