package Src.ADB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import Src.Utils.Logger;

public class ADBBase {

    private Logger logger = new Logger();

    // Method to start adb server
    public void startADBServer() {
        logger.info("Starting ADB server");
        executeCommand("adb start-server");
    }

    // Method to stop adb server
    public void stopADBServer() {
        logger.info("Stopping ADB server");
        executeCommand("adb kill-server");
    }

    // Method to execute adb commands
    public String executeCommand(String command) {
        logger.info("Executing command: " + command);
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            logger.error("Error executing ADB command: " + command, e);
            throw new RuntimeException("Error executing ADB command: " + command, e);
        }
        return output.toString();
    }

    // Method to run adb server without requiring a PC
    public void runADBServerWithoutPC() {
        logger.info("Running ADB server without PC");
        executeCommand("adb tcpip 5555");
    }

    // Method to simulate hacking by executing a command to access device files
    public void hackDevice() {
        logger.warning("Simulating device hack");
        executeCommand("adb shell ls /data");
    }

    // Method to simulate defense by checking for unauthorized access and stopping the ADB server
    public void defendAgainstHack() {
        logger.warning("Simulating defense against hack");
        String output = executeCommand("adb shell ls /data");
        if (output.contains("unauthorized")) {
            logger.error("Unauthorized access detected, stopping ADB server");
            stopADBServer();
        }
    }

    // Method to test the lifecycle methods of an Android broadcast receiver
    public void testBroadcastReceiverLifecycle() {
        logger.info("Testing broadcast receiver lifecycle");
        executeCommand("adb shell am broadcast -a android.intent.action.BOOT_COMPLETED");
    }

    // Method to test the interaction between broadcast receivers and other components
    public void testBroadcastReceiverInteraction() {
        logger.info("Testing broadcast receiver interaction");
        executeCommand("adb shell am broadcast -a android.intent.action.SCREEN_ON");
    }

    // Method to test the handling of different types of broadcast messages
    public void testBroadcastReceiverHandling() {
        logger.info("Testing broadcast receiver handling");
        executeCommand("adb shell am broadcast -a com.example.CUSTOM_BROADCAST");
    }

    // Method to test the lifecycle methods of an Android activity
    public void testActivityLifecycle() {
        logger.info("Testing activity lifecycle");
        executeCommand("adb shell am start -n com.example/.MainActivity");
        executeCommand("adb shell am force-stop com.example");
    }

    // Method to test the interaction between activities
    public void testActivityInteraction() {
        logger.info("Testing activity interaction");
        executeCommand("adb shell am start -n com.example/.MainActivity");
        executeCommand("adb shell am start -n com.example/.SecondActivity");
        executeCommand("adb shell am force-stop com.example");
    }

    // Method to test the UI elements of an activity
    public void testActivityUIElements() {
        logger.info("Testing activity UI elements");
        executeCommand("adb shell am start -n com.example/.MainActivity");
        executeCommand("adb shell input tap 100 200");
        executeCommand("adb shell input text 'Hello'");
        executeCommand("adb shell am force-stop com.example");
    }

    // Method to test the lifecycle methods of an Android service
    public void testServiceLifecycle() {
        logger.info("Testing service lifecycle");
        executeCommand("adb shell am startservice -n com.example/.MyService");
        executeCommand("adb shell am stopservice -n com.example/.MyService");
    }

    // Method to test the interaction between services and other components
    public void testServiceInteraction() {
        logger.info("Testing service interaction");
        executeCommand("adb shell am startservice -n com.example/.MyService");
        executeCommand("adb shell am broadcast -a com.example.SERVICE_ACTION");
        executeCommand("adb shell am stopservice -n com.example/.MyService");
    }

    // Method to test the background processing capabilities of a service
    public void testServiceBackgroundProcessing() {
        logger.info("Testing service background processing");
        executeCommand("adb shell am startservice -n com.example/.MyService");
        executeCommand("adb shell am force-stop com.example");
    }

    // Method to enable full wireless control of the device
    public void enableWirelessControl() {
        logger.info("Enabling wireless control of the device");
        executeCommand("adb tcpip 5555");
        String ipAddress = executeCommand("adb shell ip -f inet addr show wlan0");
        executeCommand("adb connect " + ipAddress.trim() + ":5555");
    }

    // Method to extract data from the device using ADB commands
    public void extractData(String sourcePath, String destinationPath) {
        logger.info("Extracting data from " + sourcePath + " to " + destinationPath);
        executeCommand("adb pull " + sourcePath + " " + destinationPath);
    }

    // Method to extract contacts from the device using ADB commands
    public void extractContacts(String destinationPath) {
        logger.info("Extracting contacts to " + destinationPath);
        extractData("/data/data/com.android.providers.contacts/databases/contacts2.db", destinationPath);
    }

    // Method to extract SMS messages from the device using ADB commands
    public void extractSMS(String destinationPath) {
        logger.info("Extracting SMS messages to " + destinationPath);
        extractData("/data/data/com.android.providers.telephony/databases/mmssms.db", destinationPath);
    }

    // Method to extract call logs from the device using ADB commands
    public void extractCallLogs(String destinationPath) {
        logger.info("Extracting call logs to " + destinationPath);
        extractData("/data/data/com.android.providers.contacts/databases/calllog.db", destinationPath);
    }

    // Method to extract files from the device using ADB commands
    public void extractFiles(String sourcePath, String destinationPath) {
        logger.info("Extracting files from " + sourcePath + " to " + destinationPath);
        extractData(sourcePath, destinationPath);
    }

    // Method to trace the IP address of the device using ADB commands
    public String traceIPAddress() {
        logger.info("Tracing IP address of the device");
        return executeCommand("adb shell ip -f inet addr show wlan0");
    }

    // Method to enable stealth mode by disabling notifications and hiding the ADB connection from the user
    public void enableStealthMode() {
        logger.info("Enabling stealth mode");
        executeCommand("adb shell settings put global adb_enabled 0");
        executeCommand("adb shell settings put global development_settings_enabled 0");
        executeCommand("adb shell settings put global usb_debugging_enabled 0");
    }
}
