# API Reference

## ADBBase Class

The `ADBBase` class provides methods for ADB (Android Debug Bridge) functionality. Below is a detailed description of each method and its usage.

### Methods

#### `startADBServer()`
Starts the ADB server.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.startADBServer();
```

#### `stopADBServer()`
Stops the ADB server.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.stopADBServer();
```

#### `executeCommand(String command)`
Executes the given ADB command and returns the output as a string.

**Parameters:**
- `command`: The ADB command to execute.

**Returns:**
- The output of the executed command as a string.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
String output = adbBase.executeCommand("adb devices");
```

#### `runADBServerWithoutPC()`
Runs the ADB server without requiring a PC.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.runADBServerWithoutPC();
```

#### `hackDevice()`
Simulates hacking by executing a command to access device files.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.hackDevice();
```

#### `defendAgainstHack()`
Simulates defense by checking for unauthorized access and stopping the ADB server.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.defendAgainstHack();
```

#### `testBroadcastReceiverLifecycle()`
Tests the lifecycle methods of an Android broadcast receiver.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testBroadcastReceiverLifecycle();
```

#### `testBroadcastReceiverInteraction()`
Tests the interaction between broadcast receivers and other components.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testBroadcastReceiverInteraction();
```

#### `testBroadcastReceiverHandling()`
Tests the handling of different types of broadcast messages.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testBroadcastReceiverHandling();
```

#### `testActivityLifecycle()`
Tests the lifecycle methods of an Android activity.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testActivityLifecycle();
```

#### `testActivityInteraction()`
Tests the interaction between activities.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testActivityInteraction();
```

#### `testActivityUIElements()`
Tests the UI elements of an activity.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testActivityUIElements();
```

#### `testServiceLifecycle()`
Tests the lifecycle methods of an Android service.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testServiceLifecycle();
```

#### `testServiceInteraction()`
Tests the interaction between services and other components.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testServiceInteraction();
```

#### `testServiceBackgroundProcessing()`
Tests the background processing capabilities of a service.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.testServiceBackgroundProcessing();
```

#### `enableWirelessControl()`
Enables full wireless control of the device.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.enableWirelessControl();
```

#### `extractData(String sourcePath, String destinationPath)`
Extracts data from the device using ADB commands.

**Parameters:**
- `sourcePath`: The source path on the device.
- `destinationPath`: The destination path on the local machine.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.extractData("/data/data/com.example/files", "/local/path");
```

#### `extractContacts(String destinationPath)`
Extracts contacts from the device using ADB commands.

**Parameters:**
- `destinationPath`: The destination path on the local machine.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.extractContacts("/local/path/contacts.db");
```

#### `extractSMS(String destinationPath)`
Extracts SMS messages from the device using ADB commands.

**Parameters:**
- `destinationPath`: The destination path on the local machine.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.extractSMS("/local/path/mmssms.db");
```

#### `extractCallLogs(String destinationPath)`
Extracts call logs from the device using ADB commands.

**Parameters:**
- `destinationPath`: The destination path on the local machine.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.extractCallLogs("/local/path/calllog.db");
```

#### `extractFiles(String sourcePath, String destinationPath)`
Extracts files from the device using ADB commands.

**Parameters:**
- `sourcePath`: The source path on the device.
- `destinationPath`: The destination path on the local machine.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.extractFiles("/data/data/com.example/files", "/local/path");
```

#### `traceIPAddress()`
Traces the IP address of the device using ADB commands.

**Returns:**
- The IP address of the device as a string.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
String ipAddress = adbBase.traceIPAddress();
```

#### `enableStealthMode()`
Enables stealth mode by disabling notifications and hiding the ADB connection from the user.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.enableStealthMode();
```

#### `loadUrlInWebView(String url)`
Loads the given URL in a webview.

**Parameters:**
- `url`: The URL to load in the webview.

**Usage:**
```java
ADBBase adbBase = new ADBBase();
adbBase.loadUrlInWebView("https://www.example.com");
```
