@echo off
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

::echo %fileName%
start javaw -jar %fileName%
echo �������������Ժ�
ping /n 1 127.1 >nul
cls
echo �������������Ժ�
ping /n 2 127.1 >nul
cls
echo �������������Ժ󡣡�
ping /n 2 127.1 >nul
:end