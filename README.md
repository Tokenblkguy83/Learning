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

This repository includes basic adb functionality provided by the `MaliciousADBBase` class. The `MaliciousADBBase` class provides methods to execute adb commands, check for connected devices, and detect potentially malicious apps.

### Methods

- `executeAdbCommand(String command)`: Executes the given adb command and returns the output as a string.
- `isAdbAvailable()`: Checks if ADB is available on the system.
- `getConnectedDevices()`: Returns a list of connected device IDs.
- `checkForMaliciousApps(String deviceId)`: Checks for potentially malicious apps on a device and returns a list of suspicious package names.

## Documentation

For more information, refer to the following resources:

- [API Reference](Docs/Api-Reference.md)
- [Setup Guide](Docs/Setup-guide.md)

## Known Issues and Required Fixes

- The `Tests/ADB_test/ADBBase_test.java` file does not include tests for all methods, such as `extractData`.
