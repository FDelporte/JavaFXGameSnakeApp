# Emoji Snake Game App

![Linux JAR and Native](https://github.com/FDelporte/JavaFXGameSnakeApp/workflows/Linux%20JAR%20and%20Native/badge.svg)
![MacOS](https://github.com/FDelporte/JavaFXGameSnakeApp/workflows/MacOS/badge.svg)
![Windows](https://github.com/FDelporte/JavaFXGameSnakeApp/workflows/Windows/badge.svg)
![Linux Android](https://github.com/FDelporte/JavaFXGameSnakeApp/workflows/Linux%20Android/badge.svg)

This Gluon Client sample was generated from https://start.gluon.io

Based on https://github.com/FDelporte/JavaFXGameSnake

## Basic Requirements

A list of the basic requirements can be found online in
the [Gluon Client documentation](https://docs.gluonhq.com/client/#_requirements).

## Quick instructions

### GraalVM on Mac

* Use sdkman - https://sdkman.io/
* Install GraalVM with `sdk install java 21.3.0.r17-grl`
* Set environment variable with `export GRAALVM_HOME=${SDKMAN_CANDIDATES_DIR}/java/21.3.0.r17-grl`
* Check variable with `echo $GRAALVM_HOME`

### Run the sample

    mvn javafx:run

### Run the sample as a native image

    mvn gluonfx:build gluonfx:run

### Run the sample as a native android image

    mvn -Pandroid gluonfx:build gluonfx:package gluonfx:install gluonfx:run

### Run the sample as a native iOS image

    mvn -Pios gluonfx:build gluonfx:package gluonfx:install gluonfx:run

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

## Privacy Policy

If a privacy policy is needed (e.g. for Android app), use the one created by XScreenSaver: https://www.jwz.org/xscreensaver/google.html
