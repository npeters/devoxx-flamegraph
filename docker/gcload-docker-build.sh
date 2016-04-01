#!/bin/env bash
set -e

BASEDIR=$(dirname "$0")
cd $BASEDIR/../sample-flamegraph-app
gradle jar

cd $BASEDIR
sudo docker build -t sample-flamegraph-app .
