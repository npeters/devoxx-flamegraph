#!/bin/env bash
set -e

BASEDIR=$(dirname "$0")

sudo docker run --name gcLoadFlamegraph --rm -v /tmp:/tmp sample-flamegraph-app
