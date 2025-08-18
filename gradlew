#!/bin/sh
# Simplified gradlew script
DIR="$( cd "$( dirname "$0" )" && pwd )"
exec ./gradlew "$@"
