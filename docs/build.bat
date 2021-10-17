-REM Mr. A. Maganlal/Ms M Fourie
REM Computer Science 2A/2B 2021
REM This batch file is setup to be more robust than previous versions

REM Turn echo off and clear the screen
@echo off
cls

REM Good batch file coding practice
setlocal enabledelayedexpansion

REM Paths for Java
echo Setup JDK bin path before this script
set JAVA_HOME=C:\Users\Thalukanyo\Documents\OpenJDK_winx64\jdk-11.0.10+9
set JAVAFX_HOME=C:\Users\Thalukanyo\Documents\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2
set JAVAFX_MODULES=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media
set PATH=%JAVA_HOME%\bin;%PATH%

REM Move to correct folder
echo Build script set to run in Project folder like eclipse
cd ..

REM Variables for batch
set ERRMSG=
set PRAC_BIN=.\bin
set PRAC_DOCS=.\docs
set PRAC_LIB=.\lib\*
set PRAC_SRC=.\src

REM Clean all class files from bin folder
:CLEAN
echo ~~~ Cleaning project ~~~
DEL /S %PRAC_BIN%\*.class
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error cleaning project"
    GOTO ERROR
)

:VERSION
echo ~~~ Checking Version ~~~
javac -version
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error checking version"
    GOTO ERROR
)
java -version
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error checking version"
    GOTO ERROR
)

REM Compile project by compiling just Main. Main will reference required classes
:COMPILE
echo ~~~ Compiling project ~~~
javac --module-path %JAVAFX_HOME%\lib --add-modules=%JAVAFX_MODULES% -sourcepath %PRAC_SRC% -cp "%PRAC_BIN%;%PRAC_LIB%" -d %PRAC_BIN% %PRAC_SRC%\Main.java
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error compiling project"
    GOTO ERROR
)

REM Generate JavaDoc for project
:JAVADOC
echo ~~~ Generate JavaDoc for project ~~~
javadoc --module-path %JAVAFX_HOME%\lib --add-modules=%JAVAFX_MODULES% -sourcepath %PRAC_SRC% -classpath %PRAC_BIN%;%PRAC_LIB% -use -version -author -d %PRAC_DOCS%\JavaDocs -subpackages acsse
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error generating JavaDoc for project"
    GOTO ERROR
)

REM Run project by running Main
:RUN
echo ~~~ Running project ~~~
java --module-path %JAVAFX_HOME%\lib --add-modules=%JAVAFX_MODULES% -cp %PRAC_BIN%;%PRAC_LIB% Main
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error running project"
    GOTO ERROR
)
GOTO END

REM Something went wrong, display error
:ERROR
echo Fatal error with project
echo %ERRMSG%

REM Move back to docs folder and wait
:END
echo ~~~ End ~~~
cd %PRAC_DOCS%
pause