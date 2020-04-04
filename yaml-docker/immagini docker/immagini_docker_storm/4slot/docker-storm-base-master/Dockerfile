FROM anthony2801/jre

# Loosely based on wurstmeister containers

ENV STORM_VERSION 1.2.2
ENV STORM_HOME /opt/apache-storm-${STORM_VERSION}

RUN wget -q -O - http://mirrors.sonic.net/apache/storm/apache-storm-${STORM_VERSION}/apache-storm-${STORM_VERSION}.tar.gz | tar -xzf - -C /opt

RUN ln -s $STORM_HOME/bin/storm /usr/bin/storm

VOLUME /mnt/storm

ADD storm.yaml $STORM_HOME/conf/storm.yaml
ADD log-config.xml $STORM_HOME/logback/cluster.xml

ENTRYPOINT ["/usr/bin/storm"]
