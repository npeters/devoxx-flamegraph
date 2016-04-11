#!/bin/env bash
basedir=$(pwd)

cd sample-flamegraph-app
[ -d build/classes/main  ] || gradle compileJava
java -XX:+PreserveFramePointer  -cp build/classes/main sample.flamegraph.web.LockController >/tmp/lock.log &
cd ..

sleep  30

PID=$(cat /tmp/lock.pid)

sudo perf record -e 'sched:sched_stat_runtime' -ag -- sleep 10

/opt/perf-map-agent/bin/create-java-perf-map.sh $PID

kill $PID

sudo perf script -f -s ./perf-stat-runtime-script.py |\
    ./stackcollapse-perf-offcpu.pl |\
    /opt/FlameGraph/flamegraph.pl --countname=ms --title="Off-CPU Time Flame Graph" --colors=io > offcpu.svg
