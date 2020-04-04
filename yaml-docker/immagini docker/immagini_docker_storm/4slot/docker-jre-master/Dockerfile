FROM centos:latest

ENV JAVA_VERSION 1.8.0

RUN yum install -y \
  java-${JAVA_VERSION}-openjdk-headless.x86_64 \
  wget \
  unzip \
  python

ENV JAVA_HOME /usr/lib/jvm/jre
