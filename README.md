# EleceDb

![elecedb-logo.jpg](./docs/assets/elecedb-logo.jpg)

## Overview

EleceDb is a simple sql database, done with the purpose of gaining knowledge on how databases work internally, mostly
on how to create a parser, how indexes and rows are stored, how b+ trees work, how queries are processed, etc...
This shouldn't be seen as a standard to build databases, but as a simple enough database implementation, so that others
could also learn something from here or gain some inspiration.

**This repository is a simple driver for [EleceDb](https://github.com/Bernardo-FMF/eleceDb), more documentation on the
database internals in the respective repository.**

## How to run the EleceDb driver

To compile the project locally, you need to install:

- [Java 23](https://www.oracle.com/java/technologies/downloads/)
- [Git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/download.cgi)

Then you also need to clone this repository:

```git clone https://github.com/BernardoFMF/eleceDb-driver.git```

Afterward on the directory of the project, run:

```mvn clean install```

This will compile the project, and the jar will be located here:

> /{projectDirectory}/target/eleceDb-driver-{version}-jar-with-dependencies.jar

To run the jar, go to the previously mentioned directory and execute the following command:

```java -jar eleceDb-driver-{version}-jar-with-dependencies.jar```

This will launch an instance of the EleceDb driver, that will try to connect to an EleceDb instance running on the
configured port.
Both the port and host of the EleceDb instance can be configured, as instructed below.

## Environment variables

Below is a list of the environment variables that

| Env variable name | Description                      | Default value | Possible values |
|-------------------|----------------------------------|---------------|-----------------|
| elece.db.port     | The port of the EleceDb instance | 3000          | -               |
| elece.db.host     | The port of the EleceDb instance | 127.0.0.1     | -               |
