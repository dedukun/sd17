#!/usr/bin/env bash
java -cp .:\* -Djava.rmi.server.codebase="file:///home/sd0202/dir_Spectators/"\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.Spectators.SpectatorsClient
