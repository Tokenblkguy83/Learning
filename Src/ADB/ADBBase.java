package Src.ADB;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ADBBase {

    // Method to start adb server
    public void startADBServer() {
        executeCommand("adb start-server");
    }

    // Method to stop adb server
    public void stopADBServer() {
        executeCommand("adb kill-server");
    }

    // Method to execute adb commands
    public String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    // Method to run adb server without requiring a PC
    public void runADBServerWithoutPC() {
        executeCommand("adb tcpip 5555");
    }
}
