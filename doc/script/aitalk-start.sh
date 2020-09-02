#!/usr/bin/env bash
nohup java \
 -Djava.rmi.server.hostname=192.168.100.223 \
 -Dcom.sun.management.jmxremote=true \
 -Dcom.sun.management.jmxremote.local.only=false \
 -Dcom.sun.management.jmxremote.port=6789   \
 -Dcom.sun.management.jmxremote.ssl=false  \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dfile.encoding=utf-8 \
 -Dio.netty.leakDetectionLevel=paranoid \
 -jar "$1" >  /opt/aihangxunxi/logs/aitalk.log 2>&1 &
