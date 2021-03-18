# Emoji Snake Game

This Gluon Client sample was generated from https://start.gluon.io

Based on https://github.com/FDelporte/JavaFXGameSnake

## Basic Requirements

A list of the basic requirements can be found online in the [Gluon Client documentation](https://docs.gluonhq.com/client/#_requirements).

## Quick instructions

### GraalVM on Mac

* Use sdkman - https://sdkman.io/
* Install GraalVM with `sdk install java 21.0.0.r11-grl`
* Set environment variable with `export GRAALVM_HOME=${SDKMAN_CANDIDATES_DIR}/java/21.0.0.r11-grl`
* Check variable with `echo $GRAALVM_HOME`

### Run the sample

    mvn javafx:run

### Run the sample as a native image

    mvn client:build client:run

### Run the sample as a native android image

    mvn -Pandroid client:build client:package client:install client:run

### Run the sample as a native iOS image

    mvn -Pios client:build client:package client:install client:run

## Selected features

This is a list of all the features that were selected when creating the sample:

### JavaFX 15.0.1 Modules

 - javafx-base
 - javafx-graphics
 - javafx-controls

### Gluon Features

 - Glisten: build platform independent user interfaces
 - Attach device
 - Attach display
 - Attach lifecycle
 - Attach magnetometer
 - Attach orientation
 - Attach statusbar
 - Attach storage
 - Attach vibration
