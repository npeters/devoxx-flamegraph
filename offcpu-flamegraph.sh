#!/bin/env bash
PID=$1
sudo perf record -e 'sched:sched_stat_runtime' -ag -- sleep 10
/opt/perf-map-agent/bin/create-java-perf-map.sh $PID
sudo perf script -f -s ./perf-stat-runtime-script.py |\
    ./stackcollapse-perf-offcpu.pl |\
    /opt/FlameGraph/flamegraph.pl --countname=ms --title="Off-CPU Time Flame Graph" --colors=io > cpuoff.svg
