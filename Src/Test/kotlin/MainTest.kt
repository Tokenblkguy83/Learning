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
    fun testShowDescription() {
        showDescription("1")
        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("Prepare Attack: Enable stealth, persistent access, and start C2 server."))

        showDescription("2")
        val output2 = outputStreamCaptor.toString().trim()
        assertTrue(output2.contains("Steal All Data: Exfiltrate all accessible data to /sdcard/stolen_data."))

        showDescription("3")
        val output3 = outputStreamCaptor.toString().trim()
        assertTrue(output3.contains("Dump System Info: Dump system information to /sdcard/system_info."))

        showDescription("4")
        val output4 = outputStreamCaptor.toString().trim()
        assertTrue(output4.contains("Simulate Ransomware: Simulate ransomware on the device."))

        showDescription("5")
        val output5 = outputStreamCaptor.toString().trim()
        assertTrue(output5.contains("Install Malware: Install malware from a specified APK path."))

        showDescription("6")
        val output6 = outputStreamCaptor.toString().trim()
        assertTrue(output6.contains("Execute Shell Command: Execute a shell command on the device."))

        showDescription("7")
        val output7 = outputStreamCaptor.toString().trim()
        assertTrue(output7.contains("List Files: List files in a specified directory."))

        showDescription("8")
        val output8 = outputStreamCaptor.toString().trim()
        assertTrue(output8.contains("Pull File: Pull a file from the device to the local machine."))

        showDescription("9")
        val output9 = outputStreamCaptor.toString().trim()
        assertTrue(output9.contains("Push File: Push a file from the local machine to the device."))

        showDescription("10")
        val output10 = outputStreamCaptor.toString().trim()
        assertTrue(output10.contains("Install APK: Install an APK on the device."))

        showDescription("11")
        val output11 = outputStreamCaptor.toString().trim()
        assertTrue(output11.contains("Uninstall App: Uninstall an application from the device."))

        showDescription("12")
        val output12 = outputStreamCaptor.toString().trim()
        assertTrue(output12.contains("Clear App Data: Clear data for a specified application."))

        showDescription("13")
        val output13 = outputStreamCaptor.toString().trim()
        assertTrue(output13.contains("Reboot Device: Reboot the device."))

        showDescription("14")
        val output14 = outputStreamCaptor.toString().trim()
        assertTrue(output14.contains("Take Screenshot: Take a screenshot and save it to the device."))

        showDescription("15")
        val output15 = outputStreamCaptor.toString().trim()
        assertTrue(output15.contains("Get Device Info: Retrieve device information such as model, serial number, and Android version."))

        showDescription("16")
        val output16 = outputStreamCaptor.toString().trim()
        assertTrue(output16.contains("Read Logcat: Read the logcat output from the device."))

        showDescription("17")
        val output17 = outputStreamCaptor.toString().trim()
        assertTrue(output17.contains("Check Device Status: Check the ADB accessibility and root status of the device."))

        showDescription("18")
        val output18 = outputStreamCaptor.toString().trim()
        assertTrue(output18.contains("C2 - Start Server: Start the C2 server (already integrated in Prepare Attack)."))

        showDescription("19")
        val output19 = outputStreamCaptor.toString().trim()
        assertTrue(output19.contains("C2 - Stop Server: Stop the C2 server."))

        showDescription("0")
        val output0 = outputStreamCaptor.toString().trim()
        assertTrue(output0.contains("Exit: Exit the tool."))
    }
}
