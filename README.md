# About

blend4j is a partial reimplemenation of the Python library [blend][1]
for the JVM. blend for Python is a library for scripting interactions
with Galaxy, CloudMan, and BioCloudCentral. 

blend4j will focus initially on reimplementation the particulars parts
of blend I am in immediate need of (a subset of the Galaxy API
functionality), but hopefully over time it will grow into feature
parity with blend.

[1]: https://github.com/afgane/blend

# Usage

For simplicity, these examples assume the following import statements are used to import all of blend4j's Galaxy functionality.

    import com.github.jmchilton.blend4j.galaxy.beans.*;
    import com.github.jmchilton.blend4j.galaxy.*;

Listing a users history:

    GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(url, apiKey);
    HistoriesClient historiesClient = galaxyInstance.getHistoriesClient();
    for(History history : historiesClient.getHistories()) {
      String name = history.getName();
      String id = history.getId();
      String message = String.format("Found history with name %s and id %s", name, id);
      System.out.println(message);
    }

For more examples see these [integration test cases][u1].

[u1]: https://github.com/jmchilton/blend4j/blob/master/src/test/java/com/github/jmchilton/blend4j/galaxy/IntegrationTest.java

# Building

blend4j can be built with [Apache Maven][b1].

        % git clone git://github.com/jmchilton/blend4j.git
        % cd blend4j
        % mvn compile

[b1]: http://maven.apache.org/

# License

The code is freely available under the [Apache License Version 2.0][l1].

[l1]: http://www.apache.org/licenses/LICENSE-2.0.html
