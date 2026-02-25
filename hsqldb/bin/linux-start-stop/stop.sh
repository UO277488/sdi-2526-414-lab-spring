#!/bin/sh
# Stop HSQLDB server on Unix/Linux
# Usage: stop.sh [shutdown options]

cd "$(dirname "$0")"
cd ../../data

# use SqlTool to connect to the server and issue SHUTDOWN command
java -jar ../lib/sqltool.jar --inlineRc=url=jdbc:hsqldb:hsql://localhost,user=SA,password= --sql="SHUTDOWN;" "$@"
