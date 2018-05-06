#!/bin/bash

# Get the main class and build directory from the project properties
properties="./nbproject/project.properties"
mainClassProperty=$(cat $properties | grep "main.class=")
mainClass="${mainClassProperty/main.class=/}"

# Compile source
if [ ! -f "./src/$(echo $mainClass | tr . /).java" ]; then
  echo "The main class ${mainClass} wasn't found"
  exit 1
fi

# Run clean
if [ "$1" == "clean" ]; then
  eval "ant -q -f $(pwd) -Dnb.internal.action.name=rebuild clean jar"
fi

# run program
eval "ant -q -f $(pwd) -Dnb.internal.action.name=run run"
