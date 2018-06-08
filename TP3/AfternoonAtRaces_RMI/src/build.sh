#!/bin/bash

echo "Building Project..."

printf "  Compiling source... "
# Compile source
javac ./auxiliary/*.java ./clientSide/*/*.java ./interfaces/*.java ./registry/*.java ./serverSide/*/*.java ./extras/*.java

echo "done"

echo "  Build..."

printf "    Registry... "
cd deploy/dir_registry

mkdir interfaces registry
cp ../../interfaces/Register.class ./interfaces/
cp ../../registry/*.class ./registry/

cd ..

# zip .class
zip -qr registry.zip dir_registry/

cd ..
echo "done"

printf "    Public... "
cd deploy/classes

mkdir auxiliary interfaces extras
cp ../../auxiliary/*.class ./auxiliary/
cp ../../interfaces/*.class ./interfaces/
cp ../../extras/*.class ./extras/

cd ..

# zip .class
zip -qr classes.zip classes/

cd ..
echo "done"


servers=( "BettingCenter" "ControlCenter" "GenRepos" "Paddock" "RaceTrack" "Stable" )
clients=( "Broker" "HorseJockey" "Spectators" )

cd deploy
for i in $(seq 0 $((${#servers[@]}-1)))
do
    printf "    "${servers[$i]}"... "

    # Change to server directory
    cd dir_${servers[$i]}

    # Create new folders
    mkdir interfaces auxiliary extras serverSide serverSide/${servers[$i]}

    # Copy to
    cp ../../interfaces/*.class ./interfaces/
    cp ../../auxiliary/*.class   ./auxiliary/
    cp ../../extras/*.class   ./extras/
    cp ../../serverSide/${servers[$i]}/*.class ./serverSide/${servers[$i]}/

    cd ..

    # zip .class
    zip -qr ${servers[$i]}.zip dir_${servers[$i]}/

    echo "done"
done

for i in $(seq 0 $((${#clients[@]}-1)))
do
    printf "    "${clients[$i]}"... "

    # Change to client directory
    cd dir_${clients[$i]}

    # Create new folders
    mkdir extras interfaces auxiliary clientSide clientSide/${clients[$i]}

    # Copy to
    cp ../../interfaces/*.class ./interfaces/
    cp ../../auxiliary/*.class   ./auxiliary/
    cp ../../extras/*.class   ./extras/
    cp ../../clientSide/${clients[$i]}/*.class ./clientSide/${clients[$i]}/

    cd ..

    # zip .class
    zip -qr ${clients[$i]}.zip dir_${clients[$i]}/

    echo "done"
done
cd ..

printf "  Cleaning build... "

# Remove .class
find . -type f -name '*.class' -delete

# Remove deployment directories
rm -r deploy/*/*/

echo "done"
