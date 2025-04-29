# ADBTool

## Project Description

ADBTool is an educational project designed to demonstrate various functionalities and capabilities of the Android Debug Bridge (ADB). This tool provides a command-line interface to interact with Android devices, allowing users to perform a wide range of actions such as installing applications, pulling and pushing files, executing shell commands, and more.

## Purpose

The primary purpose of this project is to serve as a learning resource for individuals interested in understanding and experimenting with ADB commands and their applications. It is intended for educational purposes only and should not be used for any malicious activities.

## Usage Instructions

1. **Prepare Attack**: Enable stealth, persistent access, and start C2.
2. **Steal All Data**: Exfiltrate all accessible data to `/sdcard/stolen_data`.
3. **Dump System Info**: Dump system information to `/sdcard/system_info`.
4. **Simulate Hostage**: Simulate hostage on the device.
5. **Install Malware**: Install malware from a specified APK path.
6. **Execute Shell Command**: Execute a shell command on the device.
7. **List Files**: List files in a specified directory.
8. **Pull File**: Pull a file from the device to the local machine.
9. **Push File**: Push a file from the local machine to the device.
10. **Install APK**: Install an APK on the device.
11. **Uninstall App**: Uninstall an application from the device.
12. **Clear App Data**: Clear data for a specified application.
13. **Reboot Device**: Reboot the device.
14. **Take Screenshot**: Take a screenshot and save it to the device.
15. **Get Device Info**: Retrieve device information such as model, serial number, and Android version.
16. **Read Logcat**: Read the logcat output from the device.
17. **Check Device Status**: Check the ADB accessibility and root status of the device.
18. **C2 - Start Server**: Start the C2 server (already integrated in Prepare Attack).
19. **C2 - Stop Server**: Stop the C2 server.
20. **Exit**: Exit the tool.

## Running Tests

To run the tests for this project, follow these steps:

1. Ensure you have the necessary dependencies installed. You can do this by running:
   ```sh
   ./gradlew build
   ```

2. Run the tests using the following command:
   ```sh
   ./gradlew test
   ```

3. The test results will be displayed in the console, and a detailed report will be generated in the `build/reports/tests/test` directory.

## Running the UI Application

To run the UI application for this project, follow these steps:

1. Ensure you have the necessary dependencies installed. You can do this by running:
   ```sh
   ./gradlew build
   ```

2. Run the UI application using the following command:
   ```sh
   ./gradlew run
   ```

3. The UI application will launch, providing a graphical interface to interact with the ADBTool functionalities.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Screenshots

![Screenshot 1](images/screenshot1.png)
![Screenshot 2](images/screenshot2.png)
![Screenshot 3](images/screenshot3.png)

## Features

- **Comprehensive ADB Commands**: Execute a wide range of ADB commands to interact with Android devices.
- **Educational Resource**: Learn and experiment with ADB commands in a safe and controlled environment.
- **User-Friendly Interface**: Simple and intuitive command-line interface for easy interaction.
- **Detailed Documentation**: Comprehensive documentation to guide users through the tool's functionalities.

## Contributing

Contributions are welcome! If you have any suggestions, bug reports, or feature requests, please open an issue or submit a pull request.

## Contact

For any inquiries or questions, please contact us at [email@example.com](mailto:email@example.com).

## Detailed Documentation

### Examples of How to Use the Tool

#### Example 1: Preparing an Attack

To prepare an attack, run the tool and select option 1. This will enable stealth mode, establish persistent access, and start the C2 server.

```sh
Enter action number: 1
Preparing attack...
Enabling stealth mode
Setting up TCP/IP on port 5555
Fetching device IP
Installing backdoor service
Scheduling persistent task
Attempting boot persistence via BootReceiver
Listing files in /sdcard/Download
Starting C2 server
Attack preparation initiated.
```

#### Example 2: Stealing All Data

To steal all accessible data, run the tool and select option 2. This will exfiltrate data to `/sdcard/stolen_data`.

```sh
Enter action number: 2
Stealing all accessible data to: /sdcard/stolen_data
Data exfiltration process started (check device for progress).
```

### Explanations of Various Functionalities

#### Prepare Attack

This functionality enables stealth mode, establishes persistent access, and starts the C2 server. It involves several steps such as disabling notifications, setting up TCP/IP, fetching the device IP, installing a backdoor service, scheduling a persistent task, enabling boot persistence, listing files in the `/sdcard/Download` directory, and starting the C2 server.

#### Steal All Data

This functionality exfiltrates all accessible data to a specified directory on the device. It uses ADB commands to copy data from various locations on the device to the specified directory.

#### Dump System Info

This functionality dumps system information to a specified directory on the device. It collects information such as account details, installed packages, global settings, and system properties.

#### Simulate Hostage

This functionality simulates hostage on the device by broadcasting a message that the device has been encrypted and demanding payment to decrypt the files.

#### Install Malware

This functionality installs malware from a specified APK path on the device. It uses the ADB install command to install the APK.

#### Execute Shell Command

This functionality executes a shell command on the device. It allows users to run any shell command and view the output.

#### List Files

This functionality lists files in a specified directory on the device. It uses the ADB shell ls command to list the files.

#### Pull File

This functionality pulls a file from the device to the local machine. It uses the ADB pull command to transfer the file.

#### Push File

This functionality pushes a file from the local machine to the device. It uses the ADB push command to transfer the file.

#### Install APK

This functionality installs an APK on the device. It uses the ADB install command to install the APK.

#### Uninstall App

This functionality uninstalls an application from the device. It uses the ADB uninstall command to remove the application.

#### Clear App Data

This functionality clears data for a specified application on the device. It uses the ADB shell pm clear command to clear the app data.

#### Reboot Device

This functionality reboots the device. It uses the ADB reboot command to restart the device.

#### Take Screenshot

This functionality takes a screenshot and saves it to the device. It uses the ADB shell screencap command to capture the screenshot.

#### Get Device Info

This functionality retrieves device information such as model, serial number, and Android version. It uses ADB shell getprop commands to get the device properties.

#### Read Logcat

This functionality reads the logcat output from the device. It uses the ADB logcat command to retrieve the logcat output.

#### Check Device Status

This functionality checks the ADB accessibility and root status of the device. It uses ADB get-state and ADB shell su -c 'whoami' commands to check the device status.

#### C2 - Start Server

This functionality starts the C2 server on the device. It sets up a server socket to listen for incoming connections and handles communication with connected clients.

#### C2 - Stop Server

This functionality stops the C2 server on the device. It closes the server socket and stops accepting new connections.

## Contributing to the Project

We welcome contributions to the ADBTool project! If you would like to contribute, please follow these guidelines:

### Coding Standards

- Follow the existing code style and conventions.
- Write clear and concise comments to explain your code.
- Ensure your code is well-documented and easy to understand.

### Pull Request Process

1. Fork the repository and create a new branch for your feature or bug fix.
2. Write your code and ensure it follows the coding standards.
3. Write tests to cover your changes and ensure all tests pass.
4. Submit a pull request with a clear description of your changes.

### Issue Reporting

If you encounter any issues or have suggestions for improvements, please open an issue on the GitHub repository. Provide as much detail as possible to help us understand and address the issue.

Thank you for contributing to ADBTool!
