# [venus](https://kvakil.github.io/venus/)
[![Build Status](https://travis-ci.com/kvakil/venus.svg?token=ke1yhth1Tq9yPQc4KzUY&branch=master)](https://travis-ci.com/kvakil/venus)

__venus__ is a RISC-V instruction set simulator built for education.

## Using venus

venus is [available online](https://kvakil.github.io/venus/).

## Building venus

If you want to make changes to venus's source code, see below.

### Backend

The backend for venus is written in Kotlin and compiled into Javascript. To build, execute the following in your terminal:

    ./gradlew build

To run the included tests, open `qunit/test.html`.

### Frontend

Building the frontend requires `node` and `npm` to be installed. Execute the following:

    npm install

Once that's done, you should be able to run the tests from the command line:

    grunt test

To create a standalone distribution, run `grunt dist`. All of the needed files are in the `out/` directory.

## Contributing

Coming soon.
