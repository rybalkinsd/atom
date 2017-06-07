@echo off

:: ***************************************************
:: java-algs4
:: ------------------
:: Wrapper for java that includes algs4.jar
:: ***************************************************

java -Dfile.encoding=UTF-8 -Duser.language=en -Duser.country=EN -classpath ".;.\build\libs\tasks\1-in-memory-account-server-1.0-SNAPSHOT.jar;.\build\classes\main;" %*