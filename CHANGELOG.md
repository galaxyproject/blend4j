# blend4j changelog

## [0.2.1][021release] (Jan 22, 2020)

- Added `invokeWorkflow` to `WorkflowsClient` to be used instead of `runWorkflow`.
- Added updated `importWorkfow(String json, boolean publish)` to `WorkflowsClient` that should be used over the deprecated `importWorkflow(String json)`.

## [0.2.0][020release] (March 26, 2018)

- Update to [galaxy-bootstrap 0.7.0][gxbootstrap070].
- Added support for accessing Galaxy Tool Data tables.  Thanks to Dan Fornika - <https://github.com/apetkau/blend4j/pull/9>.
- blend4j now requires Java 1.8+ to run.

[gxbootstrap070]: http://mvnrepository.com/artifact/com.github.jmchilton.galaxybootstrap/galaxybootstrap/0.7.0
[020release]: http://mvnrepository.com/artifact/com.github.jmchilton.blend4j/blend4j/0.2.0

## [0.1.2][012release] (December 18, 2014)

- Add ability to get information about a [library dataset][getlibrarydataset].
- Update to [galaxy-bootstrap 0.5.0][gxbootstrap050] to account for recent Galaxy configuration changes.
- Various bug fixes for recent API changes in Galaxy. [1][012bugfix1], [2][012bugfix2], [3][012bugfix3]

[012release]: http://mvnrepository.com/artifact/com.github.jmchilton.blend4j/blend4j/0.1.2
[getlibrarydataset]: https://github.com/jmchilton/blend4j/commit/e0bd2be2fc93530aa89744701dc96f0591d3894c
[gxbootstrap050]: https://github.com/jmchilton/blend4j/commit/a9a87c3c0b93a2add1572932f39583106cb8c60c
[012bugfix1]: https://github.com/jmchilton/blend4j/commit/f9a5a4bd32424f039ee90c9c1735bc5f90a38973
[012bugfix2]: https://github.com/jmchilton/blend4j/commit/47de71cee239fa4feb975120828429fabac9b500
[012bugfix3]: https://github.com/jmchilton/blend4j/commit/48ff545c1303a5b84b862afae98e6f7f49145d99


## [0.1.1][011release] (August 27, 2014)

 - Dataset collection support by @apetkau. Among other things the [histories client][historiesclient]
 can now [create][historiescollectioncreate] and [return][historiescollectionshow] information about collections and the [workflows client][workflowsclient] can [specify][workflowscollectioninput] dataset collections as inputs.
 - Documentation overhaul - [API documentation][apidocs] now available online.
 - Update tool shed client defaults to reflect the fact main tool shed is now being served over HTTPS.
 - Allow authentication with a [username and password][011changesetbasicauth]
 instead of an API key. In this case an API key will be generated automatically before the first API call and used for subsequent communication.
 - Introduce unified [exception handling][011changesetexceptionhandling].
 Hopefully this will allow clients to decouple exception handling from underlying framework.
 - [Improved logging options][011changesetfreekfixes] and [various][011changesetdocfixes1]
 [documentation][011changesetdocfixes2] and spelling corrections by @FreekDB.
 - Various testing improvements by @apetkau.

[011release]: http://mvnrepository.com/artifact/com.github.jmchilton.blend4j/blend4j/0.1.1
[011changesetdocfixes1]: https://github.com/jmchilton/blend4j/commit/58abbe03c2223e504ee90e83ffd5626b1afa6b85
[011changesetdocfixes2]: https://github.com/jmchilton/blend4j/commit/3ae570820b4fdb4bad0914349913c909c048b4a7
[011changesetfreekfixes]: https://github.com/jmchilton/blend4j/commit/22de9dd4cec55ca9a68abab2c48c8fa2cddd3a6c
[011changesetexceptionhandling]: https://github.com/jmchilton/blend4j/commit/8e56c8b5adeebf4ce22e672bc16375dab513496b
[011changesetbasicauth]: https://github.com/jmchilton/blend4j/commit/f92909fbda3616da09614b65810ebd86ce496b19
[historiescollectionshow]: http://jmchilton.github.io/blend4j/apidocs/com/github/jmchilton/blend4j/galaxy/HistoriesClient.html#showDatasetCollection(java.lang.String%2C%20java.lang.String)
[historiescollectioncreate]: http://jmchilton.github.io/blend4j/apidocs/com/github/jmchilton/blend4j/galaxy/HistoriesClient.html#createDatasetCollection(java.lang.String%2C%20com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionDescription)
[workflowscollectioninput]: http://jmchilton.github.io/blend4j/apidocs/com/github/jmchilton/blend4j/galaxy/beans/WorkflowInputs.InputSourceType.html

## [0.1.0][010release] (May 08, 2014)

 - Parse more information from dataset provenance API calls (job id, standard error, and standard output). [changeset][010changesetprov]
 - Parse [more][010changesetcontents1] [information][010changesetcontents2] from dataset contents API calls (purged, hid, history content type, state, dataset info, blurb).
 - Bugfixes for importing workflows from an installed tool shed repository.
 - Various testing and logging improvements.

[010release]: http://mvnrepository.com/artifact/com.github.jmchilton.blend4j/blend4j/0.1.0
[010changesetprov]: https://github.com/jmchilton/blend4j/commit/d253bfc51ad8b7a9e19d1b9956d1f5ad97cfbc53
[010changesetcontents1]: https://github.com/jmchilton/blend4j/commit/82717441b4d9015c38170b1231298c95630c62ee
[010changesetcontents2]: https://github.com/jmchilton/blend4j/commit/76d004d59c54799fa4d10473e360158169e65b08

## [0.1-alpha-8][010alpha8release] (Apr 10, 2014)

 - Allow setting more [workflow parameters][010alpha8changesetworkflowparams] by steps instead of tool and allow setting multiple parameters per tool/step.
 - Parse more [details][010alpha8changesetworkflowdetails] during workflow detail API calls (e.g. owner, deleted, published, and step information).
 - Various small test fixes.

[010alpha8release]: http://mvnrepository.com/artifact/com.github.jmchilton.blend4j/blend4j/0.1-alpha-8
[010alpha8changesetworkflowparams]: https://github.com/jmchilton/blend4j/commit/4f9f8b710bcc715159b5d71a2691e33df77e0c25
[010alpha8changesetworkflowdetails]: https://github.com/jmchilton/blend4j/commit/26d65c704292c2eb70b9500d45373277fdf5e340

[apidocs]: http://jmchilton.github.io/blend4j/apidocs/
[workflowsclient]: http://jmchilton.github.io/blend4j/apidocs/com/github/jmchilton/blend4j/galaxy/WorkflowsClient.html
[historiesclient]: http://jmchilton.github.io/blend4j/apidocs/com/github/jmchilton/blend4j/galaxy/HistoriesClient.html
