#!/usr/bin/env bash

yum install -y git unzip

# les sources du kernel linux et de glibc
yum install -y kernel-devel glibc-devel
# les debuginfo (les symbols) du kernel linux et de glibc
yum install --enablerepo=base-debuginfo -y kernel-debuginfo.x86_64  kernel-debuginfo-common-x86_64.x86_64 kernel-tools-debuginfo.x86_64  glibc-debuginfo

# perf est un outil fournie dans le kernel linux pour capturer les d'evenements system
yum install -y perf

#jdk 1.8_77
yum install -y  java-1.8.0-openjdk-devel
yum install --enablerepo=base-debuginfo -y java-1.8.0-openjdk-debuginfo

#Projet FlameGraph de brendangregg
[ -d /opt/FlameGraph ] ||  git clone https://github.com/brendangregg/FlameGraph /opt/FlameGraph

# Projet perf-map-agent
# agent java permettant de d'extraire les symbols de la JVM
[ -d /opt/perf-map-agent ] ||  git clone https://github.com/jrudolph/perf-map-agent /opt/perf-map-agent
yum -y install cmake gcc-c++
cd /opt/perf-map-agent
# construction de code c contenue dans l'agent java
export JAVA_HOME=/etc/alternatives/java_sdk
cmake .
make
# instalation d'un ensemble de commande dont "perf-java-flames"
bin/create-links-in /usr/bin



[ -f /tmp/gradle-2.12-all.zip ] ||  wget -O /tmp/gradle-2.12-all.zip https://services.gradle.org/distributions/gradle-2.12-all.zip
cd /opt
unzip /tmp/gradle-2.12-all.zip
