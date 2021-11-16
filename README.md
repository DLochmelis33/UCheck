<img src="logo.png" align="right" />

# UCheck
<!-- Badges -->
[![releases](https://img.shields.io/github/v/release/DLochmelis33/UCheck.svg)](https://github.com/DLochmelis33/UCheck/blob/master/CHANGELOG.md)
[![License Badge][license-badge]][license]
[![Java CI with Gradle](https://img.shields.io/github/workflow/status/DLochmelis33/UCheck/Java%20CI%20with%20Gradle)](https://github.com/DLochmelis33/UCheck/actions/workflows/on_push.yml)
[![contributions](https://img.shields.io/github/contributors/DLochmelis33/UCheck)](https://github.com/DLochmelis33/UCheck/graphs/contributors)
![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)
## Introduction
UCheck is created to help people buy a variety of goods at the lowest prices. 

### What can you do with UCheck?
* Scan your check after shopping
* Agregate all purchase information (prices, items, shop, etc) in UCheck database
* Use the collected information to find necessary goods at the lowest prices

For more information visit our [project wiki](https://github.com/DLochmelis33/UCheck/wiki)!

## Installation

1. Clone the repo: 
```bash
git clone git@github.com:DLochmelis33/UCheck.git
```

2. Execute Gradle

### Installing with Docker

1. Clone the repo (just like normal installation)

2. Build an image from `dockerfiles/download.Dockerfile`

3. Build a second image using the following command:
```bash
docker build --rm -f "dockerfiles\build.Dockerfile" -t ucheck_built:latest "dockerfiles"
```
Now the `ucheck_built:latest` contains the built project. 
The TAR and ZIP archives containing an executable JAR are located in `build/distributions` folder in the image.

## Authors
- Gleb Solovev ([@GlebSolovev](https://github.com/GlebSolovev))
- Denis Lochmelis ([@DLochmelis33](https://github.com/DLochmelis33))
- Ekaterina Itsenko ([@Soykaa](https://github.com/Soykaa))

## Acknowledgements
We are [very very a lot super mega] grateful to our wonderful teachers for their support and assistance during the whole project:
- Vladislav Tankov
- Timofey Bryksin

## Contribute
Contributions are welcome! Before creating a pull request, make sure that it adheres to the following guidelines:

- Avoid duplicates: search previous suggestions before making a new one
- Make an individual pull request for each suggestion
- Keep descriptions short and simple, but descriptive

Thank you!
## Links
* [Project Roadmap](https://github.com/DLochmelis33/UCheck/projects/1)
* [Open issues](https://github.com/DLochmelis33/UCheck/issues)

[license]: ./LICENSE.txt
[license-badge]: https://badgen.net/badge/license/GNU%20Affero%20General%20Public%20License%20v3.0
