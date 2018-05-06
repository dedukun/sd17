#!/bin/bash

clear

function build {
    folder=$1

    # Get the main class and build directory from the project properties
    properties="./$folder/nbproject/project.properties"
    mainClassProperty=$(cat $properties | grep "main.class=")
    mainClass="${mainClassProperty/main.class=/}"

    # Compile source
    if [ ! -f "./$folder/src/$(echo $mainClass | tr . /).java" ]; then
      echo "The main class ${mainClass} wasn't found"
      exit 1
    fi

    # Run clean
    if [ "$1" == "clean" ]; then
      eval "ant -q -f $(pwd)/$folder -Dnb.internal.action.name=rebuild clean jar"
    fi
}


servers=( "BettingCenterServer" "ControlCenterServer" "GenReposServer" "PaddockServer"
"RaceTrackServer" "StableServer" )

printf "Building Servers...\n"

for server in "${servers[@]}"
do

    printf "  "$server"..."

    build $server

    echo "done"

    # run program
    #eval "ant -q -f $(pwd)/$server -Dnb.internal.action.name=run run"
done

clients=( "BrokerClient" "HorseJockeyClient" "SpectatorClient" )

printf "\nBuilding Clients...\n"

for client in "${clients[@]}"
do

    printf "  "$client"..."

    build $client

    echo "done"
done
