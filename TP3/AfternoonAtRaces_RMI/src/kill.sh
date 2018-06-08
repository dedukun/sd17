#!/bin/bash

if [ -z "$1" ]; then
    printf "Enter machines password: "
    read -s PASSWORD
else
    PASSWORD=$1
fi

registryMachine="l040101-ws10.ua.pt"
registry="registry"

serversMachines=( "l040101-ws01.ua.pt" "l040101-ws02.ua.pt" "l040101-ws03.ua.pt"
"l040101-ws06.ua.pt" "l040101-ws04.ua.pt" "l040101-ws05.ua.pt" )
clientsMachines=( "l040101-ws09.ua.pt" "l040101-ws08.ua.pt" "l040101-ws10.ua.pt" )

servers=( "GenRepos" "BettingCenter" "ControlCenter" "Paddock" "RaceTrack" "Stable" )
clients=( "HorseJockey" "Spectators" "Broker" )


printf "\nKill Registry... "

# Kill rmi registry
sshpass -p $PASSWORD ssh sd0202@$registryMachine "kill \$(ps aux | grep sd0202 | grep 'rmiregistry' | awk '{print \$2}')"


# Kill registry
sshpass -p $PASSWORD ssh sd0202@$registryMachine "kill \$(ps aux | grep sd0202 | grep 'java *' | awk '{print \$2}')"

echo "done"


printf "\nKill Servers...\n"
for i in $(seq 0 $((${#servers[@]}-1)))
do
    printf "  "${servers[$i]}"... "

    # Kill if running
    sshpass -p $PASSWORD ssh sd0202@${serversMachines[$i]} "kill \$(ps aux | grep sd0202 | grep 'java *' | awk '{print \$2}')"


    echo "done"
done

printf "\nKill Clients...\n"
for i in $(seq 0 $((${#clients[@]}-1)))
do
    printf "  "${clients[$i]}"... "


    # Kill if running
    sshpass -p $PASSWORD ssh sd0202@${clientsMachines[$i]} "kill \$(ps aux | grep sd0202 | grep 'java *' | awk '{print \$2}')"

    echo "done"
done

echo "All done"
