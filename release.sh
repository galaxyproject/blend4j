#!/bin/bash

read -s -p "GPG Password: " gpgpass
mvn_args="-Dgpg.passphrase=$gpgpass -DskipTests=true -DperformRelease=true"

mvn $mvn_args release:clean

mvn $mvn_args release:prepare

mvn $mvn_args release:perform

echo "Artifacts staged, must be released by visiting: https://oss.sonatype.org/, closing staging repository, and then releasing."
