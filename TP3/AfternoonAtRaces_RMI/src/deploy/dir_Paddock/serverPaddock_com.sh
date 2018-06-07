#!/usr/bin/env bash
java -cp .:\* -Djava.rmi.server.codebase="http://l040101-ws10.ua.pt/sd0202/classes/"\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     serverSide.Paddock.PaddockServer
