#!/usr/bin/env bash
java -cp .:\* -Djava.rmi.server.codebase="http://l040101-ws10.ua.pt/sd0202/classes/"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     registry.ServerRegisterRemoteObject
