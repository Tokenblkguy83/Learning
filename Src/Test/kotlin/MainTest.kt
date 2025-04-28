import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.PrintStream

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
        assertTrue(output.contains("4. Simulate Ransomware"))
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
        assertTrue(output.contains("18. C2 - Start Server (already integrated in Prepare Attack)"))
        assertTrue(output.contains("19. C2 - Stop Server"))
        assertTrue(output.contains("0. Exit"))
    }

    @Test
    fun testSimulateRansomware() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Simulating ransomware..."))
        assertTrue(output.contains("Ransomware simulation initiated."))
    }

    @Test
    fun testInstallMalware() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enter path to APK:"))
        assertTrue(output.contains("Installing malware from:"))
        assertTrue(output.contains("Malware installation initiated."))
    }

    @Test
    fun testExecuteShellCommand() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enter shell command to execute:"))
        assertTrue(output.contains("Executing command:"))
        assertTrue(output.contains("Command execution result:"))
    }

    @Test
    fun testListFiles() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enter path to list (default: /sdcard):"))
        assertTrue(output.contains("Listing files in:"))
    }

    @Test
    fun testPullFile() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enter device path to pull:"))
        assertTrue(output.contains("Enter local path to save:"))
        assertTrue(output.contains("Pulling"))
        assertTrue(output.contains("File pull initiated."))
    }

    @Test
    fun testPushFile() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enter local path to push:"))
        assertTrue(output.contains("Enter device path to save to:"))
        assertTrue(output.contains("Pushing"))
        assertTrue(output.contains("File push initiated."))
    }

    @Test
    fun testInstallAPK() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enter path to APK to install:"))
        assertTrue(output.contains("Installing APK from:"))
        assertTrue(output.contains("APK installation initiated."))
    }

    @Test
    fun testUninstallApp() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enter package name to uninstall:"))
        assertTrue(output.contains("Uninstalling package:"))
        assertTrue(output.contains("Uninstall process initiated."))
    }

    @Test
    fun testClearAppData() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Enter package name to clear data:"))
        assertTrue(output.contains("Clearing data for:"))
        assertTrue(output.contains("Clear data process initiated."))
    }

    @Test
    fun testRebootDevice() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Rebooting device..."))
        assertTrue(output.contains("Reboot command sent."))
    }

    @Test
    fun testTakeScreenshot() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Taking screenshot and saving as"))
        assertTrue(output.contains("Screenshot taken (check device root directory)."))
    }

    @Test
    fun testGetDeviceInfo() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Device Information:"))
        assertTrue(output.contains("Model:"))
        assertTrue(output.contains("Serial:"))
        assertTrue(output.contains("Android Version:"))
    }

    @Test
    fun testReadLogcat() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("--- Logcat Output ---"))
        assertTrue(output.contains("--- End of Logcat ---"))
    }

    @Test
    fun testCheckDeviceStatus() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("--- Device Status ---"))
        assertTrue(output.contains("ADB Accessible:"))
        assertTrue(output.contains("Rooted:"))
        assertTrue(output.contains("--- End of Status ---"))
    }

    @Test
    fun testStopC2Server() {
        main()
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Stopping C2 server..."))
        assertTrue(output.contains("C2 server stopped."))
    }
}
