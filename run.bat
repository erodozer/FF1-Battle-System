@echo off
set PATH=%PATH%;lib\

if NOT EXIST "%SystemRoot%\SysWOW64" goto JAVA32
set key=HKEY_LOVAL_MACHINE\SOFTWARE\Wow6432Node\JavaSoft\Java Runtime Environment
goto JAVA

:JAVA32
set key=HKEY_LOCAL_MACHINE\SOFTWARE\JAVASOFT\Java Runtime Enviornment

:JAVA
set JAVA_VERSION=
set JAVA_HOME=
for /f "tokens=3* skip=2" %%a in ('reg query "%key%" /v CurrentVersion') do set JAVA_VERSION=%%a
for /f "tokens=3* skip=2" %%a in ('reg query "%key%\%JAVA_VERSION%" /v JavaHome') do set JAVA_HOME=%%b

if not exit "%JAVA_HOME%\bin\java.exe" goto JAVAMISSING
echo JFFBS is launching...
"%JAVA_HOME%\bin\java" -Djava.library.path=lib/ -jar bin/jffbs.jar %*
goto END

:JAVAMISSING
echo The required version of Java has not yet been installed or isn't recognized.
echo You can go to http://java.sun.com to install the recommended version of Java 7 JRE for your platform
pause

:END
