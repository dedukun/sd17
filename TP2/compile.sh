#!/bin/bash

printf "Machines password: "
read -s PASSWORD

servers=( "BettingCenterServer" "ControlCenterServer" "GenReposServer" "PaddockServer" "RaceTrackServer" "StableServer" )
serversMachines=( "l040101-ws01.ua.pt" "l040101-ws03.ua.pt" "l040101-ws04.ua.pt" "l040101-ws06.ua.pt" "l040101-ws09.ua.pt" "l040101-ws10.ua.pt" )
src=Src

printf "\nBuilding Servers...\n"

mkdir $src

for i in $(seq 0 $((${#servers[@]}-1)))
do
    printf "  "${servers[$i]}"...\n"

    # Get source files
    cp -r ${servers[$i]}/src ./$src/${servers[$i]}

    # go to copied directory
    cd $src/${servers[$i]}

    # compile
    javac Main/${servers[$i]}.java

    # remove code
    rm -rf  */*.java

    cd ..

    # zip .class
    zip -qr ${servers[$i]}.zip  ${servers[$i]}/*

    rm -rf ${servers[$i]}/

    # send to corresponding remote machine
    sshpass -p $PASSWORD sftp -q sd0202@${serversMachines[$i]}: <<< $'put '${servers[$i]}'.zip'

    # unzip in remote machine
    sshpass -p $PASSWORD ssh sd0202@${serversMachines[$i]} "rm -rf ${servers[$i]}; unzip -q ${servers[$i]}.zip; rm ${servers[$i]}.zip"

    cd ..
done

clients=( "BrokerClient" "HorseJockeyClient" "SpectatorClient" )
clientsMachines=( "l040101-ws02.ua.pt" "l040101-ws05.ua.pt" "l040101-ws08.ua.pt" )

printf "\nBuilding Clients...\n"

for i in $(seq 0 $((${#clients[@]}-1)))
do
    printf "  "${clients[$i]}"...\n"

    # Get source files
    cp -r ${clients[$i]}/src ./$src/${clients[$i]}

    # go to copied directory
    cd $src/${clients[$i]}

    # compile
    javac Main/${clients[$i]}.java

    # remove code
    rm -rf  */*.java

    cd ..

    # zip .class
    zip -qr ${clients[$i]}.zip  ${clients[$i]}/*

    rm -rf ${clients[$i]}/

    # send to corresponding remote machine
    sshpass -p $PASSWORD sftp -q sd0202@${clientsMachines[$i]}: <<< $'put '${clients[$i]}'.zip'

    # unzip in remote machine
    sshpass -p $PASSWORD ssh sd0202@${clientsMachines[$i]} "rm -rf ${clients[$i]}; unzip -q ${clients[$i]}; rm ${clients[$i]}.zip"

    cd ..
done

rm -rf $src

echo "All done"
