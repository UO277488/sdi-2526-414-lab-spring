#!/bin/sh
# Stop HSQLDB server on Unix/Linux
# Usage: stop.sh [shutdown options]

cd "$(dirname "$0")"
cd ../data

# use the server class's shutdown command
java -classpath ../lib/hsqldb.jar org.hsqldb.server.Server --shutdown "$@"
