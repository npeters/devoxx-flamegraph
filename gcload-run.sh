#!/bin/env bash
basedir=$(pwd)
cd sample-flamegraph-app
gradle compileJava 
cd build/classes/main
java -Xmx3g -XX:+PreserveFramePointer  sample.flamegraph.tools.GCLoad &

sleep 2

PID=$(cat /tmp/gcload.pid)
PERF_RECORD_SECONDS=5 PERF_FLAME_OUTPUT=$basedir/gc-flamegraph.svg  perf-java-flames $PID

