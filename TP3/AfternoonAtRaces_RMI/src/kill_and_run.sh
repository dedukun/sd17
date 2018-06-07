#!/bin/bash

printf "Enter machines password: "
read -s PASSWORD

# Kill all running machines
./kill.sh $PASSWORD

# Run
./run.sh $PASSWORD
