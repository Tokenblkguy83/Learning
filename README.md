# ADBTool

## Project Description

ADBTool is an educational project designed to demonstrate various functionalities and capabilities of the Android Debug Bridge (ADB). This tool provides a command-line interface to interact with Android devices, allowing users to perform a wide range of actions such as installing applications, pulling and pushing files, executing shell commands, and more. The project has been updated to include new features such as simulating ransomware and starting/stopping a C2 server.

## Purpose

The primary purpose of this project is to serve as a learning resource for individuals interested in understanding and experimenting with ADB commands and their applications. It is intended for educational purposes only and should not be used for any malicious activities.

## Usage Instructions

1. **Prepare Attack**: Enable stealth, persistent access, and start C2.
2. **Steal All Data**: Exfiltrate all accessible data to `/sdcard/stolen_data`.
3. **Dump System Info**: Dump system information to `/sdcard/system_info`.
4. **Simulate Ransomware**: Simulate ransomware on the device.
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
