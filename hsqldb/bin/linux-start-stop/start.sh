#!/bin/sh
# Start HSQLDB server on Unix/Linux
# Usage: start.sh [server options]

# change to script directory then to data folder
cd "$(dirname "$0")"
cd ../data

# launch the server; pass through any additional parameters
java -classpath ../lib/hsqldb.jar org.hsqldb.server.Server "$@"
