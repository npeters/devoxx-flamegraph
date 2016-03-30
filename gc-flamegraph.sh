#!/bin/env bash
cd sample-flamegraph-app/build/classes/main
java -Xmx3g sample.flamegraph.tools.GCLoad
