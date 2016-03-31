#!/bin/env bash
PID=$1
sudo perf sched  record -a -g -o /tmp/perf-offcpu.data.raw sleep 15 

/opt/perf-map-agent/bin/create-java-perf-map.sh $PID 

sudo perf inject -v -s -f -i /tmp/perf-offcpu.data.raw -o /tmp/perf-offcpu.data
sudo perf script -i /tmp/perf-offcpu.data   -F comm,pid,tid,cpu,time,period,event,ip,sym,dso,trace | awk '
    NF > 4 { exec = $1; period_ms = int($5 / 1000000) }
    NF > 1 && NF <= 4 && period_ms > 0 {  str=$2;  sub(/;/, "", str);  print str }
    NF < 2 && period_ms > 0 { printf "%s\n%d\n\n", exec, period_ms }' | $FLAMEGRAPH_DIR/stackcollapse.pl | grep java  | grep -vi safepoint | \
     $FLAMEGRAPH_DIR/flamegraph.pl --countname=ms --title="Off-CPU Time Flame Graph" --colors=io > cpuoff.svg
