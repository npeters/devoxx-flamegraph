FROM centos:7

COPY debuginfo.repo /debuginfo.repo
RUN cat /debuginfo.repo >> /etc/yum.repos.d/CentOS-Base.repo

RUN yum -y update && \
    yum -y install --enablerepo=debuginfo -y \
            java-1.8.0-openjdk-devel \
            java-1.8.0-openjdk-debuginfo \
            git \
            sudo \
            cmake \
            make \
            gcc \
            gcc-c++

ENV JAVA_HOME /etc/alternatives/java_sdk

RUN git clone https://github.com/jrudolph/perf-map-agent /opt/perf-map-agent

WORKDIR /opt/perf-map-agent

RUN cmake . && \
    make && \
    bin/create-links-in /usr/bin

COPY ../sample-flamegraph-app/ /sample-flamegraph-app

WORKDIR /sample-flamegraph-app/build/classes/main

VOLUME /tmp

CMD ["java", "-Xmx3g", "-XX:+PreserveFramePointer", "sample.flamegraph.tools.GCLoad"]
