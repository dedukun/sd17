#!/bin/bash

# Compile source and create zip to send to the machines
./build.sh

# Send the files to the specific machines
./deploy.sh
