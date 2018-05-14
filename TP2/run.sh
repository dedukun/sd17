#!/bin/bash

printf "Machines password: "
read -s PASSWORD

servers=( "BettingCenterServer" "ControlCenterServer" "GenReposServer" "PaddockServer" "RaceTrackServer" "StableServer" )
serversMachines=( "l040101-ws01.ua.pt" "l040101-ws03.ua.pt" "l040101-ws04.ua.pt" "l040101-ws06.ua.pt" "l040101-ws09.ua.pt" "l040101-ws10.ua.pt" )

printf "\nBuilding Servers...\n"

for i in $(seq 0 $((${#servers[@]}-1)))
do
    printf "  "${servers[$i]}"... "

    # Kill if running
    sshpass -p $PASSWORD ssh sd0202@${serversMachines[$i]} "kill \$(ps aux | grep sd0202 | grep 'java
    Main.*' | awk '{print \$2}' | head -n 1)"

    # run in remote machine
    sshpass -p $PASSWORD ssh sd0202@${serversMachines[$i]} "cd ${servers[$i]}; nohup java Main.${servers[$i]} > server.out 2> server.err < /dev/null &"

    echo "done"
done

clients=( "HorseJockeyClient" "SpectatorClient" "BrokerClient")
clientsMachines=( "l040101-ws05.ua.pt" "l040101-ws08.ua.pt" "l040101-ws02.ua.pt")

printf "\nBuilding Clients...\n"

for i in $(seq 0 $((${#clients[@]}-1)))
do
    printf "  "${clients[$i]}"... "

    sleep 1

    # Kill if running
    sshpass -p $PASSWORD ssh sd0202@${clientsMachines[$i]} "kill \$(ps aux | grep sd0202 | grep 'java
    Main.*' | awk '{print \$2}' | head -n 1)"

    # run in remote machine
    sshpass -p $PASSWORD ssh sd0202@${clientsMachines[$i]} "cd ${clients[$i]}; nohup java Main.${clients[$i]} > client.out 2> client.err < /dev/null &"

    echo "done"
done

echo "All done"
