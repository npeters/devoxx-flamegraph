PID=$(cat /tmp/gcload.pid)
PERF_RECORD_SECONDS=5 PERF_FLAME_OUTPUT=$basedir/gc-flamegraph.svg  perf-java-flames $PID
