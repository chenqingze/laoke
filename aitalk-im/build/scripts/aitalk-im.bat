@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  aitalk-im startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and AITALK_IM_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\aitalk-im-2.0-SNAPSHOT.jar;%APP_HOME%\lib\log4j-slf4j-impl-2.13.3.jar;%APP_HOME%\lib\log4j-core-2.13.3.jar;%APP_HOME%\lib\log4j-api-2.13.3.jar;%APP_HOME%\lib\disruptor-3.4.2.jar;%APP_HOME%\lib\aitalk-storage-2.0-SNAPSHOT.jar;%APP_HOME%\lib\spring-data-redis-2.3.3.RELEASE.jar;%APP_HOME%\lib\spring-data-keyvalue-2.3.3.RELEASE.jar;%APP_HOME%\lib\spring-context-support-5.2.8.RELEASE.jar;%APP_HOME%\lib\spring-context-5.2.8.RELEASE.jar;%APP_HOME%\lib\netty-all-4.1.51.Final.jar;%APP_HOME%\lib\protobuf-java-util-3.13.0.jar;%APP_HOME%\lib\protobuf-java-3.13.0.jar;%APP_HOME%\lib\caffeine-2.8.5.jar;%APP_HOME%\lib\jedis-3.3.0.jar;%APP_HOME%\lib\spring-data-commons-2.3.3.RELEASE.jar;%APP_HOME%\lib\slf4j-api-1.7.30.jar;%APP_HOME%\lib\spring-aop-5.2.8.RELEASE.jar;%APP_HOME%\lib\spring-tx-5.2.8.RELEASE.jar;%APP_HOME%\lib\spring-oxm-5.2.8.RELEASE.jar;%APP_HOME%\lib\spring-beans-5.2.8.RELEASE.jar;%APP_HOME%\lib\spring-expression-5.2.8.RELEASE.jar;%APP_HOME%\lib\spring-core-5.2.8.RELEASE.jar;%APP_HOME%\lib\guava-29.0-jre.jar;%APP_HOME%\lib\error_prone_annotations-2.4.0.jar;%APP_HOME%\lib\gson-2.8.6.jar;%APP_HOME%\lib\checker-qual-3.4.1.jar;%APP_HOME%\lib\mongodb-driver-sync-4.1.0.jar;%APP_HOME%\lib\lettuce-core-5.3.3.RELEASE.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.11.2.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.11.2.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.11.2.jar;%APP_HOME%\lib\jackson-databind-2.11.2.jar;%APP_HOME%\lib\spring-jcl-5.2.8.RELEASE.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\mongodb-driver-core-4.1.0.jar;%APP_HOME%\lib\bson-4.1.0.jar;%APP_HOME%\lib\commons-pool2-2.6.2.jar;%APP_HOME%\lib\netty-handler-4.1.51.Final.jar;%APP_HOME%\lib\netty-codec-4.1.51.Final.jar;%APP_HOME%\lib\netty-transport-4.1.51.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.51.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.51.Final.jar;%APP_HOME%\lib\netty-common-4.1.51.Final.jar;%APP_HOME%\lib\reactor-core-3.3.9.RELEASE.jar;%APP_HOME%\lib\jackson-annotations-2.11.2.jar;%APP_HOME%\lib\jackson-core-2.11.2.jar;%APP_HOME%\lib\reactive-streams-1.0.3.jar


@rem Execute aitalk-im
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %AITALK_IM_OPTS%  -classpath "%CLASSPATH%" com.aihangxunxi.aitalk.im.AitalkImApp %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable AITALK_IM_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%AITALK_IM_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
