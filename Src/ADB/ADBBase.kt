package Src.ADB

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import Src.Utils.Logger

class ADBBase {

    private val logger = Logger()

    class ADBExecutionException(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
        constructor(message: String) : this(message, null)
    }

    // Method to start adb server
    fun startADBServer() {
        logger.info("Starting ADB server")
        executeCommand("adb start-server")
    }

    // Method to stop adb server
    fun stopADBServer() {
        logger.info("Stopping ADB server")
        executeCommand("adb kill-server")
    }

    // Method to execute adb commands
    fun executeCommand(command: String): String {
        logger.info("Executing command: $command")
        val output = StringBuilder()
        try {
            val process = Runtime.getRuntime().exec(command)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            reader.close()

            val exitCode = process.waitFor()
            if (exitCode != 0) {
                val errorReader = BufferedReader(InputStreamReader(process.errorStream))
                val errorOutput = StringBuilder()
                var errorLine: String?
                while (errorReader.readLine().also { errorLine = it } != null) {
                    errorOutput.append(errorLine).append("\n")
                }
                errorReader.close()
                logger.error("ADB command failed (exit code $exitCode): $command\nError output:\n$errorOutput")
                throw ADBExecutionException("ADB command failed (exit code $exitCode): $command\nError output:\n$errorOutput")
            }

        } catch (e: IOException) {
            logger.error("Error executing ADB command: $command", e)
            throw ADBExecutionException("Error executing ADB command: $command", e)
        } catch (e: InterruptedException) {
            logger.error("ADB command execution interrupted: $command", e)
            Thread.currentThread().interrupt()
            throw ADBExecutionException("ADB command execution interrupted: $command", e)
        }
        return output.toString()
    }

    // Method to run adb server without requiring a PC
    fun runADBServerWithoutPC() {
        logger.info("Running ADB server without PC")
        executeCommand("adb tcpip 5555")
    }

    // Method to simulate hacking by executing a command to access device files
    fun hackDevice() {
        logger.warning("Simulating device hack")
        executeCommand("adb shell ls /data")
    }

    // Method to simulate defense by checking for unauthorized access and stopping the ADB server
    fun defendAgainstHack() {
        logger.warning("Simulating defense against hack")
        val output = executeCommand("adb shell ls /data")
        if (output.contains("unauthorized")) {
            logger.error("Unauthorized access detected, stopping ADB server")
            stopADBServer()
        }
    }

    // Method to test the lifecycle methods of an Android broadcast receiver
    fun testBroadcastReceiverLifecycle() {
        logger.info("Testing broadcast receiver lifecycle")
        executeCommand("adb shell am broadcast -a android.intent.action.BOOT_COMPLETED")
    }

    // Method to test the interaction between broadcast receivers and other components
    fun testBroadcastReceiverInteraction() {
        logger.info("Testing broadcast receiver interaction")
        executeCommand("adb shell am broadcast -a android.intent.action.SCREEN_ON")
    }

    // Method to test the handling of different types of broadcast messages
    fun testBroadcastReceiverHandling() {
        logger.info("Testing broadcast receiver handling")
        executeCommand("adb shell am broadcast -a com.example.CUSTOM_BROADCAST")
    }

    // Method to test the lifecycle methods of an Android activity
    fun testActivityLifecycle(packageName: String, activityName: String) {
        logger.info("Testing activity lifecycle for $packageName/$activityName")
        executeCommand("adb shell am start -n $packageName/$activityName")
        executeCommand("adb shell am force-stop $packageName")
    }

    // Method to test the interaction between activities
    fun testActivityInteraction(packageName: String, firstActivityName: String, secondActivityName: String) {
        logger.info("Testing activity interaction between $packageName/$firstActivityName and $packageName/$secondActivityName")
        executeCommand("adb shell am start -n $packageName/$firstActivityName")
        executeCommand("adb shell am start -n $packageName/$secondActivityName")
        executeCommand("adb shell am force-stop $packageName")
    }

    // Method to test the UI elements of an activity
    fun testActivityUIElements(packageName: String, activityName: String, x: Int, y: Int, text: String? = null) {
        logger.info("Testing UI elements of $packageName/$activityName")
        executeCommand("adb shell am start -n $packageName/$activityName")
        executeCommand("adb shell input tap $x $y")
        if (!text.isNullOrEmpty()) {
            executeCommand("adb shell input text '$text'")
        }
        executeCommand("adb shell am force-stop $packageName")
    }

    // Method to test the lifecycle methods of an Android service
    fun testServiceLifecycle(packageName: String, serviceName: String) {
        logger.info("Testing service lifecycle for $packageName/$serviceName")
        executeCommand("adb shell am startservice -n $packageName/$serviceName")
        executeCommand("adb shell am stopservice -n $packageName/$serviceName")
    }

    // Method to test the interaction between services and other components
    fun testServiceInteraction(packageName: String, serviceName: String, broadcastAction: String) {
        logger.info("Testing service interaction for $packageName/$serviceName with broadcast $broadcastAction")
        executeCommand("adb shell am startservice -n $packageName/$serviceName")
        executeCommand("adb shell am broadcast -a $broadcastAction")
        executeCommand("adb shell am stopservice -n $packageName/$serviceName")
    }

    // Method to test the background processing capabilities of a service
    fun testServiceBackgroundProcessing(packageName: String, serviceName: String) {
        logger.info("Testing background processing of $packageName/$serviceName")
        executeCommand("adb shell am startservice -n $packageName/$serviceName")
        executeCommand("adb shell am force-stop $packageName")
    }

    // Method to enable full wireless control of the device
    fun enableWirelessControl() {
        logger.info("Enabling wireless control of the device")
        executeCommand("adb tcpip 5555")
        val ipAddressOutput = executeCommand("adb shell ip -f inet addr show wlan0")
        // Simple parsing to extract the IP address
        var ipAddress: String? = null
        val lines = ipAddressOutput.split("\n")
        for (line in lines) {
            if (line.contains("inet ") && !line.contains("127.0.0.1")) {
                val start = line.indexOf("inet ") + 5
                val end = line.indexOf("/", start)
                if (end > start) {
                    ipAddress = line.substring(start, end).trim()
                    break
                }
            }
        }
        if (ipAddress != null) {
            executeCommand("adb connect ${ipAddress}:5555")
            logger.info("Attempting to connect to: ${ipAddress}:5555")
        } else {
            logger.warning("Could not parse IP address from ADB output.")
        }
    }

    // Method to extract data from the device using ADB commands
    fun extractData(sourcePath: String, destinationPath: String) {
        logger.info("Extracting data from $sourcePath to $destinationPath")
        executeCommand("adb pull $sourcePath $destinationPath")
    }

    // Method to extract contacts from the device using ADB commands
    fun extractContacts(destinationPath: String) {
        logger.info("Extracting contacts to $destinationPath")
        extractData("/data/data/com.android.providers.contacts/databases/contacts2.db", destinationPath)
    }

    // Method to extract SMS messages from the device using ADB commands
    fun extractSMS(destinationPath: String) {
        logger.info("Extracting SMS messages to $destinationPath")
        extractData("/data/data/com.android.providers.telephony/databases/mmssms.db", destinationPath)
    }

    // Method to extract call logs from the device using ADB commands
    fun extractCallLogs(destinationPath: String) {
        logger.info("Extracting call logs to $destinationPath")
        extractData("/data/data/com.android.providers.contacts/databases/calllog.db", destinationPath)
    }

    // Method to extract files from the device using ADB commands
    fun extractFiles(sourcePath: String, destinationPath: String) {
        logger.info("Extracting files from $sourcePath to $destinationPath")
        extractData(sourcePath, destinationPath)
    }

    // Method to trace the IP address of the device using ADB commands
    fun traceIPAddress(): String {
        logger.info("Tracing IP address of the device")
        return executeCommand("adb shell ip -f inet addr show wlan0")
    }

    // Method to enable stealth mode by disabling notifications and hiding the ADB connection from the user
    fun enableStealthMode() {
        logger.info("Enabling stealth mode")
        executeCommand("adb shell settings put global adb_enabled 0")
        executeCommand("adb shell settings put global development_settings_enabled 0")
        executeCommand("adb shell settings put global usb_debugging_enabled 0")
    }
}
