#!/bin/env bash
sudo perf record -e sched:sched_stat_sleep -e sched:sched_switch  -e sched:sched_process_exit -a -g -o /tmp/perf-offcpu.data.raw sleep 5
sudo perf inject -v -s -f -i perf-offcpu.data.raw -o /tmp/perf-offcpu.data
perf script -i /tmp/perf-offcpu.data   -F comm,pid,tid,cpu,time,period,event,ip,sym,dso,trace | awk '
    NF > 4 { exec = $1; period_ms = int($5 / 1000000) }
    NF > 1 && NF <= 4 && period_ms > 0 { print $2 }
    NF < 2 && period_ms > 0 { printf "%s\n%d\n\n", exec, period_ms }' | $FLAMEGRAPH_DIR/FlameGraph/stackcollapse.pl |\
     $FLAMEGRAPH_DIR/FlameGraph/flamegraph.pl --countname=ms --title="Off-CPU Time Flame Graph" --colors=io > flamegraph-output/cpuoff.svg
