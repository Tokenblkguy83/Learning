package Tests.ADB_test;

import Src.ADB.ADBBase;
import Src.ADB.ADBBase.ADBExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ADBBase_test {

    @Mock
    private Runtime runtime;
    @Mock
    private Process process;
    @Mock
    private BufferedReader inputStreamReader;
    @Mock
    private BufferedReader errorStreamReader;

    @InjectMocks
    private ADBBase adbBase;

    private void mockSuccessfulCommand(String command, String output) throws IOException {
        when(runtime.exec(command)).thenReturn(process);
        when(process.getInputStream()).thenReturn(new ByteArrayInputStream(output.getBytes()));
        when(process.getErrorStream()).thenReturn(new ByteArrayInputStream("".getBytes()));
        when(process.waitFor()).thenReturn(0);
    }

    private void mockFailedCommand(String command, String errorOutput, int exitCode) throws IOException {
        when(runtime.exec(command)).thenReturn(process);
        when(process.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes()));
        when(process.getErrorStream()).thenReturn(new ByteArrayInputStream(errorOutput.getBytes()));
        when(process.waitFor()).thenReturn(exitCode);
    }

    @Test
    public void testStartADBServer_success() throws IOException {
        mockSuccessfulCommand("adb start-server", "ADB server started successfully");
        adbBase.startADBServer();
        // Verify that the command was executed
        verify(runtime).exec("adb start-server");
    }

    @Test
    public void testStopADBServer_success() throws IOException {
        mockSuccessfulCommand("adb kill-server", "ADB server killed");
        adbBase.stopADBServer();
        verify(runtime).exec("adb kill-server");
    }

    @Test
    public void testExecuteCommand_success() throws IOException {
        String expectedOutput = "List of devices attached\nemulator-5554\tdevice\n";
        mockSuccessfulCommand("adb devices", expectedOutput);
        String output = adbBase.executeCommand("adb devices");
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testExecuteCommand_failure() throws IOException {
        String errorOutput = "error: device not found\n";
        mockFailedCommand("adb bad-command", errorOutput, 1);
        ADBExecutionException exception = assertThrows(ADBExecutionException.class, () -> adbBase.executeCommand("adb bad-command"));
        assertTrue(exception.getMessage().contains("ADB command failed"));
        assertTrue(exception.getMessage().contains("error: device not found"));
    }

    @Test
    public void testRunADBServerWithoutPC_success() throws IOException {
        mockSuccessfulCommand("adb tcpip 5555", "restarting adbd tcpip on port 5555");
        adbBase.runADBServerWithoutPC();
        verify(runtime).exec("adb tcpip 5555");
    }

    @Test
    public void testHackDevice_success() throws IOException {
        String expectedOutput = "data/app\ndata/misc\ndata/system\n";
        mockSuccessfulCommand("adb shell ls /data", expectedOutput);
        String output = adbBase.hackDevice();
        assertEquals(expectedOutput, adbBase.executeCommand("adb shell ls /data"));
    }

    @Test
    public void testDefendAgainstHack_noUnauthorized() throws IOException {
        mockSuccessfulCommand("adb shell ls /data", "data/app\ndata/misc\n");
        adbBase.defendAgainstHack();
        // Verify that stopADBServer was NOT called
        verify(runtime, never()).exec("adb kill-server");
    }

    @Test
    public void testDefendAgainstHack_unauthorizedDetected() throws IOException {
        mockSuccessfulCommand("adb shell ls /data", "data/app\nunauthorized\ndata/misc\n");
        mockSuccessfulCommand("adb kill-server", "ADB server killed");
        adbBase.defendAgainstHack();
        // Verify that stopADBServer WAS called
        verify(runtime).exec("adb kill-server");
    }

    @Test
    public void testBroadcastReceiverLifecycle_success() throws IOException {
        String expectedOutput = "Sending broadcast intent...\nBroadcast completed: result=0\n";
        mockSuccessfulCommand("adb shell am broadcast -a android.intent.action.BOOT_COMPLETED", expectedOutput);
        adbBase.testBroadcastReceiverLifecycle();
        verify(runtime).exec("adb shell am broadcast -a android.intent.action.BOOT_COMPLETED");
    }

    @Test
    public void testBroadcastReceiverInteraction_success() throws IOException {
        String expectedOutput = "Sending broadcast intent...\nBroadcast completed: result=0\n";
        mockSuccessfulCommand("adb shell am broadcast -a android.intent.action.SCREEN_ON", expectedOutput);
        adbBase.testBroadcastReceiverInteraction();
        verify(runtime).exec("adb shell am broadcast -a android.intent.action.SCREEN_ON");
    }

    @Test
    public void testBroadcastReceiverHandling_success() throws IOException {
        String expectedOutput = "Sending broadcast intent...\nBroadcast completed: result=0\n";
        mockSuccessfulCommand("adb shell am broadcast -a com.example.CUSTOM_BROADCAST", expectedOutput);
        adbBase.testBroadcastReceiverHandling();
        verify(runtime).exec("adb shell am broadcast -a com.example.CUSTOM_BROADCAST");
    }

    @Test
    public void testActivityLifecycle_success() throws IOException {
        mockSuccessfulCommand("adb shell am start -n com.example/.MainActivity", "Starting: Intent { cmp=com.example/.MainActivity }");
        mockSuccessfulCommand("adb shell am force-stop com.example", "Stopping: com.example");
        adbBase.testActivityLifecycle();
        verify(runtime).exec("adb shell am start -n com.example/.MainActivity");
        verify(runtime).exec("adb shell am force-stop com.example");
    }

    @Test
    public void testActivityInteraction_success() throws IOException {
        mockSuccessfulCommand("adb shell am start -n com.example/.MainActivity", "Starting: Intent { cmp=com.example/.MainActivity }");
        mockSuccessfulCommand("adb shell am start -n com.example/.SecondActivity", "Starting: Intent { cmp=com.example/.SecondActivity }");
        mockSuccessfulCommand("adb shell am force-stop com.example", "Stopping: com.example");
        adbBase.testActivityInteraction();
        verify(runtime).exec("adb shell am start -n com.example/.MainActivity");
        verify(runtime).exec("adb shell am start -n com.example/.SecondActivity");
        verify(runtime).exec("adb shell am force-stop com.example");
    }

    @Test
    public void testActivityUIElements_success() throws IOException {
        mockSuccessfulCommand("adb shell am start -n com.example/.MainActivity", "Starting: Intent { cmp=com.example/.MainActivity }");
        mockSuccessfulCommand("adb shell input tap 100 200", "");
        mockSuccessfulCommand("adb shell input text 'Hello'", "");
        mockSuccessfulCommand("adb shell am force-stop com.example", "Stopping: com.example");
        adbBase.testActivityUIElements();
        verify(runtime).exec("adb shell am start -n com.example/.MainActivity");
        verify(runtime).exec("adb shell input tap 100 200");
        verify(runtime).exec("adb shell input text 'Hello'");
        verify(runtime).exec("adb shell am force-stop com.example");
    }

    @Test
    public void testServiceLifecycle_success() throws IOException {
        mockSuccessfulCommand("adb shell am startservice -n com.example/.MyService", "Starting service: Intent { cmp=com.example/.MyService }");
        mockSuccessfulCommand("adb shell am stopservice -n com.example/.MyService", "Stopping service: Intent { cmp=com.example/.MyService }");
        adbBase.testServiceLifecycle();
        verify(runtime).exec("adb shell am startservice -n com.example/.MyService");
        verify(runtime).exec("adb shell am stopservice -n com.example/.MyService");
    }

    @Test
    public void testServiceInteraction_success() throws IOException {
        mockSuccessfulCommand("adb shell am startservice -n com.example/.MyService", "Starting service: Intent { cmp=com.example/.MyService }");
        mockSuccessfulCommand("adb shell am broadcast -a com.example.SERVICE_ACTION", "Sending broadcast intent...");
        mockSuccessfulCommand("adb shell am stopservice -n com.example/.MyService", "Stopping service: Intent { cmp=com.example/.MyService }");
        adbBase.testServiceInteraction();
        verify(runtime).exec("adb shell am startservice -n com.example/.MyService");
        verify(runtime).exec("adb shell am broadcast -a com.example.SERVICE_ACTION");
        verify(runtime).exec("adb shell am stopservice -n com.example/.MyService");
    }

    @Test
    public void testServiceBackgroundProcessing_success() throws IOException {
        mockSuccessfulCommand("adb shell am startservice -n com.example/.MyService", "Starting service: Intent { cmp=com.example/.MyService }");
        mockSuccessfulCommand("adb shell am force-stop com.example", "Stopping: com.example");
        adbBase.testServiceBackgroundProcessing();
        verify(runtime).exec("adb shell am startservice -n com.example/.MyService");
        verify(runtime).exec("adb shell am force-stop com.example");
    }

    @Test
    public void testEnableWirelessControl_success() throws IOException {
        mockSuccessfulCommand("adb tcpip 5555", "restarting adbd tcpip on port 5555");
        mockSuccessfulCommand("adb shell ip -f inet addr show wlan0", "inet 192.168.1.100/24 brd 192.168.1.255 scope global wlan0");
        mockSuccessfulCommand("adb connect 192.168.1.100:5555", "connected to 192.168.1.100:5555");
        adbBase.enableWirelessControl();
        verify(runtime).exec("adb tcpip 5555");
        verify(runtime).exec("adb shell ip -f inet addr show wlan0");
        verify(runtime).exec("adb connect 192.168.1.100:5555");
    }

    @Test
    public void testEnableWirelessControl_noIPFound() throws IOException {
        mockSuccessfulCommand("adb tcpip 5555", "restarting adbd tcpip on port 5555");
        mockSuccessfulCommand("adb shell ip -f inet addr show wlan0", "lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000\n    inet 127.0.0.1/8 scope host lo\n       valid_lft forever preferred_lft forever");
        adbBase.enableWirelessControl();
        verify(runtime).exec("adb tcpip 5555");
        verify(runtime).exec("adb shell ip -f inet addr show wlan0");
        // Verify that connect was NOT called
        verify(runtime, never()).exec(contains("adb connect"));
    }

    @Test
    public void testExtractData_success() throws IOException {
        mockSuccessfulCommand("adb pull /source/path /destination/path", "/destination/path/file.txt: 1 file pulled, 0 skipped. 0.5 MB/s (1024 bytes in 0.002s)");
        adbBase.extractData("/source/path", "/destination/path");
        verify(runtime).exec("adb pull /source/path /destination/path");
    }

    @Test
    public void testExtractContacts_success() throws IOException {
        mockSuccessfulCommand("adb pull /data/data/com.android.providers.contacts/databases/contacts2.db /local/path/contacts.db", "/local/path/contacts.db: 1 file pulled, 0 skipped. 0.8 MB/s (2048 bytes in 0.002s)");
        adbBase.extractContacts("/local/path/contacts.db");
        verify(runtime).exec("adb pull /data/data/com.android.providers.contacts/databases/contacts2.db /local/path/contacts.db");
    }

    @Test
    public void testExtractSMS_success() throws IOException {
        mockSuccessfulCommand("adb pull /data/data/com.android.providers.telephony/databases/mmssms.db /local/path/mmssms.db", "/local/path/mmssms.db: 1 file pulled, 0 skipped. 0.7 MB/s (1536 bytes in 0.002s)");
        adbBase.extractSMS("/local/path/mmssms.db");
        verify(runtime).exec("adb pull /data/data/com.android.providers.telephony/databases/mmssms.db /local/path/mmssms.db");
    }

    @Test
    public void testExtractCallLogs_success() throws IOException {
        mockSuccessfulCommand("adb pull /data/data/com.android.providers.contacts/databases/calllog.db /local/path/calllog.db", "/local/path/calllog.db: 1 file pulled, 0 skipped. 0.6 MB/s (1228 bytes in 0.002s)");
        adbBase.extractCallLogs("/local/path/calllog.db");
        verify(runtime).exec("adb pull /data/data/com.android.providers.contacts/databases/calllog.db /local/path/calllog.db");
    }

    @Test
    public void testExtractFiles_success() throws IOException {
        mockSuccessfulCommand("adb pull /source/file /destination/dir", "/destination/dir/source/file: 1 file pulled, 0 skipped. 0.9 MB/s (2560 bytes in 0.003s)");
        adbBase.extractFiles("/source/file", "/destination/dir");
        verify(runtime).exec("adb pull /source/file /destination/dir");
    }

    @Test
    public void testTraceIPAddress_success() throws IOException {
        String expectedIP = "inet 192.168.1.100/24 brd 192.168.1.255 scope global wlan0\n";
        mockSuccessfulCommand("adb shell ip -f inet addr show wlan0", expectedIP);
        String ipAddress = adbBase.traceIPAddress();
        assertEquals(expectedIP, ipAddress);
    }

    @Test
    public void testEnableStealthMode_success() throws IOException {
        mockSuccessfulCommand("adb shell settings put global adb_enabled 0", "");
        mockSuccessfulCommand("adb shell settings put global development_settings_enabled 0", "");
        mockSuccessfulCommand("adb shell settings put global usb_debugging_enabled 0", "");
        adbBase.enableStealthMode();
        verify(runtime).exec("adb shell settings put global adb_enabled 0");
        verify(runtime).exec("adb shell settings put global development_settings_enabled 0");
        verify(runtime).exec("adb shell settings put global usb_debugging_enabled 0");
    }
}
