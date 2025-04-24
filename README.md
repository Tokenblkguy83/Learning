# Learning
Basic project learning

## Dependency Management

This repository uses [Renovate](https://docs.renovatebot.com/) for automated dependency updates. The Dependency Dashboard issue tracks all pending updates and can be found in the Issues tab.

### Features of our Renovate setup:

- Automated dependency updates run every weekend
- Minor and patch updates are automatically merged
- Development dependencies are automatically merged
- Semantic commit messages for dependency updates
- Dependency Dashboard for monitoring updates

For more information, see the [Renovate documentation](https://docs.renovatebot.com/key-concepts/dashboard/).

## ADB Functionality

This repository includes basic adb functionality provided by the `ADBBase` class. The `ADBBase` class provides methods to start and stop the adb server, as well as execute adb commands.

### Methods

- `startADBServer()`: Starts the adb server.
- `stopADBServer()`: Stops the adb server.
- `executeCommand(String command)`: Executes the given adb command and returns the output as a string.
- `runADBServerWithoutPC()`: Runs the adb server without requiring a PC.
