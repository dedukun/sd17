#!/bin/bash

printf "Enter machines password: "
read -s PASSWORD

registryMachine="l040101-ws10.ua.pt"
registry="registry"

serversMachines=( "l040101-ws02.ua.pt" "l040101-ws03.ua.pt" "l040101-ws01.ua.pt"
"l040101-ws06.ua.pt" "l040101-ws04.ua.pt" "l040101-ws05.ua.pt" )
clientsMachines=( "l040101-ws10.ua.pt" "l040101-ws09.ua.pt" "l040101-ws08.ua.pt" )

servers=( "BettingCenter" "ControlCenter" "GenRepos" "Paddock" "RaceTrack" "Stable" )
clients=( "Broker" "HorseJockey" "Spectators" )


printf "\nRun Registry...\n"

printf "  "$registry"... "

# Kill if running
sshpass -p $PASSWORD ssh sd0202@$registryMachine "kill \$(ps aux | grep sd0202 | grep 'java
registry.ServerRegisterRemoteObject' | awk '{print \$2}' | head -n 1)"

# run in remote machine
sshpass -p $PASSWORD ssh sd0202@$registryMachine "cd dir_$registry; ./registry_com.sh > reg.out 2> reg.err < /dev/null &"

echo "done"

sleep 2

printf "\nRun Servers...\n"
for i in $(seq 0 $((${#servers[@]}-1)))
do
    printf "  "${servers[$i]}"... "

    sleep 1

    # Kill if running
    sshpass -p $PASSWORD ssh sd0202@${serversMachines[$i]} "kill \$(ps aux | grep sd0202 | grep 'java
    serverSide.*' | awk '{print \$2}' | head -n 1)"

    # run in remote machine
    sshpass -p $PASSWORD ssh sd0202@${serversMachines[$i]} "cd dir_${servers[$i]}; ./server${servers[$i]}_com.sh > server.out 2> server.err < /dev/null &"

    echo "done"
done

printf "\nRun Clients...\n"
for i in $(seq 0 $((${#clients[@]}-1)))
do
    printf "  "${clients[$i]}"... "

    sleep 2

    # Kill if running
    sshpass -p $PASSWORD ssh sd0202@${clientsMachines[$i]} "kill \$(ps aux | grep sd0202 | grep 'java
    clientSide.*' | awk '{print \$2}' | head -n 1)"

    # run in remote machine
    sshpass -p $PASSWORD ssh sd0202@${clientsMachines[$i]} "cd dir_${clients[$i]}; ./client${clients[$i]}_com.sh > client.out 2> client.err < /dev/null &"

    echo "done"
done

echo "All done"
