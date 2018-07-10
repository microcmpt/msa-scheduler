@echo off
cd..
set "configpath=%cd%"
java -Dconfig.path.prefix="%configpath%" -jar "%configpath%"/lib/msa-scheduler-1.0.1.jar