#!/bin/bash

cd `dirname $0`

mvn install:install-file -Dfile=lib/galaxybootstrap-0.7.0-SNAPSHOT.jar -DpomFile=lib/galaxybootstrap-0.7.0-SNAPSHOT.pom.xml -DcreateChecksum=true
