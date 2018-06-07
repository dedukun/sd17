#!/bin/bash

echo "Deploying Project..."

printf "  Enter machines password: "
read -s PASSWORD

registryMachine="l040101-ws10.ua.pt"
registry="registry"

serversMachines=( "l040101-ws02.ua.pt" "l040101-ws03.ua.pt" "l040101-ws01.ua.pt"
"l040101-ws06.ua.pt" "l040101-ws04.ua.pt" "l040101-ws05.ua.pt" )
clientsMachines=( "l040101-ws10.ua.pt" "l040101-ws09.ua.pt" "l040101-ws08.ua.pt" )

servers=( "BettingCenter" "ControlCenter" "GenRepos" "Paddock" "RaceTrack" "Stable" )
clients=( "Broker" "HorseJockey" "Spectators" )

cd deploy
printf "\n  Deploy...\n"

printf "    Public... "

# send to corresponding remote machine
sshpass -p $PASSWORD sftp -q sd0202@$registryMachine: <<< $'put classes.zip'

# unzip in remote machine
sshpass -p $PASSWORD ssh sd0202@$registryMachine "rm -rf Public/classes; unzip -q classes.zip -d ./Public/; rm classes.zip;"

echo "done"

printf "    "$registry"... "

# send to corresponding remote machine
sshpass -p $PASSWORD sftp -q sd0202@$registryMachine: <<< $'put '$registry'.zip'

# unzip in remote machine
sshpass -p $PASSWORD ssh sd0202@$registryMachine "rm -rf dir_$registry; unzip -q $registry.zip; rm $registry.zip; rm set_r*; mv ./dir_$registry/set_* ."

echo "done"

for i in $(seq 0 $((${#servers[@]}-1)))
do
    printf "    "${servers[$i]}"... "

    # send to corresponding remote machine
    sshpass -p $PASSWORD sftp -q sd0202@${serversMachines[$i]}: <<< $'put '${servers[$i]}'.zip'

    # unzip in remote machine
    sshpass -p $PASSWORD ssh sd0202@${serversMachines[$i]} "rm -rf dir_${servers[$i]}; unzip -q ${servers[$i]}.zip; rm ${servers[$i]}.zip"

    echo "done"
done

for i in $(seq 0 $((${#clients[@]}-1)))
do
    printf "    "${clients[$i]}"... "

    # send to corresponding remote machine
    sshpass -p $PASSWORD sftp -q sd0202@${clientsMachines[$i]}: <<< $'put '${clients[$i]}'.zip'

    # unzip in remote machine
    sshpass -p $PASSWORD ssh sd0202@${clientsMachines[$i]} "rm -rf dir_${clients[$i]}; unzip -q ${clients[$i]}.zip; rm ${clients[$i]}.zip"

    echo "done"
done

printf "Removing .zip files..."

rm *.zip

echo "done"

cd ..
