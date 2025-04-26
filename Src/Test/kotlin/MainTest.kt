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
}
