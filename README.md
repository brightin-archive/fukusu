![](logo.png)

# Fukusu

_"Fukusū" - Japanese for "Multiple"_

Run commands against multiple Heroku applications.

## Overview

We're a small company with multiple Heroku applications for multiple clients and we'd like to analyze or update multiple applications with a single command. Thus Fukusu was born!

This enables us to check the version of dependencies for all apps; grep the source code; manage collaborators and more.

## Installing

We have plans to release Fukusu via a package manager but we haven't yet. To use it you need clone this repository and run `lein bin`. This will create the executable in the bin directory. Use this file directly from the project root via `.bin/fukusu` or copy it to you $PATH.

## Requirements

- Java
- Heroku Toolbelt

## License

MIT
