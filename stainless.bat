@echo off

rem Absolute path of the script: https://stackoverflow.com/a/33372703
pushd %~dp0
set script_dir=%CD%
popd

set PATH=%PATH%;%script_dir%\z3

java -jar %script_dir%\lib\stainless-dotty-standalone-0.9.7-35-g4c973e7-SNAPSHOT.jar %*