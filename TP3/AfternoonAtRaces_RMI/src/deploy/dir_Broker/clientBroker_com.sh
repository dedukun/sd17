#!/usr/bin/env bash
java -cp .:\* -Djava.rmi.server.codebase="file:///home/sd0202/dir_Broker/"\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.Broker.BrokerClient
