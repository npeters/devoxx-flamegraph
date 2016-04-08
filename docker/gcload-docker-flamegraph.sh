#!/bin/env bash
set -e

BASEDIR=$(dirname "$0")

PID_IN_CONTAINER=$(cat /tmp/gcload.pid)

PID_IN_HOST=$(ps -aef | grep GCLoad | grep java | sed 's/root[ ]*\([0-9]*\) .*/\1/g')

PERF_RECORD_SECONDS=5
PERF_MAP_DIR=/opt/perf-map-agent
PID=$PID_IN_HOST

if [ -z "$PERF_JAVA_TMP" ]; then
  PERF_JAVA_TMP=/tmp
fi

if [ -z "$PERF_RECORD_SECONDS" ]; then
  PERF_RECORD_SECONDS=15
fi

if [ -z "$PERF_RECORD_FREQ" ]; then
  PERF_RECORD_FREQ=99
fi

if [ -z "$PERF_DATA_FILE" ]; then
  PERF_DATA_FILE=$PERF_JAVA_TMP/perf-$PID.data
fi

echo "Recording events for $PERF_RECORD_SECONDS seconds (adapt by setting PERF_RECORD_SECONDS)"
sudo perf record -F $PERF_RECORD_FREQ -o $PERF_DATA_FILE -g -p $PID -- sleep $PERF_RECORD_SECONDS

# Generate java perf map in container
sudo docker exec -it gcLoadFlamegraph /opt/perf-map-agent/bin/create-java-perf-map.sh $PID_IN_CONTAINER
sudo mv /tmp/perf-$PID_IN_CONTAINER.map /tmp/perf-$PID_IN_HOST.map


if [ -z "$PERF_JAVA_TMP" ]; then
  PERF_JAVA_TMP=/tmp
fi

STACKS=$PERF_JAVA_TMP/out-$PID.stacks
COLLAPSED=$PERF_JAVA_TMP/out-$PID.collapsed

if [ ! -x "$FLAMEGRAPH_DIR/stackcollapse-perf.pl" ]; then
  echo "FlameGraph executable not found at '$FLAMEGRAPH_DIR/stackcollapse-perf.pl'. Please set FLAMEGRAPH_DIR to the root of the clone of https://github.com/brendangregg/FlameGraph."
  exit
fi

if [ -z "$PERF_DATA_FILE" ]; then
  PERF_DATA_FILE=$PERF_JAVA_TMP/perf-$PID.data
fi

if [ -z "$PERF_FLAME_OUTPUT" ]; then
  PERF_FLAME_OUTPUT=flamegraph-$PID.svg
fi

if [ -z "$PERF_FLAME_OPTS" ]; then
    PERF_FLAME_OPTS="--color=java"
fi

sudo perf script -i $PERF_DATA_FILE > $STACKS
$FLAMEGRAPH_DIR/stackcollapse-perf.pl $STACKS | tee $COLLAPSED | $FLAMEGRAPH_DIR/flamegraph.pl $PERF_FLAME_OPTS > $PERF_FLAME_OUTPUT
echo "Flame graph SVG written to PERF_FLAME_OUTPUT='`readlink -f $PERF_FLAME_OUTPUT`'."
