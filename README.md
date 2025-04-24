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
- `hackDevice()`: Simulates hacking by executing a command to access device files.
- `defendAgainstHack()`: Simulates defense by checking for unauthorized access and stopping the ADB server.
- `testBroadcastReceiverLifecycle()`: Tests the lifecycle methods of an Android broadcast receiver.
- `testBroadcastReceiverInteraction()`: Tests the interaction between broadcast receivers and other components.
- `testBroadcastReceiverHandling()`: Tests the handling of different types of broadcast messages.
- `testActivityLifecycle()`: Tests the lifecycle methods of an Android activity.
- `testActivityInteraction()`: Tests the interaction between activities.
- `testActivityUIElements()`: Tests the UI elements of an activity.
- `testServiceLifecycle()`: Tests the lifecycle methods of an Android service.
- `testServiceInteraction()`: Tests the interaction between services and other components.
- `testServiceBackgroundProcessing()`: Tests the background processing capabilities of a service.
- `enableWirelessControl()`: Enables full wireless control of the device.
- `extractData(String sourcePath, String destinationPath)`: Extracts data from the device using ADB commands.
- `extractContacts(String destinationPath)`: Extracts contacts from the device using ADB commands.
- `extractSMS(String destinationPath)`: Extracts SMS messages from the device using ADB commands.
- `extractCallLogs(String destinationPath)`: Extracts call logs from the device using ADB commands.
- `extractFiles(String sourcePath, String destinationPath)`: Extracts files from the device using ADB commands.
- `traceIPAddress()`: Traces the IP address of the device using ADB commands.
- `enableStealthMode()`: Enables stealth mode by disabling notifications and hiding the ADB connection from the user.
