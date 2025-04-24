# Setup Guide

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) version 17 or later.
- You have installed [Gradle](https://gradle.org/install/) version 7.0 or later.
- You have installed [Android Debug Bridge (ADB)](https://developer.android.com/studio/command-line/adb) version 1.0.41 or later.
- You have a GitHub account and have [Git](https://git-scm.com/downloads) installed on your local machine.

## Installation

To install the project, follow these steps:

1. Clone the repository:
   ```sh
   git clone https://github.com/Tokenblkguy83/Learning.git
   cd Learning
   ```

2. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```

3. Run the tests to ensure everything is set up correctly:
   ```sh
   ./gradlew test
   ```

## Configuration

To configure the project, follow these steps:

1. Open the `build.gradle` file and update the dependencies as needed.
2. Configure the `renovate.json` file for automated dependency updates. Here is an example configuration:
   ```json
   {
     "$schema": "https://docs.renovatebot.com/renovate-schema.json",
     "extends": [
       "config:recommended",
       ":dependencyDashboard"
     ],
     "schedule": ["every weekend"],
     "packageRules": [
       {
         "matchUpdateTypes": ["minor", "patch"],
         "matchCurrentVersion": "!/^0/",
         "automerge": true
       },
       {
         "matchDepTypes": ["devDependencies"],
         "automerge": true
       }
     ],
     "prHourlyLimit": 4,
     "prConcurrentLimit": 10,
     "rangeStrategy": "pin",
     "semanticCommits": "enabled",
     "semanticCommitType": "chore",
     "semanticCommitScope": "deps"
   }
   ```
3. Set up GitHub Actions for CI/CD by modifying the `.github/workflows/jdk.yml` and `.github/workflows/stale.yml` files as needed.

## Usage

To use the project, follow these steps:

1. Start the ADB server:
   ```sh
   adb start-server
   ```

2. Run the main application:
   ```sh
   ./gradlew run
   ```

3. Execute ADB commands using the `ADBBase` class methods. For example, to start the ADB server:
   ```java
   ADBBase adbBase = new ADBBase();
   adbBase.startADBServer();
   ```

## Additional Resources

For more information, refer to the following resources:

- [API Reference](Docs/Api-Reference.md)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)
- [Java Documentation](https://docs.oracle.com/en/java/)
- [ADB Documentation](https://developer.android.com/studio/command-line/adb)
