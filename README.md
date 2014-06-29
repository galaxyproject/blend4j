[![Build Status](https://travis-ci.org/jmchilton/blend4j.png?branch=master)](https://travis-ci.org/jmchilton/blend4j)

# About

blend4j is a partial reimplemenation of the Python library [bioblend][1]
for the JVM. bioblend for Python is a library for scripting interactions
with Galaxy, CloudMan, and BioCloudCentral.

[1]: https://github.com/afgane/bioblend

# Usage

blend4j's [JavaDocs][api] contain some quick usage examples. Many more
examples can be found in  [Aaron Petkau][apetkau]'s [blend4j tutorials][tutorial]
and the [integration tests][tests]. Examples of using
blend4j in larger open source projects include [Molgenis integration][molgensis] by [Dennis Hendriksen][dennishendriksen] and the
[TraIT Workflow Runner][trait] for use with
[transMART][https://github.com/transmart] by [Freek de Bruijn][freekdb].

[api]: http://jmchilton.github.io/blend4j/apidocs/
[tutorial]: https://github.com/apetkau/blend4j-tutorials
[tests]: https://github.com/jmchilton/blend4j/tree/master/src/test/java/com/github/jmchilton/blend4j/galaxy
[molgensis]: https://github.com/molgenis/molgenis/commit/57d229a8d36fa9dae1155685e85187399863057f
[transmart]: https://github.com/transmart
[trait]: https://github.com/jmchilton/blend4j
[apetkau]: https://github.com/apetkau
[freekdb]: https://github.com/freekdb
[dennishendriksen]: https://github.com/dennishendriksen

# Building

blend4j can be built with [Apache Maven][b1].

        % git clone git://github.com/jmchilton/blend4j.git
        % cd blend4j
        % mvn compile

# Testing

[![Build Status](https://travis-ci.org/jmchilton/blend4j.png?branch=master)](https://travis-ci.org/jmchilton/blend4j)

blend4j can be tested with [Apache Maven][b1].

        % mvn test 

In this default mode, blend4j will use the [galaxy-bootstrap][t1] library to download, configure,
and run an instance of Galaxy for testing (requiring an Internet connection). blend4j can be tested against an existing instance using the following Java system properties.

        % mvn -Dtest.api.key=<key> -Dtest.galaxy.instance=<url> test

The above example will run a variety of tests and make various assumptions - some which may not be true for the supplied API key. Maven can be configured to run [a specific test or tests][t2].

[b1]: http://maven.apache.org/
[t1]: https://github.com/jmchilton/galaxy-bootstrap
[t2]: http://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html

# Jars

The easiest way to obtain blend4j is using Maven Central. 

For instance, if a project is using Maven, a blend4j dependency can be added by 
adding the following to the dependencies section of the project's pom.xml file.

```xml
     <dependency>
       <groupId>com.github.jmchilton.blend4j</groupId>
       <artifactId>blend4j</artifactId>
       <version>0.1.0</version>
     </dependency>
```

Similar dependencies can be specified if using Ivy, Grape, Gradle, Buildr, or SBT. 
See [mvnrepository][d1] for more details.


The latest blend4j jars can be downloaded from the [Maven Central Repository][d0].

[d0]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.jmchilton.blend4j%22
[d1]: http://mvnrepository.com/artifact/com.github.jmchilton.blend4j/blend4j/

# License

The code is freely available under the [Apache License Version 2.0][l1].

[l1]: http://www.apache.org/licenses/LICENSE-2.0.html

