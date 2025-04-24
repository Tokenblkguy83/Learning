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
}
