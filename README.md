[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg?style=plastic)](https://raw.githubusercontent.com/signed/swagger-toolbox/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.signed.swagger/swagger-toolbox.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.signed.swagger/swagger-toolbox)
[![GitHub issues](https://img.shields.io/github/issues/signed/swagger-toolbox.svg?style=plastic)](https://github.com/signed/swagger-toolbox/issues)   
[![Travis](https://img.shields.io/travis/signed/swagger-toolbox/master.svg?style=plastic)](https://travis-ci.org/signed/swagger-toolbox)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/bd65bb851ff24fe5b3b39a56a6d2ad2f)](https://www.codacy.com/app/thomas-heilbronner/swagger-toolbox)
[![Coveralls](https://img.shields.io/coveralls/signed/swagger-toolbox.svg?style=plastic)](https://coveralls.io/github/signed/swagger-toolbox?branch=master)
[![Coverage Status](https://img.shields.io/codecov/c/github/signed/swagger-toolbox.svg?style=plastic)](https://codecov.io/github/signed/swagger-toolbox)

master: [![Dependency Status master](https://www.versioneye.com/user/projects/56d74b62d71695003886c338/badge.svg?style=plastic)](https://www.versioneye.com/user/projects/56d74b62d71695003886c338)  
last release: [![VersionEye Last Release](https://img.shields.io/versioneye/d/java/com.github.signed.swagger:swagger-toolbox.svg?style=plastic)](https://www.versioneye.com/java/com.github.signed.swagger:swagger-toolbox)


## What is this?
A small library providing tools on top of [swagger core](https://github.com/swagger-api/swagger-core) to modify and clean up [swagger specifications](https://github.com/swagger-api).

## What tools are in the box?
 
- Trim
  - [not referenced definitions](src/test/resources/features/trim_model_definitions.feature)
  - [not referenced parameter definitions](src/test/resources/features/trim_parameter_definitions.feature)
  - [not referenced response definitions](src/test/resources/features/trim_response_definitions.feature)
  - [not referenced tag definitions](src/test/resources/features/trim_tag_definitions.feature)

- [Reduce](src/test/resources/features/reduce.feature)

- [Merge](src/test/resources/features/merge.feature)

