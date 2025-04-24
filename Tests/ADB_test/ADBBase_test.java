package Tests.ADB_test;

import Src.ADB.ADBBase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ADBBase_test {

    @Test
    public void testStartADBServer() {
        ADBBase adbBase = new ADBBase();
        adbBase.startADBServer();
        String output = adbBase.executeCommand("adb get-state");
        assertTrue(output.contains("device"));
    }

    @Test
    public void testStopADBServer() {
        ADBBase adbBase = new ADBBase();
        adbBase.stopADBServer();
        String output = adbBase.executeCommand("adb get-state");
        assertTrue(output.contains("error: no devices/emulators found"));
    }

    @Test
    public void testExecuteCommand() {
        ADBBase adbBase = new ADBBase();
        String output = adbBase.executeCommand("adb devices");
        assertNotNull(output);
    }

    @Test
    public void testRunADBServerWithoutPC() {
        ADBBase adbBase = new ADBBase();
        adbBase.runADBServerWithoutPC();
        String output = adbBase.executeCommand("adb devices");
        assertNotNull(output);
    }

    @Test
    public void testHackDevice() {
        ADBBase adbBase = new ADBBase();
        adbBase.hackDevice();
        String output = adbBase.executeCommand("adb shell ls /data");
        assertNotNull(output);
    }

    @Test
    public void testDefendAgainstHack() {
        ADBBase adbBase = new ADBBase();
        adbBase.defendAgainstHack();
        String output = adbBase.executeCommand("adb get-state");
        assertTrue(output.contains("error: no devices/emulators found"));
    }

    @Test
    public void testLogging() {
        ADBBase adbBase = new ADBBase();
        adbBase.startADBServer();
        adbBase.stopADBServer();
        adbBase.executeCommand("adb devices");
        adbBase.runADBServerWithoutPC();
        adbBase.hackDevice();
        adbBase.defendAgainstHack();
    }

    @Test
    public void testBroadcastReceiverLifecycle() {
        ADBBase adbBase = new ADBBase();
        adbBase.testBroadcastReceiverLifecycle();
        String output = adbBase.executeCommand("adb shell am broadcast -a android.intent.action.BOOT_COMPLETED");
        assertNotNull(output);
    }

    @Test
    public void testBroadcastReceiverInteraction() {
        ADBBase adbBase = new ADBBase();
        adbBase.testBroadcastReceiverInteraction();
        String output = adbBase.executeCommand("adb shell am broadcast -a android.intent.action.SCREEN_ON");
        assertNotNull(output);
    }

    @Test
    public void testBroadcastReceiverHandling() {
        ADBBase adbBase = new ADBBase();
        adbBase.testBroadcastReceiverHandling();
        String output = adbBase.executeCommand("adb shell am broadcast -a com.example.CUSTOM_BROADCAST");
        assertNotNull(output);
    }

    @Test
    public void testActivityLifecycle() {
        ADBBase adbBase = new ADBBase();
        adbBase.testActivityLifecycle();
        String output = adbBase.executeCommand("adb shell am start -n com.example/.MainActivity");
        assertNotNull(output);
    }

    @Test
    public void testActivityInteraction() {
        ADBBase adbBase = new ADBBase();
        adbBase.testActivityInteraction();
        String output = adbBase.executeCommand("adb shell am start -n com.example/.MainActivity");
        assertNotNull(output);
    }

    @Test
    public void testActivityUIElements() {
        ADBBase adbBase = new ADBBase();
        adbBase.testActivityUIElements();
        String output = adbBase.executeCommand("adb shell am start -n com.example/.MainActivity");
        assertNotNull(output);
    }

    @Test
    public void testServiceLifecycle() {
        ADBBase adbBase = new ADBBase();
        adbBase.testServiceLifecycle();
        String output = adbBase.executeCommand("adb shell am startservice -n com.example/.MyService");
        assertNotNull(output);
    }

    @Test
    public void testServiceInteraction() {
        ADBBase adbBase = new ADBBase();
        adbBase.testServiceInteraction();
        String output = adbBase.executeCommand("adb shell am startservice -n com.example/.MyService");
        assertNotNull(output);
    }

    @Test
    public void testServiceBackgroundProcessing() {
        ADBBase adbBase = new ADBBase();
        adbBase.testServiceBackgroundProcessing();
        String output = adbBase.executeCommand("adb shell am startservice -n com.example/.MyService");
        assertNotNull(output);
    }

    @Test
    public void testEnableWirelessControl() {
        ADBBase adbBase = new ADBBase();
        adbBase.enableWirelessControl();
        String output = adbBase.executeCommand("adb devices");
        assertNotNull(output);
    }

    @Test
    public void testExtractData() {
        ADBBase adbBase = new ADBBase();
        adbBase.extractData("/data/data/com.example/files", "/local/path");
        String output = adbBase.executeCommand("ls /local/path");
        assertNotNull(output);
    }

    @Test
    public void testExtractContacts() {
        ADBBase adbBase = new ADBBase();
        adbBase.extractContacts("/local/path/contacts.db");
        String output = adbBase.executeCommand("ls /local/path/contacts.db");
        assertNotNull(output);
    }

    @Test
    public void testExtractSMS() {
        ADBBase adbBase = new ADBBase();
        adbBase.extractSMS("/local/path/mmssms.db");
        String output = adbBase.executeCommand("ls /local/path/mmssms.db");
        assertNotNull(output);
    }

    @Test
    public void testExtractCallLogs() {
        ADBBase adbBase = new ADBBase();
        adbBase.extractCallLogs("/local/path/calllog.db");
        String output = adbBase.executeCommand("ls /local/path/calllog.db");
        assertNotNull(output);
    }

    @Test
    public void testExtractFiles() {
        ADBBase adbBase = new ADBBase();
        adbBase.extractFiles("/data/data/com.example/files", "/local/path");
        String output = adbBase.executeCommand("ls /local/path");
        assertNotNull(output);
    }

    @Test
    public void testTraceIPAddress() {
        ADBBase adbBase = new ADBBase();
        String ipAddress = adbBase.traceIPAddress();
        assertNotNull(ipAddress);
    }

    @Test
    public void testEnableStealthMode() {
        ADBBase adbBase = new ADBBase();
        adbBase.enableStealthMode();
        String output = adbBase.executeCommand("adb shell settings get global adb_enabled");
        assertEquals("0", output.trim());
    }
}
