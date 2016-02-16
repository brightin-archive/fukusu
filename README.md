![](logo.png)

# Fukusu

_"Fukusū" - Japanese for "Multiple"_

Run commands against multiple Heroku applications.

We're a small company with multiple Heroku applications for multiple clients and we'd like to analyze or update multiple applications with a single command. Thus Fukusu was born!

This enables us to check the version of dependencies for all apps; grep the source code; manage collaborators and more.

## Installation

    brew install brightin/tools/fukusu

It requires the following dependencies:

- Java 1.7+
- Heroku Toolbelt

## Usage

After you've installed Fukusu via Homebrew, you're able to run the `fukusu` command. For usage instructions run:

    fukusu --help

## Building with Leiningen

If you want to contribute or don't want to use [Homebrew], you can build the executable with via [Leiningen]. Clone this repository and run `lein bin`. This will create the executable in the bin directory. Use this file directly from the project root via `./bin/fukusu` or copy it to you `PATH`.

## License

MIT

[Homebrew]: http://leiningen.org
[Leiningen]: http://brew.sh
